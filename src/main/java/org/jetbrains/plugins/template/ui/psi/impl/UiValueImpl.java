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

public class UiValueImpl extends ASTWrapperPsiElement implements UiValue {

  public UiValueImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull UiVisitor visitor) {
    visitor.visitValue(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof UiVisitor) accept((UiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public UiArrayLiteral getArrayLiteral() {
    return findChildByClass(UiArrayLiteral.class);
  }

  @Override
  @Nullable
  public UiAtComponentRef getAtComponentRef() {
    return findChildByClass(UiAtComponentRef.class);
  }

  @Override
  @Nullable
  public UiFunctionLikeStyle getFunctionLikeStyle() {
    return findChildByClass(UiFunctionLikeStyle.class);
  }

  @Override
  @Nullable
  public UiI18NProperty getI18NProperty() {
    return findChildByClass(UiI18NProperty.class);
  }

  @Override
  @Nullable
  public UiLiteralValue getLiteralValue() {
    return findChildByClass(UiLiteralValue.class);
  }

  @Override
  @Nullable
  public UiMathPropertyList getMathPropertyList() {
    return findChildByClass(UiMathPropertyList.class);
  }

  @Override
  @Nullable
  public UiPropertyValue getPropertyValue() {
    return findChildByClass(UiPropertyValue.class);
  }

  @Override
  @Nullable
  public UiQualifiedComponentRef getQualifiedComponentRef() {
    return findChildByClass(UiQualifiedComponentRef.class);
  }

  @Override
  @Nullable
  public UiSpreadValue getSpreadValue() {
    return findChildByClass(UiSpreadValue.class);
  }

}
