package org.jetbrains.plugins.template.ui.references;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.template.ui.UiIcons;
import org.jetbrains.plugins.template.ui.psi.UiFile;
import org.jetbrains.plugins.template.ui.psi.impl.UiImportStatementImpl;
import org.jetbrains.plugins.template.ui.psi.impl.UiVariableDeclarationImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Reference implementation for variable usages like $C
 * Resolves to UiImportStatement (for file imports) or UiVariableDeclaration (for value declarations)
 */
public class UiVariableReferenceImpl extends PsiPolyVariantReferenceBase<PsiElement> {

    public UiVariableReferenceImpl(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        String variableName = getValue();
        PsiFile file = myElement.getContainingFile();

        if (!(file instanceof UiFile)) {
            return ResolveResult.EMPTY_ARRAY;
        }

        List<ResolveResult> results = new ArrayList<>();

        // Find import statements: $C = "Common.ui"
        Collection<UiImportStatementImpl> imports =
            PsiTreeUtil.findChildrenOfType(file, UiImportStatementImpl.class);

        for (UiImportStatementImpl importStatement : imports) {
            if (variableName.equals(importStatement.getName())) {
                results.add(new PsiElementResolveResult(importStatement));
            }
        }

        // Find variable declarations: @MyVar = 42
        Collection<UiVariableDeclarationImpl> declarations =
            PsiTreeUtil.findChildrenOfType(file, UiVariableDeclarationImpl.class);

        for (UiVariableDeclarationImpl declaration : declarations) {
            if (variableName.equals(declaration.getName())) {
                results.add(new PsiElementResolveResult(declaration));
            }
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
        PsiFile file = myElement.getContainingFile();

        if (!(file instanceof UiFile)) {
            return LookupElement.EMPTY_ARRAY;
        }

        List<LookupElement> variants = new ArrayList<>();

        // Add import statements
        Collection<UiImportStatementImpl> imports =
            PsiTreeUtil.findChildrenOfType(file, UiImportStatementImpl.class);

        for (UiImportStatementImpl importStatement : imports) {
            String name = importStatement.getName();
            if (name != null && !name.isEmpty()) {
                variants.add(LookupElementBuilder.create(importStatement, name)
                    .withIcon(UiIcons.FILE)
                    .withTypeText("import"));
            }
        }

        // Add variable declarations
        Collection<UiVariableDeclarationImpl> declarations =
            PsiTreeUtil.findChildrenOfType(file, UiVariableDeclarationImpl.class);

        for (UiVariableDeclarationImpl declaration : declarations) {
            String name = declaration.getName();
            if (name != null && !name.isEmpty()) {
                variants.add(LookupElementBuilder.create(declaration, name)
                    .withIcon(UiIcons.FILE)
                    .withTypeText("variable"));
            }
        }

        return variants.toArray();
    }
}
