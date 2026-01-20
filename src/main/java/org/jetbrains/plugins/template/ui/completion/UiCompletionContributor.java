package org.jetbrains.plugins.template.ui.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.UiComponents;
import org.jetbrains.plugins.template.ui.UiLanguage;
import org.jetbrains.plugins.template.ui.psi.UiTypes;

public class UiCompletionContributor extends CompletionContributor {

    public UiCompletionContributor() {
        // Component type completion
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(UiLanguage.INSTANCE),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                   @NotNull ProcessingContext context,
                                                   @NotNull CompletionResultSet result) {
                        PsiElement position = parameters.getPosition();

                        // Check if we should provide component completions
                        if (shouldProvideComponentCompletion(position)) {
                            for (String componentType : UiComponents.COMPONENT_TYPES) {
                                result.addElement(
                                        LookupElementBuilder.create(componentType)
                                                .withTypeText("Component")
                                                .withBoldness(true)
                                );
                            }
                        }

                        // Check if we should provide property completions
                        if (shouldProvidePropertyCompletion(position)) {
                            String componentType = findComponentType(position);

                            if (componentType != null) {
                                for (String property : UiComponents.getPropertiesForComponent(componentType)) {
                                    result.addElement(
                                            LookupElementBuilder.create(property)
                                                    .withTypeText("Property")
                                    );
                                }
                            } else {
                                // Provide common properties if we can't determine component type
                                for (String property : UiComponents.COMMON_PROPERTIES) {
                                    result.addElement(
                                            LookupElementBuilder.create(property)
                                                    .withTypeText("Property")
                                    );
                                }
                            }
                        }

                        // Provide Style property completions
                        if (isInsideStyleBlock(position)) {
                            for (String property : UiComponents.STYLE_PROPERTIES) {
                                result.addElement(
                                        LookupElementBuilder.create(property)
                                                .withTypeText("Style Property")
                                );
                            }
                        }

                        // Provide Anchor property completions
                        if (isInsideAnchorBlock(position)) {
                            for (String property : UiComponents.ANCHOR_PROPERTIES) {
                                result.addElement(
                                        LookupElementBuilder.create(property)
                                                .withTypeText("Anchor Property")
                                );
                            }
                        }

                        // Provide LayoutMode value completions
                        if (isAfterLayoutMode(position)) {
                            for (String value : UiComponents.LAYOUT_MODE_VALUES) {
                                result.addElement(
                                        LookupElementBuilder.create(value)
                                                .withTypeText("Layout Mode")
                                );
                            }
                        }

                        // Provide Alignment value completions
                        if (isAfterAlignment(position)) {
                            for (String value : UiComponents.ALIGNMENT_VALUES) {
                                result.addElement(
                                        LookupElementBuilder.create(value)
                                                .withTypeText("Alignment")
                                );
                            }
                        }
                    }
                }
        );
    }

    private boolean shouldProvideComponentCompletion(PsiElement position) {
        // Simple heuristic: check if previous non-whitespace token is @ or we're at start of line
        PsiElement prev = position.getPrevSibling();
        while (prev != null && prev.getText().trim().isEmpty()) {
            prev = prev.getPrevSibling();
        }

        if (prev != null && prev.getNode() != null) {
            return prev.getNode().getElementType() == UiTypes.AT ||
                   prev.getNode().getElementType() == UiTypes.EQUALS;
        }

        return false;
    }

    private boolean shouldProvidePropertyCompletion(PsiElement position) {
        // Check if we're inside a block (between braces)
        PsiElement parent = position.getParent();
        if (parent != null && parent.getNode() != null) {
            return parent.getNode().getElementType() == UiTypes.BLOCK;
        }
        return false;
    }

    private boolean isInsideStyleBlock(PsiElement position) {
        // Look for "Style:" in ancestors
        PsiElement current = position;
        while (current != null) {
            String text = current.getText();
            if (text != null && text.contains("Style:")) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    private boolean isInsideAnchorBlock(PsiElement position) {
        // Look for "Anchor:" in ancestors
        PsiElement current = position;
        while (current != null) {
            String text = current.getText();
            if (text != null && text.contains("Anchor:")) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    private boolean isAfterLayoutMode(PsiElement position) {
        PsiElement prev = position.getPrevSibling();
        while (prev != null && prev.getText().trim().isEmpty()) {
            prev = prev.getPrevSibling();
        }

        if (prev != null) {
            String text = prev.getText();
            return text != null && text.contains("LayoutMode:");
        }
        return false;
    }

    private boolean isAfterAlignment(PsiElement position) {
        PsiElement prev = position.getPrevSibling();
        while (prev != null && prev.getText().trim().isEmpty()) {
            prev = prev.getPrevSibling();
        }

        if (prev != null) {
            String text = prev.getText();
            return text != null && (text.contains("Alignment:") ||
                                   text.contains("HorizontalAlignment:") ||
                                   text.contains("VerticalAlignment:"));
        }
        return false;
    }

    private String findComponentType(PsiElement position) {
        // Try to find the component type by looking backwards
        PsiElement current = position;
        while (current != null) {
            PsiElement prev = current.getPrevSibling();
            if (prev != null && prev.getNode() != null) {
                if (prev.getNode().getElementType() == UiTypes.IDENTIFIER) {
                    String text = prev.getText();
                    if (UiComponents.isValidComponent(text)) {
                        return text;
                    }
                }
            }
            current = current.getParent();
        }
        return null;
    }
}
