package org.jetbrains.plugins.template.ui.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UiFileReference extends PsiReferenceBase<PsiElement> {

    public UiFileReference(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        String fileName = getValue();

        // Remove quotes if present
        if (fileName.startsWith("\"") || fileName.startsWith("'")) {
            fileName = fileName.substring(1);
        }
        if (fileName.endsWith("\"") || fileName.endsWith("'")) {
            fileName = fileName.substring(0, fileName.length() - 1);
        }

        // Get the directory of the current file
        PsiFile currentFile = myElement.getContainingFile();
        if (currentFile == null) {
            return null;
        }

        VirtualFile currentDir = currentFile.getVirtualFile();
        if (currentDir == null) {
            return null;
        }

        currentDir = currentDir.getParent();
        if (currentDir == null) {
            return null;
        }

        // Look for the file relative to the current file's directory
        VirtualFile targetFile = currentDir.findFileByRelativePath(fileName);
        if (targetFile != null) {
            return PsiManager.getInstance(myElement.getProject()).findFile(targetFile);
        }

        // Also try to find in the project root
        VirtualFile projectRoot = myElement.getProject().getBaseDir();
        if (projectRoot != null) {
            targetFile = projectRoot.findFileByRelativePath(fileName);
            if (targetFile != null) {
                return PsiManager.getInstance(myElement.getProject()).findFile(targetFile);
            }
        }

        return null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        // Could return all .ui files in the project for completion
        return new Object[0];
    }
}
