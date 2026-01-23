package org.jetbrains.plugins.template.ui.psi;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.template.ui.psi.impl.*;
import org.jetbrains.plugins.template.ui.references.UiComponentReferenceImpl;
import org.jetbrains.plugins.template.ui.references.UiVariableReferenceImpl;

/**
 * Utility class providing implementation methods for PSI elements.
 * These methods are referenced in the BNF grammar file.
 */
public class UiPsiImplUtil {

    // ==================== Variable Declaration ====================

    @NotNull
    public static String getName(@NotNull UiVariableDeclaration element) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            PsiElement identifier = identifierNode.getPsi();
            if (identifier != null) {
                return identifier.getText();
            }
        }
        return "";
    }

    @NotNull
    public static PsiElement setName(@NotNull UiVariableDeclaration element, @NotNull String newName) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            PsiElement identifier = identifierNode.getPsi();
            if (identifier != null) {
                PsiElement newIdentifier = UiElementFactory.createIdentifier(element.getProject(), newName);
                identifier.replace(newIdentifier);
            }
        }
        return element;
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull UiVariableDeclaration element) {
        ASTNode node = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        return node != null ? node.getPsi() : null;
    }

    // ==================== Component Definition ====================

    @NotNull
    public static String getName(@NotNull UiComponentDefinition element) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            PsiElement identifier = identifierNode.getPsi();
            if (identifier != null) {
                return identifier.getText();
            }
        }
        return "";
    }

    @NotNull
    public static PsiElement setName(@NotNull UiComponentDefinition element, @NotNull String newName) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            PsiElement identifier = identifierNode.getPsi();
            if (identifier != null) {
                PsiElement newIdentifier = UiElementFactory.createIdentifier(element.getProject(), newName);
                identifier.replace(newIdentifier);
            }
        }
        return element;
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull UiComponentDefinition element) {
        ASTNode node = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        return node != null ? node.getPsi() : null;
    }

    // ==================== Variable (Variable Reference) ====================

    @NotNull
    public static String getName(@NotNull UiVariable element) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            PsiElement identifier = identifierNode.getPsi();
            if (identifier != null) {
                return identifier.getText();
            }
        }
        return "";
    }

    @NotNull
    public static PsiElement setName(@NotNull UiVariable element, @NotNull String newName) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            PsiElement identifier = identifierNode.getPsi();
            if (identifier != null) {
                PsiElement newIdentifier = UiElementFactory.createIdentifier(element.getProject(), newName);
                identifier.replace(newIdentifier);
            }
        }
        return element;
    }

    @NotNull
    public static PsiReference getReference(@NotNull UiVariable element) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            return new UiVariableReferenceImpl(element, new TextRange(1, element.getTextLength()));
        }
        return new UiVariableReferenceImpl(element, new TextRange(0, 0));
    }

    // ==================== ID Declaration ====================

    @NotNull
    public static String getName(@NotNull UiIdDeclaration element) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            PsiElement identifier = identifierNode.getPsi();
            if (identifier != null) {
                return identifier.getText();
            }
        }
        return "";
    }

    @NotNull
    public static PsiElement setName(@NotNull UiIdDeclaration element, @NotNull String newName) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            PsiElement identifier = identifierNode.getPsi();
            if (identifier != null) {
                PsiElement newIdentifier = UiElementFactory.createIdentifier(element.getProject(), newName);
                identifier.replace(newIdentifier);
            }
        }
        return element;
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull UiIdDeclaration element) {
        ASTNode node = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        return node != null ? node.getPsi() : null;
    }

    // ==================== Component Reference ====================

    @Nullable
    public static String getReferenceName(@NotNull UiComponentReference element) {
        UiSimpleComponentRef simpleRef = PsiTreeUtil.getChildOfType(element, UiSimpleComponentRef.class);
        if (simpleRef != null) {
            return simpleRef.getComponentName();
        }

        UiQualifiedComponentRef qualifiedRef = PsiTreeUtil.getChildOfType(element, UiQualifiedComponentRef.class);
        if (qualifiedRef != null) {
            return qualifiedRef.getComponentName();
        }

        return null;
    }

    @Nullable
    public static PsiElement getQualifier(@NotNull UiComponentReference element) {
        UiQualifiedComponentRef qualifiedRef = PsiTreeUtil.getChildOfType(element, UiQualifiedComponentRef.class);
        if (qualifiedRef != null) {
            return qualifiedRef.getVariable();
        }
        return null;
    }

    // ==================== Qualified Component Reference ====================

    @Nullable
    public static String getComponentName(@NotNull UiQualifiedComponentRef element) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            PsiElement identifier = identifierNode.getPsi();
            if (identifier != null) {
                return identifier.getText();
            }
        }
        return null;
    }

    // ==================== Simple Component Reference ====================

    @Nullable
    public static String getComponentName(@NotNull UiSimpleComponentRef element) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            PsiElement identifier = identifierNode.getPsi();
            if (identifier != null) {
                return identifier.getText();
            }
        }
        return null;
    }

    // ==================== Property ====================

    @Nullable
    public static String getPropertyName(@NotNull UiProperty element) {
        ASTNode identifierNode = element.getNode().findChildByType(UiTypes.IDENTIFIER);
        if (identifierNode != null) {
            PsiElement identifier = identifierNode.getPsi();
            if (identifier != null) {
                return identifier.getText();
            }
        }
        return null;
    }

    // ==================== Import Statement ====================

    @Nullable
    public static PsiFile getImportedFile(@NotNull UiImportStatement element) {
        ASTNode stringNode = element.getNode().findChildByType(UiTypes.STRING);
        if (stringNode != null) {
            PsiElement stringLiteral = stringNode.getPsi();
            if (stringLiteral != null) {
                String path = stringLiteral.getText();
                // Remove quotes
                path = path.substring(1, path.length() - 1);
                // TODO: Resolve the file path
            }
        }
        return null;
    }
}
