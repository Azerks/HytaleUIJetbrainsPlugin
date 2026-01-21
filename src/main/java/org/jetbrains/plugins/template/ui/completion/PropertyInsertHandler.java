package org.jetbrains.plugins.template.ui.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.Document;
import org.jetbrains.annotations.NotNull;

public class PropertyInsertHandler implements InsertHandler<LookupElement> {
    @Override
    public void handleInsert(@NotNull InsertionContext context, @NotNull LookupElement item) {

        Document document = context.getDocument();
        int tailOffset = context.getTailOffset();
        document.insertString(tailOffset, ": ");

        // Move cursor after the space
        context.getEditor().getCaretModel().moveToOffset(tailOffset + 1);
    }
}
