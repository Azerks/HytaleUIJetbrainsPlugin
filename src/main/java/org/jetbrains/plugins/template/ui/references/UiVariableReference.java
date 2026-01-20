package org.jetbrains.plugins.template.ui.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.template.ui.psi.UiFile;
import org.jetbrains.plugins.template.ui.psi.UiTypes;

public class UiVariableReference extends PsiReferenceBase<PsiElement> {

    public UiVariableReference(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        String variableName = getValue();

        // Search for variable definition in the same file
        PsiFile file = myElement.getContainingFile();
        if (!(file instanceof UiFile)) {
            return null;
        }

        // Find variable declaration: $variableName = "..."
        PsiElement[] children = file.getChildren();
        return findVariableDeclaration(children, variableName);
    }

    private PsiElement findVariableDeclaration(PsiElement[] elements, String variableName) {
        for (PsiElement element : elements) {
            if (element.getNode() != null) {
                // Look for pattern: $ IDENTIFIER =
                if (element.getNode().getElementType() == UiTypes.DOLLAR) {
                    PsiElement next = element.getNextSibling();
                    while (next != null && next.getText().trim().isEmpty()) {
                        next = next.getNextSibling();
                    }

                    if (next != null && next.getNode() != null &&
                        next.getNode().getElementType() == UiTypes.IDENTIFIER &&
                        next.getText().equals(variableName)) {
                        return next; // Found the declaration
                    }
                }
            }

            // Recursively search children
            PsiElement found = findVariableDeclaration(element.getChildren(), variableName);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        // Could return all available variables for completion
        return new Object[0];
    }
}
