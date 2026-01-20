package org.jetbrains.plugins.template.ui.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.UiFileType;
import org.jetbrains.plugins.template.ui.UiLanguage;

public class UiFile extends PsiFileBase {

    public UiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, UiLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return UiFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "UI File";
    }
}
