package org.jetbrains.plugins.template.ui.parser;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class UiPsiElement extends ASTWrapperPsiElement {

    public UiPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
