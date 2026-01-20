package org.jetbrains.plugins.template.ui.formatting;

import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.psi.codeStyle.CodeStyleConfigurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.template.ui.UiLanguage;

public class UiCodeStyleSettingsProvider extends CodeStyleSettingsProvider {

    @NotNull
    @Override
    public CodeStyleConfigurable createConfigurable(@NotNull CodeStyleSettings settings,
                                                     @NotNull CodeStyleSettings modelSettings) {
        return new CodeStyleAbstractConfigurable(settings, modelSettings, "UI") {
            @Override
            protected @NotNull CodeStyleAbstractPanel createPanel(@NotNull CodeStyleSettings settings) {
                return new TabbedLanguageCodeStylePanel(UiLanguage.INSTANCE, getCurrentSettings(), settings) {
                };
            }

            @Nullable
            @Override
            public String getHelpTopic() {
                return null;
            }
        };
    }

    @Nullable
    @Override
    public String getConfigurableDisplayName() {
        return "UI";
    }

    @Nullable
    @Override
    public CustomCodeStyleSettings createCustomSettings(@NotNull CodeStyleSettings settings) {
        return new UiCodeStyleSettings(settings);
    }
}
