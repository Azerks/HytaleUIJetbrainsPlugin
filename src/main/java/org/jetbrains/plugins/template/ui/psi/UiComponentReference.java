// This is a generated file. Not intended for manual editing.
package org.jetbrains.plugins.template.ui.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface UiComponentReference extends PsiElement {

  @Nullable
  UiAtComponentRef getAtComponentRef();

  @Nullable
  UiQualifiedComponentRef getQualifiedComponentRef();

  @Nullable
  UiSimpleComponentRef getSimpleComponentRef();

  @Nullable String getReferenceName();

  @Nullable PsiElement getQualifier();

}
