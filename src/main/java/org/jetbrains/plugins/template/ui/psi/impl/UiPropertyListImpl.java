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

public class UiPropertyListImpl extends ASTWrapperPsiElement implements UiPropertyList {

  public UiPropertyListImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull UiVisitor visitor) {
    visitor.visitPropertyList(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof UiVisitor) accept((UiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<UiSpreadValue> getSpreadValueList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, UiSpreadValue.class);
  }

  @Override
  @NotNull
  public List<UiValue> getValueList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, UiValue.class);
  }

}
