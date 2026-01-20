package org.jetbrains.plugins.template.ui;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class UiFileType extends LanguageFileType {

    public static final UiFileType INSTANCE = new UiFileType();

    private UiFileType() {
        super(UiLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "UI File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "UI configuration file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "ui";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return UiIcons.FILE;
    }
}
