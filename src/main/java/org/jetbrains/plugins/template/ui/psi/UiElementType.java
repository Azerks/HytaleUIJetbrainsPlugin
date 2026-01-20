package org.jetbrains.plugins.template.ui.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.UiLanguage;

public class UiElementType extends IElementType {

    public UiElementType(@NotNull String debugName) {
        super(debugName, UiLanguage.INSTANCE);
    }
}
