package org.jetbrains.plugins.template.ui.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.UiComponents;
import org.jetbrains.plugins.template.ui.psi.UiTypes;

import java.util.HashSet;
import java.util.Set;

public class UiAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element.getNode() == null) {
            return;
        }

        IElementType elementType = element.getNode().getElementType();

        // Validate component types
        if (elementType == UiTypes.IDENTIFIER) {
            // Check if this is part of a variable first (preceded by $)
            if (isPrecededByDollar(element)) {
                // This is a variable reference or declaration
                validateVariableReference(element, holder);
                validateVariableDeclaration(element, holder);
                // Skip other validations for variables
                return;
            }

            // Not a variable, validate as component/property
            validateComponentType(element, holder);
            validateProperty(element, holder);
        }

        // Validate comma placement
        validateCommas(element, holder);

        // Note: Bracket matching is already validated by the parser when creating BLOCK elements
        // No need for additional validation here
    }

    private void validateComponentType(PsiElement element, AnnotationHolder holder) {
        String text = element.getText();

        // Check if this identifier appears to be a component type (after @ or at start of definition)
        PsiElement prev = element.getPrevSibling();
        while (prev != null && prev.getText().trim().isEmpty()) {
            prev = prev.getPrevSibling();
        }

        if (prev != null && prev.getNode() != null) {
            IElementType prevType = prev.getNode().getElementType();

            // If preceded by @ or =, it might be a component type
            if (prevType == UiTypes.AT || prevType == UiTypes.EQUALS) {
                // Check if it's a known component type
                if (!UiComponents.isValidComponent(text) &&
                    !text.startsWith("@") &&
                    !text.startsWith("$") &&
                    Character.isUpperCase(text.charAt(0))) {

                    holder.newAnnotation(HighlightSeverity.WARNING,
                            "Unknown component type: " + text)
                            .range(element)
                            .create();
                }
            }
        }
    }

    private void validateProperty(PsiElement element, AnnotationHolder holder) {
        String text = element.getText();

        // Check if this is a property name (followed by colon)
        PsiElement next = element.getNextSibling();
        while (next != null && next.getText().trim().isEmpty()) {
            next = next.getNextSibling();
        }

        if (next != null && next.getNode() != null) {
            IElementType nextType = next.getNode().getElementType();

            if (nextType == UiTypes.COLON) {
                // This is a property name, validate it
                String componentType = findComponentType(element);

                if (componentType != null) {
                    if (!UiComponents.isValidProperty(componentType, text) &&
                        !UiComponents.STYLE_PROPERTIES.contains(text) &&
                        !UiComponents.ANCHOR_PROPERTIES.contains(text) &&
                        !UiComponents.BACKGROUND_PROPERTIES.contains(text)) {

                        holder.newAnnotation(HighlightSeverity.WARNING,
                                "Unknown property '" + text + "' for component type '" + componentType + "'")
                                .range(element)
                                .create();
                    }
                }
            }
        }
    }

    private void validateCommas(PsiElement element, AnnotationHolder holder) {
        if (element.getNode() == null) {
            return;
        }

        IElementType elementType = element.getNode().getElementType();

        // Check for missing comma between property declarations
        if (elementType == UiTypes.IDENTIFIER) {
            PsiElement next = element.getNextSibling();
            while (next != null && next.getText().trim().isEmpty()) {
                next = next.getNextSibling();
            }

            if (next != null && next.getNode() != null) {
                IElementType nextType = next.getNode().getElementType();

                // If we have identifier followed by identifier (not colon), might be missing comma
                if (nextType == UiTypes.IDENTIFIER) {
                    // Check if the next identifier is followed by a colon (property name)
                    PsiElement nextNext = next.getNextSibling();
                    while (nextNext != null && nextNext.getText().trim().isEmpty()) {
                        nextNext = nextNext.getNextSibling();
                    }

                    if (nextNext != null && nextNext.getNode() != null &&
                        nextNext.getNode().getElementType() == UiTypes.COLON) {

                        // Create a TextRange for the space between the two elements
                        com.intellij.openapi.util.TextRange range = new com.intellij.openapi.util.TextRange(
                                element.getTextRange().getEndOffset(),
                                next.getTextRange().getStartOffset()
                        );

                        holder.newAnnotation(HighlightSeverity.ERROR,
                                "Missing comma between property declarations")
                                .range(range)
                                .create();
                    }
                }
            }
        }
    }

    private String findComponentType(PsiElement element) {
        // Try to find the component type by looking backwards through the tree
        PsiElement current = element;

        while (current != null) {
            PsiElement prev = current.getPrevSibling();

            while (prev != null) {
                if (prev.getNode() != null && prev.getNode().getElementType() == UiTypes.IDENTIFIER) {
                    String text = prev.getText();
                    if (UiComponents.isValidComponent(text)) {
                        return text;
                    }
                }
                prev = prev.getPrevSibling();
            }

            current = current.getParent();
        }

        return null;
    }

    private void validateVariableReference(PsiElement element, AnnotationHolder holder) {
        String text = element.getText();

        // Check if this identifier is preceded by a DOLLAR sign (variable reference)
        PsiElement prev = element.getPrevSibling();
        while (prev != null && prev.getText().trim().isEmpty()) {
            prev = prev.getPrevSibling();
        }

        if (prev != null && prev.getNode() != null && prev.getNode().getElementType() == UiTypes.DOLLAR) {
            // Check what comes after the identifier
            PsiElement next = element.getNextSibling();
            while (next != null && next.getText().trim().isEmpty()) {
                next = next.getNextSibling();
            }

            // If followed by EQUALS, this is a declaration, not a reference - skip validation
            if (next != null && next.getNode() != null && next.getNode().getElementType() == UiTypes.EQUALS) {
                return;
            }

            // Check if the variable is defined in the file
            if (!isVariableDefined(element, text)) {
                holder.newAnnotation(HighlightSeverity.ERROR,
                        "Undefined variable: $" + text)
                        .range(element)
                        .create();
            }
        }
    }

    private boolean isVariableDefined(PsiElement element, String variableName) {
        PsiFile file = element.getContainingFile();
        if (file == null) {
            return false;
        }

        // Collect all defined variables in the file
        Set<String> definedVariables = new HashSet<>();
        collectDefinedVariables(file, definedVariables);

        return definedVariables.contains(variableName);
    }

    private void collectDefinedVariables(PsiElement element, Set<String> variables) {
        if (element.getNode() != null && element.getNode().getElementType() == UiTypes.IDENTIFIER) {
            // Check if this is a variable definition ($C = "...")
            PsiElement prev = element.getPrevSibling();
            while (prev != null && prev.getText().trim().isEmpty()) {
                prev = prev.getPrevSibling();
            }

            if (prev != null && prev.getNode() != null && prev.getNode().getElementType() == UiTypes.DOLLAR) {
                // Check if followed by EQUALS
                PsiElement next = element.getNextSibling();
                while (next != null && next.getText().trim().isEmpty()) {
                    next = next.getNextSibling();
                }

                if (next != null && next.getNode() != null && next.getNode().getElementType() == UiTypes.EQUALS) {
                    variables.add(element.getText());
                }
            }
        }

        // Recursively check children
        for (PsiElement child : element.getChildren()) {
            collectDefinedVariables(child, variables);
        }
    }

    private void validateVariableDeclaration(PsiElement element, AnnotationHolder holder) {
        String text = element.getText();

        // Check if this is a variable declaration ($C = "...")
        PsiElement prev = element.getPrevSibling();
        while (prev != null && prev.getText().trim().isEmpty()) {
            prev = prev.getPrevSibling();
        }

        if (prev != null && prev.getNode() != null && prev.getNode().getElementType() == UiTypes.DOLLAR) {
            // Check if followed by EQUALS (this is a declaration)
            PsiElement next = element.getNextSibling();
            while (next != null && next.getText().trim().isEmpty()) {
                next = next.getNextSibling();
            }

            if (next != null && next.getNode() != null && next.getNode().getElementType() == UiTypes.EQUALS) {
                // This is a variable declaration
                // Find the value (STRING after EQUALS)
                PsiElement valueElement = next.getNextSibling();
                while (valueElement != null && valueElement.getText().trim().isEmpty()) {
                    valueElement = valueElement.getNextSibling();
                }

                if (valueElement != null && valueElement.getNode() != null &&
                    valueElement.getNode().getElementType() == UiTypes.STRING) {
                    String value = valueElement.getText();
                    // Remove quotes
                    value = value.substring(1, value.length() - 1);

                    // Check if the value ends with .ui extension
                    if (!value.endsWith(".ui")) {
                        holder.newAnnotation(HighlightSeverity.ERROR,
                                "Variable must be declared with a .ui file (e.g., \"Common.ui\")")
                                .range(valueElement)
                                .create();
                    }
                }
            }
        }
    }

    private boolean isPrecededByDollar(PsiElement element) {
        PsiElement prev = element.getPrevSibling();
        while (prev != null && prev.getText().trim().isEmpty()) {
            prev = prev.getPrevSibling();
        }
        return prev != null && prev.getNode() != null && prev.getNode().getElementType() == UiTypes.DOLLAR;
    }
}
