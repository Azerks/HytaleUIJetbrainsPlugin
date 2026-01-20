package org.jetbrains.plugins.template.ui.formatting;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.template.ui.UiLanguage;

public class UiFormattingModelBuilder implements FormattingModelBuilder {

    @NotNull
    @Override
    public FormattingModel createModel(@NotNull FormattingContext formattingContext) {
        System.out.println("====== UiFormattingModelBuilder.createModel() called ======");
        System.out.println("Node type: " + formattingContext.getNode().getElementType());

        CodeStyleSettings codeStyleSettings = formattingContext.getCodeStyleSettings();
        SpacingBuilder spacingBuilder = createSpacingBuilder(codeStyleSettings);
        UiBlock block = new UiBlock(
                formattingContext.getNode(),
                Wrap.createWrap(WrapType.NONE, false),
                Alignment.createAlignment(),
                spacingBuilder
        );
        return FormattingModelProvider.createFormattingModelForPsiFile(
                formattingContext.getContainingFile(),
                block,
                codeStyleSettings
        );
    }

    private static SpacingBuilder createSpacingBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, UiLanguage.INSTANCE)
                // Equals spacing: x = y
                .around(org.jetbrains.plugins.template.ui.psi.UiTypes.EQUALS).spaces(1)

                // Colon spacing: Key: Value
                .before(org.jetbrains.plugins.template.ui.psi.UiTypes.COLON).spaces(0)
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.COLON).spaces(1)

                // Comma spacing: (x, y, z)
                .before(org.jetbrains.plugins.template.ui.psi.UiTypes.COMMA).spaces(0)
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.COMMA).spaces(1)

                // Semicolon spacing: statement;
                .before(org.jetbrains.plugins.template.ui.psi.UiTypes.SEMICOLON).spaces(0)
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.SEMICOLON).lineBreakInCode()

                // Dot spacing: $C.@DefaultStyle (no spaces)
                .around(org.jetbrains.plugins.template.ui.psi.UiTypes.DOT).spaces(0)

                // Braces: { and }
                .before(org.jetbrains.plugins.template.ui.psi.UiTypes.LBRACE).spaces(1)
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.LBRACE).lineBreakInCode()
                .before(org.jetbrains.plugins.template.ui.psi.UiTypes.RBRACE).lineBreakInCode()
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.RBRACE).lineBreakInCode()

                // Parentheses: (content)
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.LPAREN).spaces(0)
                .before(org.jetbrains.plugins.template.ui.psi.UiTypes.RPAREN).spaces(0)

                // Special characters: no space after @, $, #
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.AT).spaces(0)
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.DOLLAR).spaces(0)
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.HASH).spaces(0)

                // Spread operator: ...
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.SPREAD).spaces(0);
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
