package org.jetbrains.plugins.template.ui.lexer;

import com.intellij.lexer.FlexAdapter;

public class UiLexerAdapter extends FlexAdapter {

    public UiLexerAdapter() {
        super(new UiLexer(null));
    }
}
