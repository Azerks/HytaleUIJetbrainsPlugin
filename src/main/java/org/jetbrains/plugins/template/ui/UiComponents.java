package org.jetbrains.plugins.template.ui;

import java.util.*;

public class UiComponents {

    // Component types
    public static final Set<String> COMPONENT_TYPES = Set.of(
            "Group", "Label", "Button", "TextButton", "TextField", "NumberField",
            "DropdownBox", "CheckBox", "Sprite", "BackButton", "CompactTextField",
            "Slider", "ColorPicker", "ColorPickerDropdownBox", "FileDropdownBox",
            "TabNavigation"
    );

    // Common properties for all components
    public static final Set<String> COMMON_PROPERTIES = Set.of(
            "Anchor", "Style", "Background", "Padding", "LayoutMode",
            "FlexWeight", "Visible", "Text"
    );

    // Component-specific properties
    private static final Map<String, Set<String>> COMPONENT_PROPERTIES = new HashMap<>();

    static {
        // Group properties
        COMPONENT_PROPERTIES.put("Group", Set.of(
                "Anchor", "Background", "Padding", "LayoutMode", "FlexWeight",
                "Visible", "ScrollbarStyle"
        ));

        // Label properties
        COMPONENT_PROPERTIES.put("Label", Set.of(
                "Anchor", "Style", "Text", "Padding", "Visible"
        ));

        // Button properties
        COMPONENT_PROPERTIES.put("Button", Set.of(
                "Anchor", "Style", "Padding", "Visible", "Sounds"
        ));

        // TextButton properties
        COMPONENT_PROPERTIES.put("TextButton", Set.of(
                "Anchor", "Style", "Text", "Padding", "Visible", "Sounds"
        ));

        // TextField properties
        COMPONENT_PROPERTIES.put("TextField", Set.of(
                "Anchor", "Style", "PlaceholderStyle", "PlaceholderText",
                "Background", "Padding", "MaxLength", "Visible", "Decoration"
        ));

        // NumberField properties
        COMPONENT_PROPERTIES.put("NumberField", Set.of(
                "Anchor", "Style", "PlaceholderStyle", "PlaceholderText",
                "Background", "Padding", "Visible"
        ));

        // DropdownBox properties
        COMPONENT_PROPERTIES.put("DropdownBox", Set.of(
                "Anchor", "Style", "Visible"
        ));

        // CheckBox properties
        COMPONENT_PROPERTIES.put("CheckBox", Set.of(
                "Anchor", "Background", "Padding", "Style", "Value", "Visible"
        ));

        // Sprite properties
        COMPONENT_PROPERTIES.put("Sprite", Set.of(
                "Anchor", "TexturePath", "Frame", "FramesPerSecond", "Visible"
        ));

        // Slider properties
        COMPONENT_PROPERTIES.put("Slider", Set.of(
                "Anchor", "Style", "MinValue", "MaxValue", "Value", "Visible"
        ));

        // CompactTextField properties
        COMPONENT_PROPERTIES.put("CompactTextField", Set.of(
                "Anchor", "Style", "PlaceholderStyle", "PlaceholderText",
                "CollapsedWidth", "ExpandedWidth", "Padding", "Decoration", "Visible"
        ));

        // BackButton properties
        COMPONENT_PROPERTIES.put("BackButton", Set.of(
                "Anchor", "Style", "Visible"
        ));
    }

    // Style properties
    public static final Set<String> STYLE_PROPERTIES = Set.of(
            "FontSize", "TextColor", "RenderBold", "RenderUppercase",
            "VerticalAlignment", "HorizontalAlignment", "Alignment",
            "Wrap", "LetterSpacing", "FontName"
    );

    // Anchor properties
    public static final Set<String> ANCHOR_PROPERTIES = Set.of(
            "Left", "Right", "Top", "Bottom", "Width", "Height", "Horizontal", "Vertical", "Full"
    );

    // Background properties
    public static final Set<String> BACKGROUND_PROPERTIES = Set.of(
            "TexturePath", "Border", "Color", "HorizontalBorder", "VerticalBorder"
    );

    // LayoutMode values
    public static final Set<String> LAYOUT_MODE_VALUES = Set.of(
            "Top", "Bottom", "Left", "Right", "TopScrolling", "BottomScrolling",
            "LeftScrolling", "RightScrolling"
    );

    // Alignment values
    public static final Set<String> ALIGNMENT_VALUES = Set.of(
            "Start", "Center", "End", "Stretch"
    );

    public static Set<String> getPropertiesForComponent(String componentType) {
        return COMPONENT_PROPERTIES.getOrDefault(componentType, COMMON_PROPERTIES);
    }

    public static boolean isValidComponent(String componentType) {
        return COMPONENT_TYPES.contains(componentType);
    }

    public static boolean isValidProperty(String componentType, String property) {
        Set<String> properties = getPropertiesForComponent(componentType);
        return properties.contains(property) || COMMON_PROPERTIES.contains(property);
    }
}
