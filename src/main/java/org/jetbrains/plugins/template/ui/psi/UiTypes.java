// This is a generated file. Not intended for manual editing.
package org.jetbrains.plugins.template.ui.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.plugins.template.ui.psi.impl.*;

public interface UiTypes {

  IElementType ARRAY_LITERAL = new UiElementType("ARRAY_LITERAL");
  IElementType AT_COMPONENT_REF = new UiElementType("AT_COMPONENT_REF");
  IElementType AT_PROPERTY_OVERRIDE = new UiElementType("AT_PROPERTY_OVERRIDE");
  IElementType COMPONENT_BODY = new UiElementType("COMPONENT_BODY");
  IElementType COMPONENT_BODY_CONTENT = new UiElementType("COMPONENT_BODY_CONTENT");
  IElementType COMPONENT_DEFINITION = new UiElementType("COMPONENT_DEFINITION");
  IElementType COMPONENT_REFERENCE = new UiElementType("COMPONENT_REFERENCE");
  IElementType COMPONENT_USAGE = new UiElementType("COMPONENT_USAGE");
  IElementType FUNCTION_LIKE_STYLE = new UiElementType("FUNCTION_LIKE_STYLE");
  IElementType ID_DECLARATION = new UiElementType("ID_DECLARATION");
  IElementType IMPORT_STATEMENT = new UiElementType("IMPORT_STATEMENT");
  IElementType I_18_N_PROPERTY = new UiElementType("I_18_N_PROPERTY");
  IElementType LITERAL_VALUE = new UiElementType("LITERAL_VALUE");
  IElementType MATH_PROPERTY_LIST = new UiElementType("MATH_PROPERTY_LIST");
  IElementType PROPERTY = new UiElementType("PROPERTY");
  IElementType PROPERTY_LIST = new UiElementType("PROPERTY_LIST");
  IElementType PROPERTY_VALUE = new UiElementType("PROPERTY_VALUE");
  IElementType QUALIFIED_COMPONENT_REF = new UiElementType("QUALIFIED_COMPONENT_REF");
  IElementType SIMPLE_COMPONENT_REF = new UiElementType("SIMPLE_COMPONENT_REF");
  IElementType SPREAD_VALUE = new UiElementType("SPREAD_VALUE");
  IElementType STATEMENT = new UiElementType("STATEMENT");
  IElementType VALUE = new UiElementType("VALUE");
  IElementType VALUE_LIST = new UiElementType("VALUE_LIST");
  IElementType VARIABLE = new UiElementType("VARIABLE");
  IElementType VARIABLE_DECLARATION = new UiElementType("VARIABLE_DECLARATION");

  IElementType AT = new UiTokenType("@");
  IElementType BOOLEAN = new UiTokenType("BOOLEAN");
  IElementType COLON = new UiTokenType(":");
  IElementType COLOR = new UiTokenType("COLOR");
  IElementType COMMA = new UiTokenType(",");
  IElementType COMMENT = new UiTokenType("COMMENT");
  IElementType DOLLAR = new UiTokenType("$");
  IElementType DOT = new UiTokenType(".");
  IElementType EQUALS = new UiTokenType("=");
  IElementType HASH = new UiTokenType("#");
  IElementType IDENTIFIER = new UiTokenType("IDENTIFIER");
  IElementType LBRACE = new UiTokenType("{");
  IElementType LBRACKET = new UiTokenType("[");
  IElementType LPAREN = new UiTokenType("(");
  IElementType MATH_OPERATOR = new UiTokenType("regexp[+\\-*\\/%]");
  IElementType NUMBER = new UiTokenType("NUMBER");
  IElementType RBRACE = new UiTokenType("}");
  IElementType RBRACKET = new UiTokenType("]");
  IElementType RPAREN = new UiTokenType(")");
  IElementType SEMICOLON = new UiTokenType(";");
  IElementType SPREAD = new UiTokenType("...");
  IElementType STRING = new UiTokenType("STRING");
  IElementType _______________ = new UiTokenType("'([^'\\\\]|\\\\.)*'");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ARRAY_LITERAL) {
        return new UiArrayLiteralImpl(node);
      }
      else if (type == AT_COMPONENT_REF) {
        return new UiAtComponentRefImpl(node);
      }
      else if (type == AT_PROPERTY_OVERRIDE) {
        return new UiAtPropertyOverrideImpl(node);
      }
      else if (type == COMPONENT_BODY) {
        return new UiComponentBodyImpl(node);
      }
      else if (type == COMPONENT_BODY_CONTENT) {
        return new UiComponentBodyContentImpl(node);
      }
      else if (type == COMPONENT_DEFINITION) {
        return new UiComponentDefinitionImpl(node);
      }
      else if (type == COMPONENT_REFERENCE) {
        return new UiComponentReferenceImpl(node);
      }
      else if (type == COMPONENT_USAGE) {
        return new UiComponentUsageImpl(node);
      }
      else if (type == FUNCTION_LIKE_STYLE) {
        return new UiFunctionLikeStyleImpl(node);
      }
      else if (type == ID_DECLARATION) {
        return new UiIdDeclarationImpl(node);
      }
      else if (type == IMPORT_STATEMENT) {
        return new UiImportStatementImpl(node);
      }
      else if (type == I_18_N_PROPERTY) {
        return new UiI18NPropertyImpl(node);
      }
      else if (type == LITERAL_VALUE) {
        return new UiLiteralValueImpl(node);
      }
      else if (type == MATH_PROPERTY_LIST) {
        return new UiMathPropertyListImpl(node);
      }
      else if (type == PROPERTY) {
        return new UiPropertyImpl(node);
      }
      else if (type == PROPERTY_LIST) {
        return new UiPropertyListImpl(node);
      }
      else if (type == PROPERTY_VALUE) {
        return new UiPropertyValueImpl(node);
      }
      else if (type == QUALIFIED_COMPONENT_REF) {
        return new UiQualifiedComponentRefImpl(node);
      }
      else if (type == SIMPLE_COMPONENT_REF) {
        return new UiSimpleComponentRefImpl(node);
      }
      else if (type == SPREAD_VALUE) {
        return new UiSpreadValueImpl(node);
      }
      else if (type == STATEMENT) {
        return new UiStatementImpl(node);
      }
      else if (type == VALUE) {
        return new UiValueImpl(node);
      }
      else if (type == VALUE_LIST) {
        return new UiValueListImpl(node);
      }
      else if (type == VARIABLE) {
        return new UiVariableImpl(node);
      }
      else if (type == VARIABLE_DECLARATION) {
        return new UiVariableDeclarationImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
