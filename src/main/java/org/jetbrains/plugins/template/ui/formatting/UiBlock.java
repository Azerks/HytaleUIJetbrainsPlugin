package org.jetbrains.plugins.template.ui.formatting;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.template.ui.psi.UiTypes;

import java.util.ArrayList;
import java.util.List;

public class UiBlock extends AbstractBlock {

    private final SpacingBuilder spacingBuilder;
    private final int indentLevel;

    public UiBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment,
                   SpacingBuilder spacingBuilder) {
        this(node, wrap, alignment, spacingBuilder, 0);
    }

    public UiBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment,
                   SpacingBuilder spacingBuilder, int indentLevel) {
        super(node, wrap, alignment);
        this.spacingBuilder = spacingBuilder;
        this.indentLevel = indentLevel;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<>();
        ASTNode child = myNode.getFirstChildNode();

        while (child != null) {
            IElementType childType = child.getElementType();

            if (childType != TokenType.WHITE_SPACE && childType != null) {
                Block block = new UiBlock(
                        child,
                        Wrap.createWrap(WrapType.NONE, false),
                        null,
                        spacingBuilder,
                        indentLevel
                );
                blocks.add(block);
            }
            child = child.getTreeNext();
        }
        return blocks;
    }

    @Override
    public Indent getIndent() {
        IElementType elementType = myNode.getElementType();
        ASTNode parent = myNode.getTreeParent();

        // No indent for braces and parentheses themselves
        if (elementType == UiTypes.LBRACE || elementType == UiTypes.RBRACE ||
            elementType == UiTypes.LPAREN || elementType == UiTypes.RPAREN) {
            return Indent.getNoneIndent();
        }

        // If parent is null, no indent (root level)
        if (parent == null) {
            return Indent.getNoneIndent();
        }

        // If parent is a BLOCK element (content between braces or parentheses), we should be indented
        IElementType parentType = parent.getElementType();
        if (parentType == UiTypes.BLOCK) {
            return Indent.getNormalIndent();
        }

        return Indent.getNoneIndent();
    }

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }
}
