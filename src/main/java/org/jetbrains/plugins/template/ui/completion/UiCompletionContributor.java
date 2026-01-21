package org.jetbrains.plugins.template.ui.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.UiComponents;
import org.jetbrains.plugins.template.ui.UiLanguage;
import org.jetbrains.plugins.template.ui.psi.UiTypes;

import java.util.Set;

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
                                                    .withInsertHandler(new PropertyInsertHandler())
                                                    .withTypeText("Property")
                                    );
                                }
                            }
                        }

                        // Provide Style property completions ONLY when directly inside Style: (...)
                        String propertyValue = findPropertyValue(position);
                        switch (propertyValue) {
                            case "Style" -> buildLookupElements(result, UiComponents.STYLE_PROPERTIES, "");
                            case "Anchor" -> buildLookupElements(result, UiComponents.ANCHOR_PROPERTIES, "");
                            case "Padding" -> buildLookupElements(result, UiComponents.PADDING_PROPERTIES, "");
                            case null, default -> {
                            }
                        }

                        // Provide LayoutMode value completions
                        String afterProperty = isAfterProperty(position);
                        switch (afterProperty) {
                            case "LayoutMode" -> buildLookupElements(result, UiComponents.LAYOUT_MODE_VALUES, "");
                            case "Alignment", "HorizontalAlignment", "VerticalAlignment" ->
                                    buildLookupElements(result, UiComponents.ALIGNMENT_VALUES, "");
                            case null, default -> {
                            }
                        }

                        String findCurrentProperty = findCurrentProperty(position);
                        switch (findCurrentProperty) {
                            case "PatchStyle" -> buildLookupElements(result, UiComponents.PATCH_STYLE_PARAMS, "");
                            case "LabelStyle" -> buildLookupElements(result, UiComponents.LABEL_STYLE_PARAMS, "");
                            case "ButtonStyle", "TextButtonStyle" ->
                                    buildLookupElements(result, UiComponents.BUTTON_STATES, "");
                            case null, default -> {
                            }
                        }
                    }
                }
        );
    }

    private void buildLookupElements(CompletionResultSet result, Set<String> componentType, String TypeText) {
        for (String state : componentType) {
            result.addElement(
                    LookupElementBuilder.create(state)
                            .withTypeText(TypeText)
            );
        }
    }

    private boolean shouldProvideComponentCompletion(PsiElement position) {

        if (position.getParent().getNode().getElementType() instanceof IFileElementType) {
            return true;
        }

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

    private String findPropertyValue(PsiElement position) {


        // Check if we're inside a PROPERTY_VALUE that belongs to the specific property
        PsiElement current = position.getParent();

        // Walk up to find a PROPERTY_VALUE node
        while (current != null) {
            if (current.getNode() != null &&
                    current.getNode().getElementType() == UiTypes.PROPERTY_VALUE) {

                PsiElement p = position.getPrevSibling();
                while (p != null) {
                    if (p.getNode().getText().equals(",")) break;
                    if (p.getNode().getElementType() == UiTypes.PROPERTY) return null;
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

                    if (beforeColon != null && beforeColon.getNode() != null && beforeColon.getNode().getElementType() == UiTypes.KEYWORD)
                        return beforeColon.getText();
                }

                // Don't continue up - we're in the wrong PROPERTY_VALUE
                return null;
            }
            current = current.getParent();
        }
        return null;
    }


    private String isAfterProperty(PsiElement position) {
        PsiElement prev = position.getPrevSibling();
        while (prev != null && prev.getText().trim().isEmpty()) {
            prev = prev.getPrevSibling();
        }

        if (prev != null && prev.getNode().getText().equals(":")) {
            return prev.getPrevSibling().getText();
        }
        return null;
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

    private String findCurrentProperty(PsiElement position) {
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
                            beforeLParen.getNode().getElementType() == UiTypes.IDENTIFIER) {
                        return beforeLParen.getText();
                    }
                }

                // Don't continue up - we're in the wrong PROPERTY_VALUE
                return null;
            }
            current = current.getParent();
        }
        return null;
    }
}
