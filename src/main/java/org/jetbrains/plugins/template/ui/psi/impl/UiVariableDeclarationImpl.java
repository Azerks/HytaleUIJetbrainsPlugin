// This is a generated file. Not intended for manual editing.
package org.jetbrains.plugins.template.ui.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.jetbrains.plugins.template.ui.psi.UiTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.jetbrains.plugins.template.ui.psi.*;

public class UiVariableDeclarationImpl extends ASTWrapperPsiElement implements UiVariableDeclaration {

  public UiVariableDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull UiVisitor visitor) {
    visitor.visitVariableDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof UiVisitor) accept((UiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public UiValue getValue() {
    return findChildByClass(UiValue.class);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

  @Override
  public @NotNull String getName() {
    return UiPsiImplUtil.getName(this);
  }

  @Override
  public @NotNull PsiElement setName(@NotNull String newName) {
    return UiPsiImplUtil.setName(this, newName);
  }

  @Override
  public @Nullable PsiElement getNameIdentifier() {
    return UiPsiImplUtil.getNameIdentifier(this);
  }

}
