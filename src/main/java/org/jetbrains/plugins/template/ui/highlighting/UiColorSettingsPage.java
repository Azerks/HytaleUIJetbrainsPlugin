package org.jetbrains.plugins.template.ui.highlighting;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.template.ui.UiIcons;

import javax.swing.*;
import java.util.Map;

public class UiColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Comment", UiSyntaxHighlighter.COMMENT),
            new AttributesDescriptor("Boolean", UiSyntaxHighlighter.BOOLEAN),
            new AttributesDescriptor("Identifier", UiSyntaxHighlighter.IDENTIFIER),
            new AttributesDescriptor("Number", UiSyntaxHighlighter.NUMBER),
            new AttributesDescriptor("String", UiSyntaxHighlighter.STRING),
            new AttributesDescriptor("Color", UiSyntaxHighlighter.COLOR),
            new AttributesDescriptor("Operator", UiSyntaxHighlighter.OPERATOR),
            new AttributesDescriptor("Braces", UiSyntaxHighlighter.BRACES),
            new AttributesDescriptor("Parentheses", UiSyntaxHighlighter.PARENTHESES),
            new AttributesDescriptor("Special (@, $, #)", UiSyntaxHighlighter.SPECIAL),
    };

    @Nullable
    @Override
    public Icon getIcon() {
        return UiIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new UiSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return """
                // UI Configuration Example
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
                    Style: (
                      FontSize: 14,
                      TextColor: #ffffff(0.85)
                    );
                  }
                };
                """;
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "UI";
    }
}
