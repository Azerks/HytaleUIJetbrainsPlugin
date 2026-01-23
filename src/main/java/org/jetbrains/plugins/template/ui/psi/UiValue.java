// This is a generated file. Not intended for manual editing.
package org.jetbrains.plugins.template.ui.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface UiValue extends PsiElement {

  @Nullable
  UiArrayLiteral getArrayLiteral();

  @Nullable
  UiAtComponentRef getAtComponentRef();

  @Nullable
  UiFunctionLikeStyle getFunctionLikeStyle();

  @Nullable
  UiI18NProperty getI18NProperty();

  @Nullable
  UiLiteralValue getLiteralValue();

  @Nullable
  UiMathPropertyList getMathPropertyList();

  @Nullable
  UiPropertyValue getPropertyValue();

  @Nullable
  UiQualifiedComponentRef getQualifiedComponentRef();

  @Nullable
  UiSpreadValue getSpreadValue();

}
