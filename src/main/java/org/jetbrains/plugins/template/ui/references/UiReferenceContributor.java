package org.jetbrains.plugins.template.ui.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.UiLanguage;
import org.jetbrains.plugins.template.ui.psi.UiTypes;

public class UiReferenceContributor extends PsiReferenceContributor {

    static {
        System.out.println("============================================");
        System.out.println("UiReferenceContributor CLASS LOADED");
        System.out.println("============================================");
    }

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        System.out.println("============================================");
        System.out.println("UiReferenceContributor.registerReferenceProviders called");
        System.out.println("============================================");

        // Register reference provider for all PsiElements
        // We filter for LeafPsiElement (actual tokens) inside
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        System.out.println("getReferencesByElement called: " + element.getClass().getName() +
                                         ", Lang: " + element.getLanguage() + ", Text: '" + element.getText() + "'" +
                                         ", isLeaf: " + (element instanceof LeafPsiElement));

                        // Only process UI language elements first
                        if (element.getLanguage() != UiLanguage.INSTANCE) {
                            return PsiReference.EMPTY_ARRAY;
                        }

                        if (element.getNode() == null) {
                            return PsiReference.EMPTY_ARRAY;
                        }

                        IElementType elementType = element.getNode().getElementType();
                        System.out.println("Processing element: " + elementType + " = '" + element.getText() + "'");

                        // Handle IDENTIFIER composite elements (UiPsiElement wrapping IDENTIFIER tokens)
                        if (elementType == UiTypes.IDENTIFIER) {
                            System.out.println("  -> Found IDENTIFIER composite element: " + element.getText());

                            // Check if this is a variable reference (preceded by $)
                            PsiElement prev = element.getPrevSibling();
                            while (prev != null && prev.getText().trim().isEmpty()) {
                                prev = prev.getPrevSibling();
                            }

                            if (prev != null && prev.getNode() != null &&
                                    prev.getNode().getElementType() == UiTypes.DOLLAR) {
                                System.out.println("  -> This is a variable reference: $" + element.getText());
                                // This is a variable reference like $C
                                return new PsiReference[]{
                                        new UiVariableReferenceImpl(element, new TextRange(0, element.getTextLength()))
                                };
                            }

                            // Check if this is a component reference (preceded by @ or used standalone)
                            if (prev != null && prev.getNode() != null &&
                                    prev.getNode().getElementType() == UiTypes.AT) {
                                System.out.println("  -> This is a @component reference: @" + element.getText());
                                // This is a component reference like @QuestName
                                return new PsiReference[]{
                                        new UiComponentReferenceImpl(element, new TextRange(0, element.getTextLength()))
                                };
                            }

                            // Check if the next sibling is a component body (standalone component usage like InputLabel {...})
                            PsiElement next = element.getNextSibling();
                            while (next != null && next.getText().trim().isEmpty()) {
                                next = next.getNextSibling();
                            }

                            if (next != null && next.getNode() != null &&
                                    next.getNode().getElementType() == UiTypes.COMPONENT_BODY) {
                                System.out.println("  -> This is a standalone component reference: " + element.getText());
                                // This is a standalone component reference like InputLabel { ... }
                                return new PsiReference[]{
                                        new UiComponentReferenceImpl(element, new TextRange(0, element.getTextLength()))
                                };
                            }
                        }

                        // Handle string literals (file imports)
                        if (elementType == UiTypes.STRING) {
                            String text = element.getText();
                            if (text.endsWith(".ui\"") || text.endsWith(".ui'")) {
                                System.out.println("  -> Found file import: " + text);
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
