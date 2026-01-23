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
MATH=[+\-*\/%]

%%

<YYINITIAL> {
    {WHITE_SPACE}+              { return TokenType.WHITE_SPACE; }
    {LINE_COMMENT}              { return UiTypes.COMMENT; }
    {BLOCK_COMMENT}             { return UiTypes.COMMENT; }

    // Component types
    "Group"                     { return UiTypes.IDENTIFIER; }
    "Label"                     { return UiTypes.IDENTIFIER; }
    "Button"                    { return UiTypes.IDENTIFIER; }
    "TextButton"                { return UiTypes.IDENTIFIER; }
    "CheckBox"                  { return UiTypes.IDENTIFIER; }
    "TextField"                 { return UiTypes.IDENTIFIER; }
    "NumberField"               { return UiTypes.IDENTIFIER; }
    "DropdownBox"               { return UiTypes.IDENTIFIER; }
    "ColorPickerDropdownBox"    { return UiTypes.IDENTIFIER; }
    "Sprite"                    { return UiTypes.IDENTIFIER; }
    "CompactTextField"          { return UiTypes.IDENTIFIER; }
    "BackButton"                { return UiTypes.IDENTIFIER; }
    "FileDropdownBox"           { return UiTypes.IDENTIFIER; }
    "Panel"                     { return UiTypes.IDENTIFIER; }

    // Property keywords
    "Anchor"                    { return UiTypes.IDENTIFIER; }
    "Style"                     { return UiTypes.IDENTIFIER; }
    "LayoutMode"                { return UiTypes.IDENTIFIER; }
    "Padding"                   { return UiTypes.IDENTIFIER; }
    "Background"                { return UiTypes.IDENTIFIER; }
    "Text"                      { return UiTypes.IDENTIFIER; }
    "FlexWeight"                { return UiTypes.IDENTIFIER; }
    "MaxLength"                 { return UiTypes.IDENTIFIER; }
    "ScrollbarStyle"            { return UiTypes.IDENTIFIER; }
    "TexturePath"               { return UiTypes.IDENTIFIER; }
    "Border"                    { return UiTypes.IDENTIFIER; }

    // Property keywords
    "Left"                      { return UiTypes.IDENTIFIER; }
    "Right"                     { return UiTypes.IDENTIFIER; }
    "Top"                       { return UiTypes.IDENTIFIER; }
    "Bottom"                    { return UiTypes.IDENTIFIER; }
    "Width"                     { return UiTypes.IDENTIFIER; }
    "Height"                    { return UiTypes.IDENTIFIER; }
    "Full"                      { return UiTypes.IDENTIFIER; }
    "Center"                    { return UiTypes.IDENTIFIER; }
    "FontSize"                  { return UiTypes.IDENTIFIER; }
    "TextColor"                 { return UiTypes.IDENTIFIER; }
    "RenderBold"                { return UiTypes.IDENTIFIER; }
    "VerticalAlignment"         { return UiTypes.IDENTIFIER; }
    "HorizontalAlignment"       { return UiTypes.IDENTIFIER; }
    "Alignment"                 { return UiTypes.IDENTIFIER; }
    "Wrap"                      { return UiTypes.IDENTIFIER; }

    // Layout modes
    "TopScrolling"              { return UiTypes.IDENTIFIER; }

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
    {MATH}                      { return UiTypes.MATH_OPERATOR; }
    {STRING}                    { return UiTypes.STRING; }
    {NUMBER}                    { return UiTypes.NUMBER; }
    {IDENTIFIER}                { return UiTypes.IDENTIFIER; }
}

[^] { return TokenType.BAD_CHARACTER; }
