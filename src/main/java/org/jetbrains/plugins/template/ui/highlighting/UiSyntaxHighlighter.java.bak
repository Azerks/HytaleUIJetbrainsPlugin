package org.jetbrains.plugins.template.ui.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.lexer.UiLexerAdapter;
import org.jetbrains.plugins.template.ui.psi.UiTypes;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class UiSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("UI_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);

    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey("UI_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);

    public static final TextAttributesKey COMPONENT =
            createTextAttributesKey("UI_COMPONENT", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);

    public static final TextAttributesKey PROPERTY =
            createTextAttributesKey("UI_PROPERTY", DefaultLanguageHighlighterColors.INSTANCE_FIELD);

    public static final TextAttributesKey VALUE =
            createTextAttributesKey("UI_VALUE", DefaultLanguageHighlighterColors.CONSTANT);

    public static final TextAttributesKey BOOLEAN =
            createTextAttributesKey("UI_BOOLEAN", DefaultLanguageHighlighterColors.KEYWORD);

    public static final TextAttributesKey IDENTIFIER =
            createTextAttributesKey("UI_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);

    public static final TextAttributesKey NUMBER =
            createTextAttributesKey("UI_NUMBER", DefaultLanguageHighlighterColors.NUMBER);

    public static final TextAttributesKey STRING =
            createTextAttributesKey("UI_STRING", DefaultLanguageHighlighterColors.STRING);

    public static final TextAttributesKey COLOR =
            createTextAttributesKey("UI_COLOR", DefaultLanguageHighlighterColors.NUMBER);

    public static final TextAttributesKey OPERATOR =
            createTextAttributesKey("UI_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);

    public static final TextAttributesKey BRACES =
            createTextAttributesKey("UI_BRACES", DefaultLanguageHighlighterColors.BRACES);

    public static final TextAttributesKey BRACKETS =
            createTextAttributesKey("UI_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);

    public static final TextAttributesKey PARENTHESES =
            createTextAttributesKey("UI_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);

    public static final TextAttributesKey SPECIAL =
            createTextAttributesKey("UI_SPECIAL", DefaultLanguageHighlighterColors.METADATA);

    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("UI_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] COMPONENT_KEYS = new TextAttributesKey[]{COMPONENT};
    private static final TextAttributesKey[] PROPERTY_KEYS = new TextAttributesKey[]{PROPERTY};
    private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
    private static final TextAttributesKey[] BOOLEAN_KEYS = new TextAttributesKey[]{BOOLEAN};
    private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] COLOR_KEYS = new TextAttributesKey[]{COLOR};
    private static final TextAttributesKey[] OPERATOR_KEYS = new TextAttributesKey[]{OPERATOR};
    private static final TextAttributesKey[] BRACES_KEYS = new TextAttributesKey[]{BRACES};
    private static final TextAttributesKey[] BRACKETS_KEYS = new TextAttributesKey[]{BRACKETS};
    private static final TextAttributesKey[] PARENTHESES_KEYS = new TextAttributesKey[]{PARENTHESES};
    private static final TextAttributesKey[] SPECIAL_KEYS = new TextAttributesKey[]{SPECIAL};
    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new UiLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(UiTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(UiTypes.KEYWORD)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(UiTypes.COMPONENT)) {
            return COMPONENT_KEYS;
        } else if (tokenType.equals(UiTypes.PROPERTY)) {
            return PROPERTY_KEYS;
        } else if (tokenType.equals(UiTypes.VALUE)) {
            return VALUE_KEYS;
        } else if (tokenType.equals(UiTypes.BOOLEAN)) {
            return BOOLEAN_KEYS;
        } else if (tokenType.equals(UiTypes.IDENTIFIER)) {
            return IDENTIFIER_KEYS;
        } else if (tokenType.equals(UiTypes.NUMBER)) {
            return NUMBER_KEYS;
        } else if (tokenType.equals(UiTypes.STRING)) {
            return STRING_KEYS;
        } else if (tokenType.equals(UiTypes.COLOR)) {
            return COLOR_KEYS;
        } else if (tokenType.equals(UiTypes.EQUALS) || tokenType.equals(UiTypes.COLON) ||
                   tokenType.equals(UiTypes.SEMICOLON) || tokenType.equals(UiTypes.COMMA) ||
                   tokenType.equals(UiTypes.DOT)) {
            return OPERATOR_KEYS;
        } else if (tokenType.equals(UiTypes.LBRACE) || tokenType.equals(UiTypes.RBRACE)) {
            return BRACES_KEYS;
        } else if (tokenType.equals(UiTypes.LPAREN) || tokenType.equals(UiTypes.RPAREN)) {
            return PARENTHESES_KEYS;
        } else if (tokenType.equals(UiTypes.AT) || tokenType.equals(UiTypes.DOLLAR) ||
                   tokenType.equals(UiTypes.HASH) || tokenType.equals(UiTypes.SPREAD)) {
            return SPECIAL_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}
