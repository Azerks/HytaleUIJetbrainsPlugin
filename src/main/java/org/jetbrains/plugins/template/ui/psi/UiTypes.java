package org.jetbrains.plugins.template.ui.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.plugins.template.ui.UiLanguage;

public interface UiTypes {

    IElementType COMMENT = new UiTokenType("COMMENT");
    IElementType KEYWORD = new UiTokenType("KEYWORD");
    IElementType PROPERTY = new UiTokenType("PROPERTY");
    IElementType VALUE = new UiTokenType("VALUE");
    IElementType BOOLEAN = new UiTokenType("BOOLEAN");
    IElementType IDENTIFIER = new UiTokenType("IDENTIFIER");
    IElementType NUMBER = new UiTokenType("NUMBER");
    IElementType STRING = new UiTokenType("STRING");
    IElementType COLOR = new UiTokenType("COLOR");

    IElementType EQUALS = new UiTokenType("EQUALS");
    IElementType COLON = new UiTokenType("COLON");
    IElementType SEMICOLON = new UiTokenType("SEMICOLON");
    IElementType COMMA = new UiTokenType("COMMA");
    IElementType DOT = new UiTokenType("DOT");
    IElementType LBRACE = new UiTokenType("LBRACE");
    IElementType RBRACE = new UiTokenType("RBRACE");
    IElementType LPAREN = new UiTokenType("LPAREN");
    IElementType RPAREN = new UiTokenType("RPAREN");
    IElementType AT = new UiTokenType("AT");
    IElementType DOLLAR = new UiTokenType("DOLLAR");
    IElementType HASH = new UiTokenType("HASH");
    IElementType SPREAD = new UiTokenType("SPREAD");

    // Composite element types for structure
    IElementType BLOCK = new UiElementType("BLOCK");
}
