// This is a generated file. Not intended for manual editing.
package org.jetbrains.plugins.template.ui.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;

public interface UiIdDeclaration extends PsiNameIdentifierOwner {

  @Nullable
  PsiElement getIdentifier();

  @NotNull String getName();

  @NotNull PsiElement setName(@NotNull String newName);

  @Nullable PsiElement getNameIdentifier();

}
