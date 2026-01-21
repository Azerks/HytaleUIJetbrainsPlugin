package org.jetbrains.plugins.template.ui.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.psi.UiTypes;

public class UiParser implements PsiParser {

    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        PsiBuilder.Marker rootMarker = builder.mark();

        parseContent(builder);

        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

    private void parseContent(PsiBuilder builder) {
        while (!builder.eof()) {
            IElementType tokenType = builder.getTokenType();

            if (tokenType == UiTypes.LBRACE) {
                parseComponentBody(builder);
            } else if (tokenType == UiTypes.RBRACE) {
                // Unmatched closing brace
                PsiBuilder.Marker errorMarker = builder.mark();
                builder.advanceLexer();
                errorMarker.error("Unmatched closing brace '}'");
            } else if (tokenType == UiTypes.LPAREN) {
                parsePropertyValue(builder);
            } else if (tokenType == UiTypes.RPAREN) {
                // Unmatched closing parenthesis
                PsiBuilder.Marker errorMarker = builder.mark();
                builder.advanceLexer();
                errorMarker.error("Unmatched closing parenthesis ')'");
            } else if (tokenType == UiTypes.LBRACKET) {
                parseArrayLiteral(builder);
            } else if (tokenType == UiTypes.RBRACKET) {
                // Unmatched closing bracket
                PsiBuilder.Marker errorMarker = builder.mark();
                builder.advanceLexer();
                errorMarker.error("Unmatched closing bracket ']'");
            } else {
                builder.advanceLexer();
            }
        }
    }

    private void parseComponentBody(PsiBuilder builder) {
        PsiBuilder.Marker bodyMarker = builder.mark();
        builder.advanceLexer(); // consume {
        boolean matched = parseBlockContent(builder, UiTypes.RBRACE);

        if (!matched) {
            bodyMarker.error("Unclosed brace '{'");
        } else {
            bodyMarker.done(UiTypes.COMPONENT_BODY);
        }
    }

    private void parsePropertyValue(PsiBuilder builder) {
        PsiBuilder.Marker valueMarker = builder.mark();
        builder.advanceLexer(); // consume (
        boolean matched = parseBlockContent(builder, UiTypes.RPAREN);

        if (!matched) {
            valueMarker.error("Unclosed parenthesis '('");
        } else {
            valueMarker.done(UiTypes.PROPERTY_VALUE);
        }
    }

    private void parseArrayLiteral(PsiBuilder builder) {
        PsiBuilder.Marker arrayMarker = builder.mark();
        builder.advanceLexer(); // consume [
        boolean matched = parseBlockContent(builder, UiTypes.RBRACKET);

        if (!matched) {
            arrayMarker.error("Unclosed bracket '['");
        } else {
            arrayMarker.done(UiTypes.ARRAY_LITERAL);
        }
    }

    private boolean parseBlockContent(PsiBuilder builder, IElementType expectedClosing) {
        while (!builder.eof()) {
            IElementType tokenType = builder.getTokenType();

            if (tokenType == expectedClosing) {
                builder.advanceLexer(); // consume closing bracket
                return true; // Found matching closing bracket
            } else if (tokenType == UiTypes.LBRACE) {
                parseComponentBody(builder);
            } else if (tokenType == UiTypes.RBRACE) {
                // Wrong type of closing bracket
                return false;
            } else if (tokenType == UiTypes.LPAREN) {
                parsePropertyValue(builder);
            } else if (tokenType == UiTypes.RPAREN) {
                // Wrong type of closing bracket
                return false;
            } else if (tokenType == UiTypes.LBRACKET) {
                parseArrayLiteral(builder);
            } else if (tokenType == UiTypes.RBRACKET) {
                // Wrong type of closing bracket
                return false;
            } else {
                builder.advanceLexer();
            }
        }

        // Reached EOF without finding closing bracket
        return false;
    }
}
