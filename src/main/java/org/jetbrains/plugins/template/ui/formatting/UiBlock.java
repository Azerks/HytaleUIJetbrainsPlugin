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

    public UiBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment,
                   SpacingBuilder spacingBuilder) {
        super(node, wrap, alignment);
        this.spacingBuilder = spacingBuilder;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<>();
        ASTNode child = myNode.getFirstChildNode();

        while (child != null) {
            IElementType childType = child.getElementType();

            if (childType != TokenType.WHITE_SPACE) {
                Block block = new UiBlock(
                        child,
                        Wrap.createWrap(WrapType.NONE, false),
                        null,
                        spacingBuilder
                );
                blocks.add(block);
            }
            child = child.getTreeNext();
        }
        System.out.println(blocks);
        return blocks;
    }

    @Override
    public Indent getIndent() {
        IElementType elementType = myNode.getElementType();
        ASTNode parent = myNode.getTreeParent();

        String parentTypeStr = (parent != null) ? parent.getElementType().toString() : "null";

        if (elementType == UiTypes.LBRACE || elementType == UiTypes.RBRACE ||
                elementType == UiTypes.LPAREN || elementType == UiTypes.RPAREN ||
                elementType == UiTypes.LBRACKET || elementType == UiTypes.RBRACKET) {
            return Indent.getNoneIndent();
        }

        if (parent == null) {
            return Indent.getNoneIndent();
        }

        IElementType parentType = parent.getElementType();

        // Block containers: if nested inside another block container, inherit its indent
        // If this container is inside another container, it should be indented

        // Children of block containers get indented
        if (parentType == UiTypes.COMPONENT_BODY ||
                parentType == UiTypes.PROPERTY_VALUE ||
                parentType == UiTypes.ARRAY_LITERAL) {
            return Indent.getNormalIndent();
        }

        return Indent.getNoneIndent();
    }

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @NotNull
    @Override
    public ChildAttributes getChildAttributes(int newChildIndex) {
        IElementType elementType = myNode.getElementType();

        System.out.println("getChildAttributes called on: " + elementType + " at index: " + newChildIndex);

        // Inside block containers, always indent new children
        if (elementType == UiTypes.COMPONENT_BODY ||
                elementType == UiTypes.PROPERTY_VALUE ||
                elementType == UiTypes.ARRAY_LITERAL) {
            System.out.println("Inside block container - returning normal indent");
            return new ChildAttributes(Indent.getNormalIndent(), null);
        }

        // Check if the previous child is an opening brace/paren/bracket
        // This handles the case when pressing Enter right after { ( or [
        List<Block> subBlocks = getSubBlocks();
        if (newChildIndex > 0 && newChildIndex <= subBlocks.size()) {
            Block previousBlock = subBlocks.get(newChildIndex - 1);
            if (previousBlock instanceof UiBlock) {
                UiBlock prevUiBlock = (UiBlock) previousBlock;
                IElementType prevType = prevUiBlock.myNode.getElementType();
                System.out.println("Previous block type: " + prevType);

                // After opening braces, we should look for the COMPONENT_BODY/PROPERTY_VALUE/ARRAY_LITERAL
                // that was created by the parser
                if (prevType == UiTypes.COMPONENT_BODY ||
                    prevType == UiTypes.PROPERTY_VALUE ||
                    prevType == UiTypes.ARRAY_LITERAL) {
                    System.out.println("Previous block is a container - returning normal indent");
                    return new ChildAttributes(Indent.getNormalIndent(), null);
                }
            }
        }

        System.out.println("Default case - returning none indent");
        // Default: no indent for other cases
        return new ChildAttributes(Indent.getNoneIndent(), null);
    }

    public int computeDepth() {

        int depth = 0;
        if (myNode.getStartOffsetInParent() == -1 || myNode.getElementType() == UiTypes.COMPONENT_BODY || myNode.getElementType() == UiTypes.PROPERTY_VALUE) {
            depth++;
        }

        ASTNode node = myNode.getFirstChildNode();


        while (node != null) {
            if (node.getElementType() == UiTypes.COMPONENT_BODY || node.getElementType() == UiTypes.PROPERTY_VALUE) {
                depth++;
            }
            if (myNode.getStartOffsetInParent() <= node.getStartOffsetInParent()) {
                break;
            }
            node = node.getTreeNext();
        }
        return depth;
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }
}
