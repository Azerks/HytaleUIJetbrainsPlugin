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
                .around(org.jetbrains.plugins.template.ui.psi.UiTypes.EQUALS).spaces(1)
                .around(org.jetbrains.plugins.template.ui.psi.UiTypes.COLON).spaces(1)
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.COMMA).spaces(1)
                .before(org.jetbrains.plugins.template.ui.psi.UiTypes.SEMICOLON).spaces(0)
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.SEMICOLON).spaces(1)
                .before(org.jetbrains.plugins.template.ui.psi.UiTypes.LBRACE).spaces(1)
                .after(org.jetbrains.plugins.template.ui.psi.UiTypes.LBRACE).lineBreakInCode()
                .before(org.jetbrains.plugins.template.ui.psi.UiTypes.RBRACE).lineBreakInCode();
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
