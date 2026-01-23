// This is a generated file. Not intended for manual editing.
package org.jetbrains.plugins.template.ui.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;

public interface UiVariable extends PsiNamedElement {

  @Nullable
  PsiElement getIdentifier();

  @NotNull String getName();

  @NotNull PsiElement setName(@NotNull String newName);

  @NotNull PsiReference getReference();

}
