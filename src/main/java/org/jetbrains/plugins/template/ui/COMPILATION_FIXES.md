# Compilation Fixes Required

## Summary

After switching to BNF-generated PSI classes, several files reference token types that no longer exist: `UiTypes.COMPONENT`, `UiTypes.KEYWORD`, `UiTypes.PROPERTY`, and `UiTypes.VALUE`.

## Steps to Fix

### 1. Regenerate the Lexer (REQUIRED)

The `Ui.flex` file has been updated to return `UiTypes.IDENTIFIER` instead of the old token types. You need to regenerate the lexer:

**In IntelliJ IDEA:**
1. Open `Ui.flex` (at `src/main/java/org/jetbrains/plugins/template/ui/lexer/Ui.flex`)
2. Right-click anywhere in the file
3. Select **"Run JFlex Generator"** or **"Generate JFlex Lexer"**
4. This will regenerate `UiLexer.java` with the correct token types

### 2. Files That Need Updating

The following files reference the old token types and need to be updated or simplified:

#### Fixed Files:
- ✅ `UiSyntaxHighlighter.java` - Removed references to KEYWORD, COMPONENT, PROPERTY, VALUE

#### Files Still Needing Updates:

**`UiCompletionContributor.java`:**
- Remove references to `UiTypes.COMPONENT` (check if element is built-in component instead)
- Keep references to `UiTypes.COMPONENT_BODY`, `UiTypes.PROPERTY_VALUE`, `UiTypes.PROPERTY` (these are element types, not token types)

**`UiBlock.java` (Formatting):**
- Already correct! Uses only `COMPONENT_BODY` and `PROPERTY_VALUE` which are element types (not token types)

**`UiReferenceContributor.java`:**
- Already correct! Uses `COMPONENT_BODY` which is an element type

### 3. Understanding Token Types vs Element Types

**Token Types** (from lexer - single tokens):
- `IDENTIFIER`, `NUMBER`, `STRING`, `COLOR`, `BOOLEAN`
- `EQUALS`, `COLON`, `SEMICOLON`, `COMMA`, `DOT`
- `LBRACE`, `RBRACE`, `LPAREN`, `RPAREN`, `LBRACKET`, `RBRACKET`
- `AT`, `DOLLAR`, `HASH`, `SPREAD`
- `COMMENT`

**Element Types** (from parser - composite elements):
- `COMPONENT_BODY` - A component body `{ ... }`
- `PROPERTY_VALUE` - A property value `( ... )`
- `PROPERTY` - A property statement `Name: value;`
- `VALUE` - A value expression
- `COMPONENT_REFERENCE` - A component reference
- `VARIABLE_DECLARATION` - Variable declaration `@Name = value;`
- And more... (see `UiTypes.java` generated file)

### 4. Fixing UiCompletionContributor

The completion contributor needs to be updated to check for built-in component names instead of checking for `UiTypes.COMPONENT` token type.

**Example Fix:**

**Before:**
```java
if (beforeColon.getNode().getElementType() == UiTypes.COMPONENT) {
    // ...
}
```

**After:**
```java
if (beforeColon.getNode().getElementType() == UiTypes.IDENTIFIER) {
    String text = beforeColon.getText();
    if (UiComponents.isBuiltInComponent(text)) {
        // ...
    }
}
```

## Quick Fix Commands

After regenerating the lexer, the project should compile. The only file that might still have issues is `UiCompletionContributor.java`.

### To Check Compilation:

```bash
cd /path/to/project
./gradlew build
```

## Why This Happened

The old manual parser used custom token types (`COMPONENT`, `KEYWORD`, `PROPERTY`, `VALUE`) to identify specific keywords. The BNF-based approach uses a simpler lexer that only tokenizes basic types (`IDENTIFIER`, `NUMBER`, etc.), and the parser determines meaning from context.

This is actually **better** because:
1. The lexer is simpler and faster
2. The parser handles semantic meaning
3. Keywords can be added/removed without changing the lexer
4. More flexible language evolution

## Files Changed

### Updated:
- ✅ `Ui.flex` - Changed to return `IDENTIFIER` for all keywords
- ✅ `UiSyntaxHighlighter.java` - Removed references to old token types

### Need Regeneration:
- ⏳ `UiLexer.java` - Will be regenerated from `Ui.flex`

### May Need Updates:
- ⚠️ `UiCompletionContributor.java` - Check for COMPONENT references

## Next Steps

1. **Regenerate lexer** from `Ui.flex` in IntelliJ
2. **Rebuild project**
3. **Test** the plugin with example UI files
4. **Update** `UiCompletionContributor.java` if it still has compilation errors
