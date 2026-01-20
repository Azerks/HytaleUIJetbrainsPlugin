package org.jetbrains.plugins.template.ui.formatting;

import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.UiLanguage;

public class UiLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {

    @NotNull
    @Override
    public Language getLanguage() {
        return UiLanguage.INSTANCE;
    }

    @Override
    public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer,
                                   @NotNull SettingsType settingsType) {
        if (settingsType == SettingsType.SPACING_SETTINGS) {
            consumer.showStandardOptions("SPACE_AROUND_ASSIGNMENT_OPERATORS");
            consumer.showStandardOptions("SPACE_BEFORE_COMMA");
            consumer.showStandardOptions("SPACE_AFTER_COMMA");
        } else if (settingsType == SettingsType.BLANK_LINES_SETTINGS) {
            consumer.showStandardOptions("KEEP_BLANK_LINES_IN_CODE");
        } else if (settingsType == SettingsType.WRAPPING_AND_BRACES_SETTINGS) {
            consumer.showStandardOptions("KEEP_LINE_BREAKS");
        }
    }

    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        return """
                $C = "Common.ui";
                @MaterialSlotSize = 46;

                @InputLabel = Label {
                  Anchor: (Left: 6, Right: 16, Width: 250);
                  Style: (
                    ...$C.@DefaultLabelStyle,
                    VerticalAlignment: Center,
                    Wrap: true
                  );
                };

                @QuestName = Group {
                  LayoutMode: Top;

                  Label {
                    Anchor: ();
                    Style: (
                      FontSize: 18,
                      TextColor: #93844c,
                      RenderBold: true
                    );
                    Text: "Quest Name :";
                  }

                  $C.@TextField #QuestNameField {
                    FlexWeight: 1;
                    Anchor: (Height: 28);
                  }
                };
                """;
    }
}
