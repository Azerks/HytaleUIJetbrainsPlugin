package org.jetbrains.plugins.template.ui.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.UiLanguage;
import org.jetbrains.plugins.template.ui.lexer.UiLexerAdapter;
import org.jetbrains.plugins.template.ui.psi.UiFile;
import org.jetbrains.plugins.template.ui.psi.UiTypes;

public class UiParserDefinition implements ParserDefinition {

    public static final IFileElementType FILE = new IFileElementType(UiLanguage.INSTANCE);
    public static final TokenSet COMMENTS = TokenSet.create(UiTypes.COMMENT);
    public static final TokenSet STRINGS = TokenSet.create(UiTypes.STRING);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new UiLexerAdapter();
    }

    @NotNull
    @Override
    public PsiParser createParser(Project project) {
        return new UiParser();
    }

    @NotNull
    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return STRINGS;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return new UiPsiElement(node);
    }

    @NotNull
    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new UiFile(viewProvider);
    }
}
