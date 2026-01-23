package org.jetbrains.plugins.template.ui.references;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.template.ui.UiComponents;
import org.jetbrains.plugins.template.ui.UiIcons;
import org.jetbrains.plugins.template.ui.psi.UiFile;
import org.jetbrains.plugins.template.ui.psi.impl.UiComponentDefinitionImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Reference implementation for component usages like @TextField or Label
 * Resolves to UiComponentDefinition (for custom components) or built-in components
 */
public class UiComponentReferenceImpl extends PsiPolyVariantReferenceBase<PsiElement> {

    public UiComponentReferenceImpl(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        String componentName = getValue();
        PsiFile file = myElement.getContainingFile();

        if (!(file instanceof UiFile)) {
            return ResolveResult.EMPTY_ARRAY;
        }

        List<ResolveResult> results = new ArrayList<>();

        // Find component definitions: @ComponentName = Component { ... }
        Collection<UiComponentDefinitionImpl> definitions =
            PsiTreeUtil.findChildrenOfType(file, UiComponentDefinitionImpl.class);

        for (UiComponentDefinitionImpl definition : definitions) {
            if (componentName.equals(definition.getName())) {
                results.add(new PsiElementResolveResult(definition));
            }
        }


        // Check if it's a built-in component
        if (UiComponents.isBuiltInComponent(componentName)) {
            // For built-in components, we don't have a PSI element to resolve to
            // But we can still provide completion and other IDE features
            // Return empty array for built-in components
        }

        return results.toArray(new ResolveResult[0]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] results = multiResolve(false);
        return results.length > 0 ? results[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        List<LookupElement> variants = new ArrayList<>();

        // Add built-in components
        for (String component : UiComponents.BUILT_IN_COMPONENTS) {
            variants.add(LookupElementBuilder.create(component)
                .withIcon(UiIcons.FILE)
                .withTypeText("built-in component")
                .bold());
        }

        // Add custom component definitions from the current file
        PsiFile file = myElement.getContainingFile();
        if (file instanceof UiFile) {
            Collection<UiComponentDefinitionImpl> definitions =
                PsiTreeUtil.findChildrenOfType(file, UiComponentDefinitionImpl.class);

            for (UiComponentDefinitionImpl definition : definitions) {
                String name = definition.getName();
                if (name != null && !name.isEmpty()) {
                    variants.add(LookupElementBuilder.create(definition, name)
                        .withIcon(UiIcons.FILE)
                        .withTypeText("custom component"));
                }
            }
        }

        return variants.toArray();
    }
}
