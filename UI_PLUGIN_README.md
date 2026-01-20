# UI File Support Plugin

This IntelliJ Platform plugin provides comprehensive support for `.ui` configuration files, including:

- **Syntax Highlighting**: Colors for keywords, properties, identifiers, strings, numbers, colors, and comments
- **Code Formatting**: Automatic code formatting and indentation
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

The formatter automatically:
- Adds spaces around operators (`:`, `=`)
- Formats braces with proper line breaks
- Indents nested blocks
- Handles comma-separated values

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
