// This is a generated file. Not intended for manual editing.
package org.jetbrains.plugins.template.ui.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiNamedElement;

public class UiVisitor extends PsiElementVisitor {

  public void visitArrayLiteral(@NotNull UiArrayLiteral o) {
    visitPsiElement(o);
  }

  public void visitAtComponentRef(@NotNull UiAtComponentRef o) {
    visitPsiElement(o);
  }

  public void visitAtPropertyOverride(@NotNull UiAtPropertyOverride o) {
    visitPsiElement(o);
  }

  public void visitComponentBody(@NotNull UiComponentBody o) {
    visitPsiElement(o);
  }

  public void visitComponentBodyContent(@NotNull UiComponentBodyContent o) {
    visitPsiElement(o);
  }

  public void visitComponentDefinition(@NotNull UiComponentDefinition o) {
    visitPsiNameIdentifierOwner(o);
  }

  public void visitComponentReference(@NotNull UiComponentReference o) {
    visitPsiElement(o);
  }

  public void visitComponentUsage(@NotNull UiComponentUsage o) {
    visitPsiElement(o);
  }

  public void visitFunctionLikeStyle(@NotNull UiFunctionLikeStyle o) {
    visitPsiElement(o);
  }

  public void visitI18NProperty(@NotNull UiI18NProperty o) {
    visitPsiElement(o);
  }

  public void visitIdDeclaration(@NotNull UiIdDeclaration o) {
    visitPsiNameIdentifierOwner(o);
  }

  public void visitImportStatement(@NotNull UiImportStatement o) {
    visitPsiElement(o);
  }

  public void visitLiteralValue(@NotNull UiLiteralValue o) {
    visitPsiElement(o);
  }

  public void visitMathPropertyList(@NotNull UiMathPropertyList o) {
    visitPsiElement(o);
  }

  public void visitProperty(@NotNull UiProperty o) {
    visitPsiElement(o);
  }

  public void visitPropertyList(@NotNull UiPropertyList o) {
    visitPsiElement(o);
  }

  public void visitPropertyValue(@NotNull UiPropertyValue o) {
    visitPsiElement(o);
  }

  public void visitQualifiedComponentRef(@NotNull UiQualifiedComponentRef o) {
    visitPsiElement(o);
  }

  public void visitSimpleComponentRef(@NotNull UiSimpleComponentRef o) {
    visitPsiElement(o);
  }

  public void visitSpreadValue(@NotNull UiSpreadValue o) {
    visitPsiElement(o);
  }

  public void visitStatement(@NotNull UiStatement o) {
    visitPsiElement(o);
  }

  public void visitValue(@NotNull UiValue o) {
    visitPsiElement(o);
  }

  public void visitValueList(@NotNull UiValueList o) {
    visitPsiElement(o);
  }

  public void visitVariable(@NotNull UiVariable o) {
    visitPsiNamedElement(o);
  }

  public void visitVariableDeclaration(@NotNull UiVariableDeclaration o) {
    visitPsiNameIdentifierOwner(o);
  }

  public void visitPsiNameIdentifierOwner(@NotNull PsiNameIdentifierOwner o) {
    visitElement(o);
  }

  public void visitPsiNamedElement(@NotNull PsiNamedElement o) {
    visitElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
