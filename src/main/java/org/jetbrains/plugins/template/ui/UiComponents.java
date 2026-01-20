package org.jetbrains.plugins.template.ui;

import java.util.*;

public class UiComponents {

    // Component types
    public static final Set<String> COMPONENT_TYPES = Set.of(
            "Group", "Label", "Button", "TextButton", "TextField", "NumberField",
            "DropdownBox", "CheckBox", "Sprite", "BackButton", "CompactTextField",
            "Slider", "FloatSlider", "ColorPicker", "ColorPickerDropdownBox",
            "FileDropdownBox", "TabNavigation", "ItemGrid"
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
                "Background", "Padding", "Visible", "Format", "Value"
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
                "Anchor", "Style", "Min", "Max", "Value", "Step", "Visible"
        ));

        // FloatSlider properties
        COMPONENT_PROPERTIES.put("FloatSlider", Set.of(
                "Anchor", "Style", "Min", "Max", "Value", "Step", "Visible"
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

        // ItemGrid properties
        COMPONENT_PROPERTIES.put("ItemGrid", Set.of(
                "Anchor", "Style", "SlotsPerRow", "Visible"
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

    // Padding properties (used inside Padding(...))
    public static final Set<String> PADDING_PROPERTIES = Set.of(
            "Top", "Bottom", "Left", "Right", "Horizontal", "Vertical", "Full"
    );

    // Style function types (functions that return styles)
    public static final Set<String> STYLE_FUNCTIONS = Set.of(
            "PatchStyle", "LabelStyle", "ButtonStyle", "TextButtonStyle",
            "DropdownBoxStyle", "CheckBoxStyle", "SliderStyle", "ColorPickerStyle",
            "ColorPickerDropdownBoxStyle", "FileDropdownBoxStyle", "InputFieldStyle",
            "PopupMenuLayerStyle", "TabStateStyle", "TabNavigationStyle",
            "ScrollbarStyle", "TextTooltipStyle"
    );

    // Common function names
    public static final Set<String> FUNCTION_TYPES = Set.of(
            "PatchStyle", "LabelStyle", "ButtonStyle", "TextButtonStyle",
            "DropdownBoxStyle", "CheckBoxStyle", "SliderStyle", "Anchor",
            "Padding", "ColorPickerStyle", "ColorPickerDropdownBoxStyle",
            "FileDropdownBoxStyle", "InputFieldStyle", "PopupMenuLayerStyle",
            "TabStateStyle", "TabNavigationStyle", "ScrollbarStyle",
            "TextTooltipStyle"
    );

    // PatchStyle parameters
    public static final Set<String> PATCH_STYLE_PARAMS = Set.of(
            "TexturePath", "Border", "HorizontalBorder", "VerticalBorder", "Color"
    );

    // LabelStyle parameters
    public static final Set<String> LABEL_STYLE_PARAMS = Set.of(
            "FontSize", "TextColor", "RenderBold", "RenderUppercase",
            "HorizontalAlignment", "VerticalAlignment", "Alignment",
            "Wrap", "LetterSpacing", "FontName"
    );

    // ButtonStyle/TextButtonStyle state names
    public static final Set<String> BUTTON_STATES = Set.of(
            "Default", "Hovered", "Pressed", "Disabled"
    );

    // Properties for button state definitions
    public static final Set<String> BUTTON_STATE_PROPERTIES = Set.of(
            "Background", "LabelStyle"
    );

    // Decoration properties (for TextField)
    public static final Set<String> DECORATION_PROPERTIES = Set.of(
            "Default", "Icon", "ClearButtonStyle"
    );

    // NumberField Format properties
    public static final Set<String> NUMBER_FORMAT_PROPERTIES = Set.of(
            "MaxDecimalPlaces", "Step", "MinValue", "MaxValue"
    );

    // ItemGrid Style properties
    public static final Set<String> ITEM_GRID_STYLE_PROPERTIES = Set.of(
            "SlotSize", "SlotIconSize", "SlotSpacing", "SlotBackground"
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

    public static boolean isStyleFunction(String name) {
        return STYLE_FUNCTIONS.contains(name);
    }

    public static boolean isFunctionType(String name) {
        return FUNCTION_TYPES.contains(name);
    }
}
