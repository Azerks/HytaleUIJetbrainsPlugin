package org.jetbrains.plugins.template.ui.typing;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.template.ui.psi.UiFile;

public class UiTypedHandler extends TypedHandlerDelegate {

    @NotNull
    @Override
    public Result charTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        if (!(file instanceof UiFile)) {
            return Result.CONTINUE;
        }

        int offset = editor.getCaretModel().getOffset();
        CharSequence text = editor.getDocument().getCharsSequence();

        // Auto-close pairs
        String closing = null;
        switch (c) {
            case '{':
                closing = "}";
                break;
            case '(':
                closing = ")";
                break;
            case '[':
                closing = "]";
                break;
            case '"':
                // Check if we should close the quote or skip over it
                if (offset < text.length() && text.charAt(offset) == '"') {
                    // Skip over the existing closing quote
                    editor.getCaretModel().moveToOffset(offset + 1);
                    return Result.STOP;
                }
                closing = "\"";
                break;
        }

        if (closing != null) {
            // Check if there's already a closing character right after the cursor
            if (offset < text.length() && text.charAt(offset) == closing.charAt(0) && c != '"') {
                // Don't insert another closing character
                return Result.CONTINUE;
            }

            // Insert the closing character
            EditorModificationUtil.insertStringAtCaret(editor, closing, false, 0);
            return Result.STOP;
        }

        return Result.CONTINUE;
    }

    @NotNull
    @Override
    public Result beforeCharTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file, @NotNull com.intellij.openapi.fileTypes.FileType fileType) {
        if (!(file instanceof UiFile)) {
            return Result.CONTINUE;
        }

        int offset = editor.getCaretModel().getOffset();
        CharSequence text = editor.getDocument().getCharsSequence();

        // Skip over closing characters if they're already there
        if ((c == '}' || c == ')' || c == ']' || c == '"') && offset < text.length() && text.charAt(offset) == c) {
            editor.getCaretModel().moveToOffset(offset + 1);
            return Result.STOP;
        }

        return Result.CONTINUE;
    }
}
