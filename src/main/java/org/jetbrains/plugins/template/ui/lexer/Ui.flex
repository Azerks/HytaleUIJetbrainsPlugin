package org.jetbrains.plugins.template.ui.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.plugins.template.ui.psi.UiTypes;
import com.intellij.psi.TokenType;

%%

%class UiLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF=\R
WHITE_SPACE=[\ \n\t\f]
LINE_COMMENT="//"[^\r\n]*
BLOCK_COMMENT="/*" [^*] ~"*/" | "/*" "*"+ "/"

IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_]*
NUMBER=[0-9]+(\.[0-9]+)?
HEX_COLOR=#[0-9a-fA-F]{6}(\([0-9.]+\))?
STRING=\"([^\"\\\r\n]|\\.)*\"

%%

<YYINITIAL> {
    {WHITE_SPACE}+              { return TokenType.WHITE_SPACE; }
    {LINE_COMMENT}              { return UiTypes.COMMENT; }
    {BLOCK_COMMENT}             { return UiTypes.COMMENT; }

    // Keywords
    "Anchor"                    { return UiTypes.KEYWORD; }
    "Style"                     { return UiTypes.KEYWORD; }
    "LayoutMode"                { return UiTypes.KEYWORD; }
    "Padding"                   { return UiTypes.KEYWORD; }
    "Background"                { return UiTypes.KEYWORD; }
    "Text"                      { return UiTypes.KEYWORD; }
    "FlexWeight"                { return UiTypes.KEYWORD; }
    "MaxLength"                 { return UiTypes.KEYWORD; }
    "ScrollbarStyle"            { return UiTypes.KEYWORD; }
    "TexturePath"               { return UiTypes.KEYWORD; }
    "Border"                    { return UiTypes.KEYWORD; }

    // Property keywords
    "Left"                      { return UiTypes.PROPERTY; }
    "Right"                     { return UiTypes.PROPERTY; }
    "Top"                       { return UiTypes.PROPERTY; }
    "Bottom"                    { return UiTypes.PROPERTY; }
    "Width"                     { return UiTypes.PROPERTY; }
    "Height"                    { return UiTypes.PROPERTY; }
    "Full"                      { return UiTypes.PROPERTY; }
    "Center"                    { return UiTypes.PROPERTY; }
    "FontSize"                  { return UiTypes.PROPERTY; }
    "TextColor"                 { return UiTypes.PROPERTY; }
    "RenderBold"                { return UiTypes.PROPERTY; }
    "VerticalAlignment"         { return UiTypes.PROPERTY; }
    "Wrap"                      { return UiTypes.PROPERTY; }

    // Layout modes
    "TopScrolling"              { return UiTypes.VALUE; }

    // Boolean values
    "true"                      { return UiTypes.BOOLEAN; }
    "false"                     { return UiTypes.BOOLEAN; }

    // Operators and separators
    "="                         { return UiTypes.EQUALS; }
    ":"                         { return UiTypes.COLON; }
    ";"                         { return UiTypes.SEMICOLON; }
    ","                         { return UiTypes.COMMA; }
    "."                         { return UiTypes.DOT; }
    "{"                         { return UiTypes.LBRACE; }
    "}"                         { return UiTypes.RBRACE; }
    "("                         { return UiTypes.LPAREN; }
    ")"                         { return UiTypes.RPAREN; }
    "["                         { return UiTypes.LBRACKET; }
    "]"                         { return UiTypes.RBRACKET; }
    "@"                         { return UiTypes.AT; }
    "$"                         { return UiTypes.DOLLAR; }
    "#"                         { return UiTypes.HASH; }
    "..."                       { return UiTypes.SPREAD; }

    {HEX_COLOR}                 { return UiTypes.COLOR; }
    {STRING}                    { return UiTypes.STRING; }
    {NUMBER}                    { return UiTypes.NUMBER; }
    {IDENTIFIER}                { return UiTypes.IDENTIFIER; }
}

[^] { return TokenType.BAD_CHARACTER; }
