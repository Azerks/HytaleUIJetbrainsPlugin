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

        parseContent(builder, 0);

        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

    private void parseContent(PsiBuilder builder, int level) {
        while (!builder.eof()) {
            IElementType tokenType = builder.getTokenType();

            if (tokenType == UiTypes.LBRACE) {
                PsiBuilder.Marker errorMarker = builder.mark(); // Mark opening position
                builder.advanceLexer(); // consume {
                PsiBuilder.Marker blockMarker = builder.mark();
                boolean matched = parseBlock(builder, UiTypes.RBRACE);
                blockMarker.done(UiTypes.BLOCK);

                if (!matched) {
                    errorMarker.error("Unclosed brace '{'");
                } else {
                    errorMarker.drop();
                }
            } else if (tokenType == UiTypes.RBRACE) {
                // Unmatched closing brace
                PsiBuilder.Marker errorMarker = builder.mark();
                builder.advanceLexer();
                errorMarker.error("Unmatched closing brace '}'");
            } else if (tokenType == UiTypes.LPAREN) {
                PsiBuilder.Marker errorMarker = builder.mark(); // Mark opening position
                builder.advanceLexer(); // consume (
                PsiBuilder.Marker blockMarker = builder.mark();
                boolean matched = parseBlock(builder, UiTypes.RPAREN);
                blockMarker.done(UiTypes.BLOCK);

                if (!matched) {
                    errorMarker.error("Unclosed parenthesis '('");
                } else {
                    errorMarker.drop();
                }
            } else if (tokenType == UiTypes.RPAREN) {
                // Unmatched closing parenthesis
                PsiBuilder.Marker errorMarker = builder.mark();
                builder.advanceLexer();
                errorMarker.error("Unmatched closing parenthesis ')'");
            } else {
                builder.advanceLexer();
            }
        }
    }

    private boolean parseBlock(PsiBuilder builder, IElementType expectedClosing) {
        while (!builder.eof()) {
            IElementType tokenType = builder.getTokenType();

            if (tokenType == expectedClosing) {
                builder.advanceLexer(); // consume closing bracket
                return true; // Found matching closing bracket
            } else if (tokenType == UiTypes.LBRACE) {
                PsiBuilder.Marker errorMarker = builder.mark();
                builder.advanceLexer();
                PsiBuilder.Marker blockMarker = builder.mark();
                boolean matched = parseBlock(builder, UiTypes.RBRACE);
                blockMarker.done(UiTypes.BLOCK);

                if (!matched) {
                    errorMarker.error("Unclosed brace '{'");
                } else {
                    errorMarker.drop();
                }
            } else if (tokenType == UiTypes.RBRACE) {
                if (expectedClosing == UiTypes.RBRACE) {
                    builder.advanceLexer();
                    return true;
                }
                // Wrong type of closing bracket, exit without consuming
                return false;
            } else if (tokenType == UiTypes.LPAREN) {
                PsiBuilder.Marker errorMarker = builder.mark();
                builder.advanceLexer();
                PsiBuilder.Marker blockMarker = builder.mark();
                boolean matched = parseBlock(builder, UiTypes.RPAREN);
                blockMarker.done(UiTypes.BLOCK);

                if (!matched) {
                    errorMarker.error("Unclosed parenthesis '('");
                } else {
                    errorMarker.drop();
                }
            } else if (tokenType == UiTypes.RPAREN) {
                if (expectedClosing == UiTypes.RPAREN) {
                    builder.advanceLexer();
                    return true;
                }
                // Wrong type of closing bracket, exit without consuming
                return false;
            } else {
                builder.advanceLexer();
            }
        }

        // Reached EOF without finding closing bracket
        return false;
    }
}
