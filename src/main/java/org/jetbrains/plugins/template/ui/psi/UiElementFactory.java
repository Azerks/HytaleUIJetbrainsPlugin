package org.jetbrains.plugins.template.ui.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.UiFileType;

/**
 * Factory class for creating PSI elements programmatically.
 * Used for rename refactoring and other PSI manipulations.
 */
public class UiElementFactory {

    @NotNull
    public static PsiElement createIdentifier(@NotNull Project project, @NotNull String name) {
        UiFile file = createFile(project, name);
        PsiElement identifier = file.getFirstChild();
        if (identifier != null) {
            return identifier;
        }
        throw new IllegalStateException("Failed to create identifier: " + name);
    }

    @NotNull
    public static UiVariableDeclaration createVariableDeclaration(@NotNull Project project,
                                                                   @NotNull String name,
                                                                   @NotNull String value) {
        String text = "@" + name + " = " + value + ";";
        UiFile file = createFile(project, text);
        UiVariableDeclaration declaration = findFirstChildOfType(file, UiVariableDeclaration.class);
        if (declaration != null) {
            return declaration;
        }
        throw new IllegalStateException("Failed to create variable declaration: " + text);
    }

    @NotNull
    public static UiComponentDefinition createComponentDefinition(@NotNull Project project,
                                                                   @NotNull String name,
                                                                   @NotNull String componentType) {
        String text = "@" + name + " = " + componentType + " { };";
        UiFile file = createFile(project, text);
        UiComponentDefinition definition = findFirstChildOfType(file, UiComponentDefinition.class);
        if (definition != null) {
            return definition;
        }
        throw new IllegalStateException("Failed to create component definition: " + text);
    }

    @NotNull
    public static UiVariable createVariable(@NotNull Project project, @NotNull String name) {
        String text = "$" + name;
        UiFile file = createFile(project, text);
        UiVariable variable = findFirstChildOfType(file, UiVariable.class);
        if (variable != null) {
            return variable;
        }
        throw new IllegalStateException("Failed to create variable: " + text);
    }

    @NotNull
    private static UiFile createFile(@NotNull Project project, @NotNull String text) {
        String fileName = "dummy.ui";
        return (UiFile) PsiFileFactory.getInstance(project)
                .createFileFromText(fileName, UiFileType.INSTANCE, text);
    }

    @SuppressWarnings("unchecked")
    private static <T extends PsiElement> T findFirstChildOfType(@NotNull PsiElement element,
                                                                  @NotNull Class<T> clazz) {
        PsiElement child = element.getFirstChild();
        while (child != null) {
            if (clazz.isInstance(child)) {
                return (T) child;
            }
            T result = findFirstChildOfType(child, clazz);
            if (result != null) {
                return result;
            }
            child = child.getNextSibling();
        }
        return null;
    }
}
