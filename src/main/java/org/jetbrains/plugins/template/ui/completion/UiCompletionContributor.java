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

                        // Provide Style property completions ONLY when directly inside Style: (...)
                        if (isDirectlyInsidePropertyValue(position, "Style")) {
                            for (String property : UiComponents.STYLE_PROPERTIES) {
                                result.addElement(
                                        LookupElementBuilder.create(property)
                                                .withTypeText("Style Property")
                                );
                            }
                        }

                        // Provide Anchor property completions ONLY when directly inside Anchor: (...)
                        if (isDirectlyInsidePropertyValue(position, "Anchor")) {
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

                        // Provide Padding property completions when inside Padding(...)
                        if (isDirectlyInsideFunctionCall(position, "Padding")) {
                            for (String property : UiComponents.PADDING_PROPERTIES) {
                                result.addElement(
                                        LookupElementBuilder.create(property)
                                                .withTypeText("Padding Property")
                                );
                            }
                        }

                        // Provide PatchStyle parameter completions when inside PatchStyle(...)
                        if (isDirectlyInsideFunctionCall(position, "PatchStyle")) {
                            for (String param : UiComponents.PATCH_STYLE_PARAMS) {
                                result.addElement(
                                        LookupElementBuilder.create(param)
                                                .withTypeText("PatchStyle Parameter")
                                );
                            }
                        }

                        // Provide LabelStyle parameter completions when inside LabelStyle(...)
                        if (isDirectlyInsideFunctionCall(position, "LabelStyle")) {
                            for (String param : UiComponents.LABEL_STYLE_PARAMS) {
                                result.addElement(
                                        LookupElementBuilder.create(param)
                                                .withTypeText("LabelStyle Parameter")
                                );
                            }
                        }

                        // Provide ButtonStyle state names when inside ButtonStyle(...) or TextButtonStyle(...)
                        if (isDirectlyInsideFunctionCall(position, "ButtonStyle") ||
                                isDirectlyInsideFunctionCall(position, "TextButtonStyle")) {
                            for (String state : UiComponents.BUTTON_STATES) {
                                result.addElement(
                                        LookupElementBuilder.create(state)
                                                .withTypeText("Button State")
                                );
                            }
                        }
                    }
                }
        );
    }

    private boolean shouldProvideComponentCompletion(PsiElement position) {

        boolean hasLineTerminator = false;
        PsiElement beforeColon = position.getPrevSibling();
        while (beforeColon != null && beforeColon.getNode().getElementType() != UiTypes.COMPONENT) {
            if (beforeColon.getNode().getText().equals(";")) hasLineTerminator = true;
            if (beforeColon.getNode().getText().equals(":") && !hasLineTerminator) return false;

            beforeColon = beforeColon.getPrevSibling();
            if (beforeColon == null) beforeColon = position.getParent();
        }

        if (beforeColon != null && beforeColon.getNode() != null && beforeColon.getNode().getElementType() == UiTypes.COMPONENT && beforeColon.getText().equals("Group")) {
            return position.getParent().getNode().getElementType() == UiTypes.COMPONENT_BODY;
        }

        return position.getParent() == null;
    }

    private boolean shouldProvidePropertyCompletion(PsiElement position) {
        boolean hasLineTerminator = false;
        PsiElement beforeColon = position.getPrevSibling();
        while (beforeColon != null && beforeColon.getNode().getElementType() != UiTypes.PROPERTY_VALUE) {
            if (beforeColon.getNode().getText().equals(";")) hasLineTerminator = true;
            if (beforeColon.getNode().getText().equals(":") && !hasLineTerminator) return false;
            beforeColon = beforeColon.getPrevSibling();
        }

        PsiElement parent = position.getParent();
        if (parent != null && parent.getNode() != null) {
            return parent.getNode().getElementType() == UiTypes.COMPONENT_BODY;
        }
        return false;
    }

    private boolean isDirectlyInsidePropertyValue(PsiElement position, String propertyName) {


        // Check if we're inside a PROPERTY_VALUE that belongs to the specific property
        PsiElement current = position.getParent();

        // Walk up to find a PROPERTY_VALUE node
        while (current != null) {
            if (current.getNode() != null &&
                    current.getNode().getElementType() == UiTypes.PROPERTY_VALUE) {

                PsiElement p = position.getPrevSibling();
                while (p != null) {
                    if (p.getNode().getText().equals(",")) break;
                    if (p.getNode().getElementType() == UiTypes.PROPERTY) return false;
                    p = p.getPrevSibling();
                }

                // Found a PROPERTY_VALUE, now check if it's preceded by the property name
                PsiElement prev = current.getPrevSibling();
                while (prev != null && prev.getText().trim().isEmpty()) {
                    prev = prev.getPrevSibling();
                }

                // Check for "PropertyName:"
                if (prev != null) {
                    PsiElement beforeColon = prev.getPrevSibling();
                    while (beforeColon != null && (beforeColon.getText().trim().isEmpty() || beforeColon.getText().trim().equals(":"))) {
                        beforeColon = beforeColon.getPrevSibling();
                    }

                    if (beforeColon != null &&
                            beforeColon.getNode() != null &&
                            beforeColon.getNode().getElementType() == UiTypes.KEYWORD &&
                            beforeColon.getText().equals(propertyName)) {
                        return true;
                    }
                }

                // Don't continue up - we're in the wrong PROPERTY_VALUE
                return false;
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

    private boolean isDirectlyInsideFunctionCall(PsiElement position, String functionName) {
        // Check if we're inside a PROPERTY_VALUE that comes from FunctionName(...)
        PsiElement current = position.getParent();

        // Walk up to find a PROPERTY_VALUE node
        while (current != null) {
            if (current.getNode() != null &&
                    current.getNode().getElementType() == UiTypes.PROPERTY_VALUE) {

                // Found a PROPERTY_VALUE, now check if it's preceded by FunctionName and (
                PsiElement prev = current.getPrevSibling();
                while (prev != null && prev.getText().trim().isEmpty()) {
                    prev = prev.getPrevSibling();
                }

                // Should be LPAREN
                if (prev != null && prev.getNode() != null &&
                        prev.getNode().getElementType() == UiTypes.LPAREN) {

                    // Before LPAREN should be the function name
                    PsiElement beforeLParen = prev.getPrevSibling();
                    while (beforeLParen != null && beforeLParen.getText().trim().isEmpty()) {
                        beforeLParen = beforeLParen.getPrevSibling();
                    }

                    if (beforeLParen != null &&
                            beforeLParen.getNode() != null &&
                            beforeLParen.getNode().getElementType() == UiTypes.IDENTIFIER &&
                            beforeLParen.getText().equals(functionName)) {
                        return true;
                    }
                }

                // Don't continue up - we're in the wrong PROPERTY_VALUE
                return false;
            }
            current = current.getParent();
        }
        return false;
    }
}
