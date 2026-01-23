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

public class UiPropertyImpl extends ASTWrapperPsiElement implements UiProperty {

  public UiPropertyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull UiVisitor visitor) {
    visitor.visitProperty(this);
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
  public @Nullable String getPropertyName() {
    return UiPsiImplUtil.getPropertyName(this);
  }

}
