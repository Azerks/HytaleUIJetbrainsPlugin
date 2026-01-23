// This is a generated file. Not intended for manual editing.
package org.jetbrains.plugins.template.ui.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface UiQualifiedComponentRef extends PsiElement {

  @NotNull
  UiVariable getVariable();

  @Nullable
  PsiElement getIdentifier();

  @Nullable String getComponentName();

}
