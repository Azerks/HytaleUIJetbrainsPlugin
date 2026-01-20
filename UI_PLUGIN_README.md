# UI File Support Plugin

This IntelliJ Platform plugin provides comprehensive support for `.ui` configuration files, including:

- **Syntax Highlighting**: Colors for keywords, properties, identifiers, strings, numbers, colors, and comments
- **Code Formatting**: Automatic code formatting and indentation with proper nesting
- **Autocompletion**: Smart completion for component types, properties, and values
- **Error Highlighting**: Real-time validation of component types, properties, and syntax
- **Import Resolution**: Navigation to imported files and variable references
- **File Type Recognition**: Automatic recognition of `.ui` files

## Features

### Syntax Highlighting

The plugin recognizes and highlights:

- **Keywords**: `Anchor`, `Style`, `LayoutMode`, `Padding`, `Background`, `Text`, `FlexWeight`, `MaxLength`, `ScrollbarStyle`, `TexturePath`, `Border`
- **Properties**: `Left`, `Right`, `Top`, `Bottom`, `Width`, `Height`, `Full`, `Center`, `FontSize`, `TextColor`, `RenderBold`, `VerticalAlignment`, `Wrap`
- **Values**: Layout modes like `TopScrolling`
- **Booleans**: `true`, `false`
- **Numbers**: Integer and decimal numbers
- **Colors**: Hex colors like `#93844c` or with opacity `#ffffff(0.85)`
- **Strings**: Quoted text
- **Comments**: Line comments (`//`) and block comments (`/* */`)
- **Special Characters**: `@` (definitions), `$` (variables), `#` (IDs), `...` (spread operator)

### Code Formatting

The formatter provides automatic code formatting when you use **Ctrl+Alt+L** (Windows/Linux) or **Cmd+Option+L** (Mac):

**Spacing Rules:**
- Adds space around equals: `x = y`
- Adds space after colon: `Key: Value`
- Adds space after comma: `(x, y, z)`
- No space before punctuation: `item,` `item;`
- No space around dots: `$C.@DefaultStyle`
- No space after special chars: `@name`, `$var`, `#id`

**Indentation:**
- Automatically indents content inside braces `{ }`
- Maintains proper nesting levels
- Aligns related properties

**Line Breaks:**
- New line after opening brace `{`
- New line before closing brace `}`
- New line after semicolon `;`

**Customization:**
Code style settings can be configured in:
**Settings → Editor → Code Style → UI**

### Autocompletion

The plugin provides intelligent code completion:

**Component Types:**
- Press **Ctrl+Space** after `@` or `=` to see available component types
- Supported: Group, Label, Button, TextButton, TextField, NumberField, DropdownBox, CheckBox, Sprite, Slider, etc.

**Properties:**
- Type property names inside component blocks for context-aware suggestions
- Component-specific properties are suggested based on the component type
- Common properties: Anchor, Style, Background, Padding, LayoutMode, Text, FlexWeight, Visible

**Style Properties:**
- Inside `Style: ( )` blocks: FontSize, TextColor, RenderBold, RenderUppercase, Alignment, Wrap, LetterSpacing

**Anchor Properties:**
- Inside `Anchor: ( )` blocks: Left, Right, Top, Bottom, Width, Height, Horizontal, Vertical, Full

**Values:**
- LayoutMode values: Top, Bottom, Left, Right, TopScrolling, BottomScrolling, etc.
- Alignment values: Start, Center, End, Stretch
- Boolean values: true, false

### Error Highlighting

Real-time error detection and validation:

- **Unknown Component Types**: Warns when using undefined component types
- **Invalid Properties**: Highlights properties that don't belong to a component type
- **Missing Commas**: Detects missing commas between property declarations
- **Unmatched Brackets**: Reports unmatched braces `{ }` and parentheses `( )`
- **Syntax Errors**: Highlights various syntax issues

### Import Resolution

Navigate to definitions and imported files:

- **File Imports**: Click on `"Common.ui"` to navigate to the imported file (Ctrl+Click or Cmd+Click)
- **Variable References**: Click on `$C` to jump to its declaration
- **Go to Definition**: Works for both imports and variable references

### UI Language Syntax

The UI language supports:

```ui
// Variable declarations
$C = "Common.ui";
@MaterialSlotSize = 46;

// Component definitions
@InputLabel = Label {
  Anchor: (Left: 6, Right: 16, Width: 250);
  Style: (
    ...$C.@DefaultLabelStyle,
    VerticalAlignment: Center,
    Wrap: true
  );
};

// Nested components
@QuestName = Group {
  LayoutMode: Top;

  Label {
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
  }
};
```

## Building the Plugin

1. Generate the lexer from the JFlex file:
   ```bash
   ./gradlew generateLexer
   ```

2. Build the plugin:
   ```bash
   ./gradlew build
   ```

3. Run the plugin in a test IDE:
   ```bash
   ./gradlew runIde
   ```

## Project Structure

```
src/main/java/org/jetbrains/plugins/template/ui/
├── UiLanguage.java              # Language definition
├── UiFileType.java              # File type registration
├── UiIcons.java                 # Icon resources
├── lexer/
│   ├── Ui.flex                  # JFlex lexer specification
│   ├── UiLexer.java            # Generated lexer (auto-generated)
│   └── UiLexerAdapter.java     # Lexer adapter
├── psi/
│   ├── UiTypes.java            # Token type definitions
│   ├── UiTokenType.java        # Token type implementation
│   ├── UiElementType.java      # PSI element type
│   └── UiFile.java             # PSI file implementation
├── parser/
│   ├── UiParserDefinition.java # Parser configuration
│   ├── UiParser.java           # Parser implementation
│   └── UiPsiElement.java       # PSI element wrapper
├── highlighting/
│   ├── UiSyntaxHighlighter.java        # Syntax highlighter
│   ├── UiSyntaxHighlighterFactory.java # Highlighter factory
│   └── UiColorSettingsPage.java        # Color settings UI
└── formatting/
    ├── UiFormattingModelBuilder.java   # Formatter
    └── UiBlock.java                     # Formatting block
```

## Customization

### Adding New Keywords

To add new keywords:

1. Edit `src/main/java/org/jetbrains/plugins/template/ui/lexer/Ui.flex`
2. Add the keyword to the appropriate section
3. Regenerate the lexer: `./gradlew generateLexer`
4. Rebuild the plugin

### Customizing Colors

Users can customize syntax colors in:
**Settings → Editor → Color Scheme → UI**

## Development Notes

- The lexer is automatically generated from the JFlex specification during build
- The parser is simple and permissive, focusing on tokenization rather than strict syntax validation
- Formatting rules can be adjusted in `UiFormattingModelBuilder.java`

## License

This plugin is based on the IntelliJ Platform Plugin Template.
