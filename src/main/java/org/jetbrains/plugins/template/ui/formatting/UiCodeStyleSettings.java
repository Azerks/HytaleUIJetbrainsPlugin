package org.jetbrains.plugins.template.ui.formatting;

import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;

public class UiCodeStyleSettings extends CustomCodeStyleSettings {

    public boolean SPACE_AROUND_EQUALS = true;
    public boolean SPACE_AFTER_COLON = true;
    public boolean SPACE_BEFORE_COLON = false;
    public boolean SPACE_AFTER_COMMA = true;
    public boolean SPACE_BEFORE_COMMA = false;
    public boolean SPACE_BEFORE_LBRACE = true;
    public boolean NEW_LINE_AFTER_LBRACE = true;
    public boolean NEW_LINE_BEFORE_RBRACE = true;

    public UiCodeStyleSettings(CodeStyleSettings settings) {
        super("UiCodeStyleSettings", settings);
    }
}
