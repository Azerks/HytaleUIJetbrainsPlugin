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
                builder.advanceLexer(); // consume {
                PsiBuilder.Marker blockMarker = builder.mark();
                parseContent(builder, level + 1);
                blockMarker.done(UiTypes.BLOCK);
            } else if (tokenType == UiTypes.RBRACE) {
                if (level > 0) {
                    builder.advanceLexer(); // consume }
                    return; // exit this block level
                } else {
                    builder.advanceLexer(); // consume unmatched }
                }
            } else if (tokenType == UiTypes.LPAREN) {
                builder.advanceLexer(); // consume (
                PsiBuilder.Marker blockMarker = builder.mark();
                parseContent(builder, level + 1);
                blockMarker.done(UiTypes.BLOCK);
            } else if (tokenType == UiTypes.RPAREN) {
                if (level > 0) {
                    builder.advanceLexer(); // consume )
                    return; // exit this block level
                } else {
                    builder.advanceLexer(); // consume unmatched )
                }
            } else {
                builder.advanceLexer();
            }
        }
    }
}
