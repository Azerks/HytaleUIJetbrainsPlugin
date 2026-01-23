// This is a generated file. Not intended for manual editing.
package org.jetbrains.plugins.template.ui.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface UiStatement extends PsiElement {

  @Nullable
  UiComponentDefinition getComponentDefinition();

  @Nullable
  UiComponentUsage getComponentUsage();

  @Nullable
  UiImportStatement getImportStatement();

  @Nullable
  UiVariableDeclaration getVariableDeclaration();

}
