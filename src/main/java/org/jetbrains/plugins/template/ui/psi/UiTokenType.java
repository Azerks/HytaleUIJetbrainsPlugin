package org.jetbrains.plugins.template.ui.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.UiLanguage;

public class UiTokenType extends IElementType {

    public UiTokenType(@NotNull String debugName) {
        super(debugName, UiLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "UiTokenType." + super.toString();
    }
}
