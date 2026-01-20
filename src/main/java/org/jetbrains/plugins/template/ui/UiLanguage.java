package org.jetbrains.plugins.template.ui;

import com.intellij.lang.Language;

public class UiLanguage extends Language {

    public static final UiLanguage INSTANCE = new UiLanguage();

    private UiLanguage() {
        super("UI");
    }
}
