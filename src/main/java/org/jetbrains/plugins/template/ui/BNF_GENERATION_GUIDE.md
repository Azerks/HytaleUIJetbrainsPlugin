# UI Language BNF Grammar and PSI Generation Guide

This guide explains the proper BNF-based PSI structure for the UI language plugin.

## Overview

The UI language uses Grammar-Kit to generate PSI (Program Structure Interface) classes from a BNF grammar definition. This provides:

- **Type-safe PSI elements** with proper interfaces
- **Automatic reference resolution** using `PsiPolyVariantReferenceBase`
- **Rename refactoring** support via `PsiNameIdentifierOwner`
- **Code completion** with variants
- **Navigation** (Go to Definition, Find Usages)

## Architecture

```
Ui.bnf (Grammar Definition)
    ↓ (Grammar-Kit generates)
PSI Interfaces (org.jetbrains.plugins.template.ui.psi)
    ↓ (Grammar-Kit generates)
PSI Implementation Classes (org.jetbrains.plugins.template.ui.psi.impl)
    ↓ (calls methods from)
UiPsiImplUtil (Manual utility class)
```

## Key Components

### 1. **Ui.bnf** - Grammar Definition
Located at: `src/main/java/org/jetbrains/plugins/template/ui/Ui.bnf`

Defines the complete language syntax including:
- Variable declarations: `@MaterialSlotSize = 46;`
- Component definitions: `@InputLabel = Label { ... };`
- Import statements: `$C = "Common.ui";`
- Component usage: `Label { ... }` or `$C.@TextField { ... }`
- Properties, arrays, spread operators, etc.

### 2. **UiPsiImplUtil.java** - PSI Method Implementations
Located at: `src/main/java/org/jetbrains/plugins/template/ui/psi/UiPsiImplUtil.java`

Provides implementation for PSI methods declared in the BNF via `methods=[...]` blocks:
- `getName()` - Get element name
- `setName()` - Rename element
- `getNameIdentifier()` - Get the identifier PSI element
- `getReference()` - Get reference for resolution
- Custom navigation methods

### 3. **UiElementFactory.java** - PSI Element Factory
Located at: `src/main/java/org/jetbrains/plugins/template/ui/psi/UiElementFactory.java`

Factory for creating PSI elements programmatically (used in rename refactoring):
- `createIdentifier()` - Create identifier element
- `createVariableDeclaration()` - Create variable declaration
- `createComponentDefinition()` - Create component definition

### 4. **Reference Classes** - Reference Resolution

#### UiVariableReference.java
- Extends `PsiPolyVariantReferenceBase<PsiElement>`
- Resolves variable references like `$C` to their declarations
- Provides completion variants for all available variables

#### UiComponentReference.java
- Extends `PsiPolyVariantReferenceBase<PsiElement>`
- Resolves component references like `@TextField` or `Label`
- Provides completion for built-in and custom components

### 5. **UiReferenceContributor.java** - Reference Provider Registration
Located at: `src/main/java/org/jetbrains/plugins/template/ui/references/UiReferenceContributor.java`

Registers reference providers for elements that need reference resolution.

## Generating PSI Classes from BNF

### Prerequisites

1. **Install Grammar-Kit Plugin** in IntelliJ IDEA:
   - Go to: Settings → Plugins → Marketplace
   - Search for "Grammar-Kit"
   - Install and restart IntelliJ

2. **Install PsiViewer Plugin** (optional, but recommended):
   - Helps visualize PSI tree structure
   - Settings → Plugins → Marketplace → "PsiViewer"

### Generation Steps

1. **Open Ui.bnf** in IntelliJ IDEA

2. **Right-click anywhere in the BNF file**

3. **Select "Generate Parser Code"**
   - This generates the parser: `UiParser.java`

4. **Right-click again and select "Generate PSI Classes"**
   - This generates:
     - PSI interfaces in `org.jetbrains.plugins.template.ui.psi`
     - PSI implementation classes in `org.jetbrains.plugins.template.ui.psi.impl`

5. **Build the project** to ensure everything compiles

### What Gets Generated

From the BNF grammar, Grammar-Kit will generate:

**Interfaces** (in `psi/` package):
- `UiFile.java` - Root file element
- `UiStatement.java` - Top-level statement
- `UiVariableDeclaration.java` - Variable declaration
- `UiComponentDefinition.java` - Component definition
- `UiImportStatement.java` - Import statement
- `UiComponentUsage.java` - Component usage
- `UiComponentReference.java` - Component reference
- `UiVariable.java` - Variable reference
- `UiProperty.java` - Property
- `UiValue.java` - Value
- And more...

**Implementation Classes** (in `psi/impl/` package):
- `UiFileImpl.java`
- `UiVariableDeclarationImpl.java`
- `UiComponentDefinitionImpl.java`
- And corresponding impl classes for all interfaces

## Key BNF Patterns

### Making an Element Nameable

```bnf
componentDefinition ::= AT IDENTIFIER EQUALS componentUsage SEMICOLON {
  implements="com.intellij.psi.PsiNameIdentifierOwner"
  methods=[
    getName
    setName
    getNameIdentifier
  ]
}
```

This makes `UiComponentDefinition` renameable and searchable.

### Adding Custom Methods

```bnf
property ::= IDENTIFIER COLON value SEMICOLON {
  methods=[
    getPropertyName
    getValue
  ]
}
```

Implement these methods in `UiPsiImplUtil.java`:

```java
public static String getPropertyName(@NotNull UiProperty element) {
    PsiElement identifier = element.getNode().findChildByType(UiTypes.IDENTIFIER);
    return identifier != null ? identifier.getText() : null;
}
```

### Adding References

```bnf
variable ::= DOLLAR IDENTIFIER {
  implements="com.intellij.psi.PsiNamedElement"
  methods=[
    getName
    setName
    getReference
  ]
}
```

Implement in `UiPsiImplUtil.java`:

```java
public static PsiReference getReference(@NotNull UiVariable element) {
    return new UiVariableReference(element, new TextRange(1, element.getTextLength()));
}
```

## Plugin Configuration

Ensure `plugin.xml` has the proper registrations:

```xml
<!-- Parser Definition -->
<lang.parserDefinition
    language="UI"
    implementationClass="org.jetbrains.plugins.template.ui.parser.UiParserDefinition"/>

<!-- Reference Contributor -->
<psi.referenceContributor
    language="UI"
    implementation="org.jetbrains.plugins.template.ui.references.UiReferenceContributor"/>
```

## Testing the PSI Structure

1. **Create a test UI file** with the example syntax
2. **Use PsiViewer** (Tools → View PSI Structure) to see the PSI tree
3. **Test features**:
   - Ctrl+Click on a variable reference → should navigate to declaration
   - Rename a component → all usages should update
   - Code completion → should show available components/variables

## Debugging Tips

1. **Enable PSI structure view**: Tools → View PSI Structure of Current File
2. **Check generated classes**: Verify they exist in the correct packages
3. **Rebuild project**: File → Invalidate Caches / Restart if things seem broken
4. **Check logs**: Help → Show Log in Explorer (for IntelliJ errors)

## References

- [Grammar-Kit Documentation](https://github.com/JetBrains/Grammar-Kit)
- [IntelliJ Platform Plugin SDK](https://plugins.jetbrains.com/docs/intellij/welcome.html)
- [Custom Language Support Tutorial](https://plugins.jetbrains.com/docs/intellij/custom-language-support-tutorial.html)
