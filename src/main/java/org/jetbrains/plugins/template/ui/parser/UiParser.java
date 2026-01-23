// This is a generated file. Not intended for manual editing.
package org.jetbrains.plugins.template.ui.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.jetbrains.plugins.template.ui.psi.UiTypes.*;
import static org.jetbrains.plugins.template.ui.parser.UiParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class UiParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return uiFile(b, l + 1);
  }

  /* ********************************************************** */
  // LBRACKET valueList? RBRACKET
  public static boolean arrayLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayLiteral")) return false;
    if (!nextTokenIs(b, LBRACKET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ARRAY_LITERAL, null);
    r = consumeToken(b, LBRACKET);
    p = r; // pin = 1
    r = r && report_error_(b, arrayLiteral_1(b, l + 1));
    r = p && consumeToken(b, RBRACKET) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // valueList?
  private static boolean arrayLiteral_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayLiteral_1")) return false;
    valueList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // AT IDENTIFIER
  public static boolean atComponentRef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atComponentRef")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, AT_COMPONENT_REF, null);
    r = consumeTokens(b, 1, AT, IDENTIFIER);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // componentDefinition | variableDeclaration
  static boolean atDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atDeclaration")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r;
    r = componentDefinition(b, l + 1);
    if (!r) r = variableDeclaration(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // AT IDENTIFIER EQUALS value SEMICOLON
  public static boolean atPropertyOverride(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atPropertyOverride")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, AT_PROPERTY_OVERRIDE, null);
    r = consumeTokens(b, 3, AT, IDENTIFIER, EQUALS);
    p = r; // pin = 3
    r = r && report_error_(b, value(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // LBRACE componentBodyContent* RBRACE
  public static boolean componentBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "componentBody")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_BODY, null);
    r = consumeToken(b, LBRACE);
    p = r; // pin = 1
    r = r && report_error_(b, componentBody_1(b, l + 1));
    r = p && consumeToken(b, RBRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // componentBodyContent*
  private static boolean componentBody_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "componentBody_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!componentBodyContent(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "componentBody_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // property | atPropertyOverride
  //                        | componentUsage
  public static boolean componentBodyContent(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "componentBodyContent")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_BODY_CONTENT, "<component body content>");
    r = property(b, l + 1);
    if (!r) r = atPropertyOverride(b, l + 1);
    if (!r) r = componentUsage(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // AT IDENTIFIER EQUALS componentReference componentBody SEMICOLON
  public static boolean componentDefinition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "componentDefinition")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, AT, IDENTIFIER, EQUALS);
    r = r && componentReference(b, l + 1);
    r = r && componentBody(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, COMPONENT_DEFINITION, r);
    return r;
  }

  /* ********************************************************** */
  // qualifiedComponentRef | atComponentRef | simpleComponentRef
  public static boolean componentReference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "componentReference")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_REFERENCE, "<component reference>");
    r = qualifiedComponentRef(b, l + 1);
    if (!r) r = atComponentRef(b, l + 1);
    if (!r) r = simpleComponentRef(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (componentReference idDeclaration? | idDeclaration) componentBody
  public static boolean componentUsage(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "componentUsage")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_USAGE, "<component usage>");
    r = componentUsage_0(b, l + 1);
    p = r; // pin = 1
    r = r && componentBody(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // componentReference idDeclaration? | idDeclaration
  private static boolean componentUsage_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "componentUsage_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = componentUsage_0_0(b, l + 1);
    if (!r) r = idDeclaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // componentReference idDeclaration?
  private static boolean componentUsage_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "componentUsage_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = componentReference(b, l + 1);
    r = r && componentUsage_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // idDeclaration?
  private static boolean componentUsage_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "componentUsage_0_0_1")) return false;
    idDeclaration(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER propertyValue
  public static boolean functionLikeStyle(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionLikeStyle")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && propertyValue(b, l + 1);
    exit_section_(b, m, FUNCTION_LIKE_STYLE, r);
    return r;
  }

  /* ********************************************************** */
  // MATH_OPERATOR IDENTIFIER (DOT IDENTIFIER)*
  public static boolean i18nProperty(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "i18nProperty")) return false;
    if (!nextTokenIs(b, MATH_OPERATOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, MATH_OPERATOR, IDENTIFIER);
    r = r && i18nProperty_2(b, l + 1);
    exit_section_(b, m, I_18_N_PROPERTY, r);
    return r;
  }

  // (DOT IDENTIFIER)*
  private static boolean i18nProperty_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "i18nProperty_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!i18nProperty_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "i18nProperty_2", c)) break;
    }
    return true;
  }

  // DOT IDENTIFIER
  private static boolean i18nProperty_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "i18nProperty_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // HASH IDENTIFIER
  public static boolean idDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "idDeclaration")) return false;
    if (!nextTokenIs(b, HASH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ID_DECLARATION, null);
    r = consumeTokens(b, 1, HASH, IDENTIFIER);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // variable EQUALS STRING SEMICOLON
  public static boolean importStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importStatement")) return false;
    if (!nextTokenIs(b, DOLLAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IMPORT_STATEMENT, null);
    r = variable(b, l + 1);
    r = r && consumeTokens(b, 1, EQUALS, STRING, SEMICOLON);
    p = r; // pin = 2
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // STRING | NUMBER | COLOR | BOOLEAN | IDENTIFIER
  public static boolean literalValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literalValue")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LITERAL_VALUE, "<literal value>");
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, COLOR);
    if (!r) r = consumeToken(b, BOOLEAN);
    if (!r) r = consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // MATH_OPERATOR NUMBER | NUMBER (MATH_OPERATOR MATH_OPERATOR? NUMBER)*
  public static boolean mathPropertyList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mathPropertyList")) return false;
    if (!nextTokenIs(b, "<math property list>", MATH_OPERATOR, NUMBER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATH_PROPERTY_LIST, "<math property list>");
    r = parseTokens(b, 0, MATH_OPERATOR, NUMBER);
    if (!r) r = mathPropertyList_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NUMBER (MATH_OPERATOR MATH_OPERATOR? NUMBER)*
  private static boolean mathPropertyList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mathPropertyList_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NUMBER);
    r = r && mathPropertyList_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (MATH_OPERATOR MATH_OPERATOR? NUMBER)*
  private static boolean mathPropertyList_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mathPropertyList_1_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!mathPropertyList_1_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "mathPropertyList_1_1", c)) break;
    }
    return true;
  }

  // MATH_OPERATOR MATH_OPERATOR? NUMBER
  private static boolean mathPropertyList_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mathPropertyList_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MATH_OPERATOR);
    r = r && mathPropertyList_1_1_0_1(b, l + 1);
    r = r && consumeToken(b, NUMBER);
    exit_section_(b, m, null, r);
    return r;
  }

  // MATH_OPERATOR?
  private static boolean mathPropertyList_1_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mathPropertyList_1_1_0_1")) return false;
    consumeToken(b, MATH_OPERATOR);
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER COLON value SEMICOLON
  public static boolean property(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PROPERTY, null);
    r = consumeTokens(b, 2, IDENTIFIER, COLON);
    p = r; // pin = 2
    r = r && report_error_(b, value(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IDENTIFIER COLON value
  static boolean propertyItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyItem")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeTokens(b, 2, IDENTIFIER, COLON);
    p = r; // pin = 2
    r = r && value(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // propertyListElement (COMMA propertyListElement)*
  public static boolean propertyList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PROPERTY_LIST, "<property list>");
    r = propertyListElement(b, l + 1);
    r = r && propertyList_1(b, l + 1);
    exit_section_(b, l, m, r, false, UiParser::property_recover);
    return r;
  }

  // (COMMA propertyListElement)*
  private static boolean propertyList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyList_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!propertyList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "propertyList_1", c)) break;
    }
    return true;
  }

  // COMMA propertyListElement
  private static boolean propertyList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && propertyListElement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // spreadValue | propertyItem
  static boolean propertyListElement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyListElement")) return false;
    if (!nextTokenIs(b, "", IDENTIFIER, SPREAD)) return false;
    boolean r;
    r = spreadValue(b, l + 1);
    if (!r) r = propertyItem(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // LPAREN propertyList RPAREN
  public static boolean propertyValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyValue")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PROPERTY_VALUE, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, propertyList(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // !(RPAREN | RBRACE | RBRACKET)
  static boolean property_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !property_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RPAREN | RBRACE | RBRACKET
  private static boolean property_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_recover_0")) return false;
    boolean r;
    r = consumeToken(b, RPAREN);
    if (!r) r = consumeToken(b, RBRACE);
    if (!r) r = consumeToken(b, RBRACKET);
    return r;
  }

  /* ********************************************************** */
  // variable DOT AT IDENTIFIER
  public static boolean qualifiedComponentRef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qualifiedComponentRef")) return false;
    if (!nextTokenIs(b, DOLLAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, QUALIFIED_COMPONENT_REF, null);
    r = variable(b, l + 1);
    r = r && consumeTokens(b, 2, DOT, AT, IDENTIFIER);
    p = r; // pin = 3
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean simpleComponentRef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simpleComponentRef")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, SIMPLE_COMPONENT_REF, r);
    return r;
  }

  /* ********************************************************** */
  // SPREAD componentReference
  public static boolean spreadValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "spreadValue")) return false;
    if (!nextTokenIs(b, SPREAD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SPREAD_VALUE, null);
    r = consumeToken(b, SPREAD);
    p = r; // pin = 1
    r = r && componentReference(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // atDeclaration
  //             | importStatement
  //             | componentUsage
  public static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEMENT, "<statement>");
    r = atDeclaration(b, l + 1);
    if (!r) r = importStatement(b, l + 1);
    if (!r) r = componentUsage(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // statement*
  static boolean uiFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "uiFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!statement(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "uiFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // propertyValue
  //         | arrayLiteral
  //         | spreadValue
  //         | qualifiedComponentRef | atComponentRef
  //         | functionLikeStyle
  //         | mathPropertyList
  //         | i18nProperty
  //         | literalValue
  public static boolean value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VALUE, "<value>");
    r = propertyValue(b, l + 1);
    if (!r) r = arrayLiteral(b, l + 1);
    if (!r) r = spreadValue(b, l + 1);
    if (!r) r = qualifiedComponentRef(b, l + 1);
    if (!r) r = atComponentRef(b, l + 1);
    if (!r) r = functionLikeStyle(b, l + 1);
    if (!r) r = mathPropertyList(b, l + 1);
    if (!r) r = i18nProperty(b, l + 1);
    if (!r) r = literalValue(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // value (COMMA value)*
  public static boolean valueList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "valueList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VALUE_LIST, "<value list>");
    r = value(b, l + 1);
    r = r && valueList_1(b, l + 1);
    exit_section_(b, l, m, r, false, UiParser::value_recover);
    return r;
  }

  // (COMMA value)*
  private static boolean valueList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "valueList_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!valueList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "valueList_1", c)) break;
    }
    return true;
  }

  // COMMA value
  private static boolean valueList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "valueList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(RBRACKET | RPAREN | RBRACE | COMMA)
  static boolean value_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !value_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACKET | RPAREN | RBRACE | COMMA
  private static boolean value_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value_recover_0")) return false;
    boolean r;
    r = consumeToken(b, RBRACKET);
    if (!r) r = consumeToken(b, RPAREN);
    if (!r) r = consumeToken(b, RBRACE);
    if (!r) r = consumeToken(b, COMMA);
    return r;
  }

  /* ********************************************************** */
  // DOLLAR IDENTIFIER
  public static boolean variable(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable")) return false;
    if (!nextTokenIs(b, DOLLAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, VARIABLE, null);
    r = consumeTokens(b, 1, DOLLAR, IDENTIFIER);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // AT IDENTIFIER EQUALS value SEMICOLON
  public static boolean variableDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableDeclaration")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, VARIABLE_DECLARATION, null);
    r = consumeTokens(b, 3, AT, IDENTIFIER, EQUALS);
    p = r; // pin = 3
    r = r && report_error_(b, value(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

}
