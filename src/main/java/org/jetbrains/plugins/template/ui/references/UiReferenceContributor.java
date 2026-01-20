package org.jetbrains.plugins.template.ui.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.UiLanguage;
import org.jetbrains.plugins.template.ui.psi.UiTypes;

public class UiReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        // Register reference provider for identifiers (for import resolution)
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement().withLanguage(UiLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                  @NotNull ProcessingContext context) {
                        if (element.getNode() == null) {
                            return PsiReference.EMPTY_ARRAY;
                        }

                        // Handle variable references like $C
                        if (element.getNode().getElementType() == UiTypes.IDENTIFIER) {
                            PsiElement prev = element.getPrevSibling();
                            while (prev != null && prev.getText().trim().isEmpty()) {
                                prev = prev.getPrevSibling();
                            }

                            if (prev != null && prev.getNode() != null &&
                                prev.getNode().getElementType() == UiTypes.DOLLAR) {
                                // This is a variable reference
                                return new PsiReference[]{
                                        new UiVariableReference(element, new TextRange(0, element.getTextLength()))
                                };
                            }
                        }

                        // Handle string literals (file imports)
                        if (element.getNode().getElementType() == UiTypes.STRING) {
                            String text = element.getText();
                            if (text.endsWith(".ui\"") || text.endsWith(".ui'")) {
                                // This is a file import
                                return new PsiReference[]{
                                        new UiFileReference(element, new TextRange(1, element.getTextLength() - 1))
                                };
                            }
                        }

                        return PsiReference.EMPTY_ARRAY;
                    }
                }
        );
    }
}
