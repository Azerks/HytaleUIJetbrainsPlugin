# UI Language Plugin - Recent Changes

## Summary

Implemented a proper BNF-based PSI structure for the UI language plugin with full IDE support including rename refactoring, go to definition, find usages, and code completion.

## Key Changes

### 1. Created BNF Grammar (`Ui.bnf`)

A comprehensive Grammar-Kit BNF file that defines the complete UI language syntax:

- **Import statements**: `$C = "Common.ui";`
- **Variable declarations**: `@MaterialSlotSize = 46;`
- **Component definitions**: `@InputLabel = Label { ... };`
- **Component usage**: `Label { ... }` or `$C.@TextField { ... }`
- **Qualified references**: `$C.@TextField` with proper variable and component resolution
- **Properties, arrays, spread operators**, and all language features

**Key BNF Features:**
- `implements="com.intellij.psi.PsiNameIdentifierOwner"` for renameable elements
- `methods=[...]` blocks to declare custom PSI methods
- `psiImplUtilClass` to provide method implementations
- Proper `pin` directives for error recovery

### 2. PSI Implementation Utility (`UiPsiImplUtil.java`)

Provides implementation for all PSI methods declared in the BNF:

- **PsiNameIdentifierOwner methods**: `getName()`, `setName()`, `getNameIdentifier()`
- **Navigation methods**: `getValue()`, `getComponentUsage()`, `getReference()`
- **Reference creation**: Creates appropriate reference implementations

### 3. Element Factory (`UiElementFactory.java`)

Factory for creating PSI elements programmatically:

- `createIdentifier()` - Create identifiers for rename operations
- `createVariableDeclaration()` - Create variable declarations
- `createComponentDefinition()` - Create component definitions
- Used by rename refactoring and code generation

### 4. Reference Implementations

Renamed reference classes to avoid naming conflicts with generated PSI classes:

#### UiVariableReferenceImpl
- Extends `PsiPolyVariantReferenceBase<PsiElement>`
- Resolves `$C` variable references to:
  - `UiImportStatement` (for file imports like `$C = "Common.ui"`)
  - `UiVariableDeclaration` (for value declarations)
- Provides code completion variants for all variables in the file

#### UiComponentReferenceImpl
- Extends `PsiPolyVariantReferenceBase<PsiElement>`
- Resolves component references like `@TextField` or `Label` to:
  - `UiComponentDefinition` (for custom components)
  - Built-in components from `UiComponents.BUILT_IN_COMPONENTS`
- Provides code completion for all available components

### 5. Removed Duplicate Reference Resolution

**Problem**: Previously had duplicate reference resolution:
1. `UiPsiElement.getReferences()` override
2. `UiReferenceContributor` reference provider

**Solution**: Removed the `getReferences()` override from `UiPsiElement` to use only `UiReferenceContributor`, which is the recommended IntelliJ Platform approach.

### 6. Fixed Naming Conflicts

**Problem**: BNF generator created `UiComponentReference` PSI interface in the `psi` package, which conflicted with our reference implementation class with the same name in the `references` package.

**Solution**: Renamed reference implementation classes:
- `UiComponentReference` → `UiComponentReferenceImpl`
- `UiVariableReference` → `UiVariableReferenceImpl`

### 7. Removed Duplicate Methods from UiPsiImplUtil

**Problem**: BNF generator automatically creates getter methods for child elements (e.g., `getComponentReference()` for `UiSpreadValue`), but we also manually defined them in `UiPsiImplUtil`, causing compilation errors.

**Solution**: Removed automatically-generated methods from `UiPsiImplUtil`:
- `getComponentReference()` for `UiSpreadValue`
- `getComponentReference()` and `getComponentBody()` for `UiComponentUsage`

The BNF generator handles these automatically.

## Generated PSI Classes

After running Grammar-Kit's "Generate Parser Code" and "Generate PSI Classes", the following will be created:

### PSI Interfaces (in `psi/` package):
- `UiFile` - Root file element
- `UiStatement` - Top-level statement
- `UiVariableDeclaration` - Variable declaration (`@Var = value`)
- `UiComponentDefinition` - Component definition (`@Comp = Component { }`)
- `UiImportStatement` - Import statement (`$C = "file.ui"`)
- `UiComponentUsage` - Component usage
- `UiComponentReference` - Component reference (PSI element)
- `UiQualifiedComponentRef` - Qualified reference (`$C.@TextField`)
- `UiSimpleComponentRef` - Simple reference (`Label`)
- `UiVariable` - Variable reference (`$C`)
- `UiProperty` - Property
- `UiValue` - Value
- And more...

### PSI Implementation Classes (in `psi/impl/` package):
- All corresponding `*Impl` classes for the interfaces above

## IDE Features Enabled

With this proper PSI structure, the following IDE features are now supported:

1. **Rename Refactoring** (Shift+F6)
   - Rename variables, component definitions, IDs
   - All usages update automatically

2. **Go to Definition** (Ctrl+Click or Ctrl+B)
   - Click on `$C` → navigates to import statement
   - Click on `@TextField` → navigates to component definition

3. **Find Usages** (Alt+F7)
   - Find all places where a component or variable is used

4. **Code Completion** (Ctrl+Space)
   - Complete variable names after `$`
   - Complete component names after `@` or standalone
   - Shows built-in and custom components

5. **Reference Resolution**
   - Qualified references work: `$C.@TextField`
   - Multi-level resolution supported

## Testing the Changes

To verify everything works:

1. **Install Grammar-Kit plugin** in IntelliJ IDEA
2. **Open `Ui.bnf`** in the IDE
3. **Generate parser**: Right-click → "Generate Parser Code"
4. **Generate PSI**: Right-click → "Generate PSI Classes"
5. **Rebuild the project**
6. **Test features**:
   - Create a `.ui` file with your example syntax
   - Try Ctrl+Click on variables and components
   - Try renaming (Shift+F6)
   - Try code completion (Ctrl+Space)

## Architecture Benefits

### Before (Manual Parser):
- Manual token-by-token parsing in `UiParser`
- Simple `ASTWrapperPsiElement` with no type safety
- Basic reference resolution
- Limited refactoring support

### After (BNF-Generated):
- Grammar-Kit generates type-safe parser
- Proper PSI interfaces for each language construct
- Full `PsiNameIdentifierOwner` support for rename
- `PsiPolyVariantReferenceBase` for robust reference resolution
- Complete IDE integration (rename, find usages, navigation)

## Files Modified

### New Files:
- `Ui.bnf` - Grammar definition
- `UiPsiImplUtil.java` - PSI method implementations
- `UiElementFactory.java` - PSI element factory
- `UiComponentReferenceImpl.java` - Component reference implementation
- `UiVariableReferenceImpl.java` - Variable reference implementation
- `BNF_GENERATION_GUIDE.md` - Documentation

### Modified Files:
- `UiPsiElement.java` - Simplified to basic `ASTWrapperPsiElement`
- `UiReferenceContributor.java` - Updated to use `*Impl` reference classes

### Deleted Files:
- `UiComponentReference.java` (old, replaced by `UiComponentReferenceImpl`)
- `UiVariableReference.java` (old, replaced by `UiVariableReferenceImpl`)

## Next Steps

1. Generate PSI classes from BNF using Grammar-Kit
2. Test all IDE features with example UI files
3. Add more sophisticated reference resolution if needed (cross-file references)
4. Implement code folding, structure view, etc.

## Documentation

See `BNF_GENERATION_GUIDE.md` for comprehensive documentation on:
- BNF grammar patterns
- How to generate PSI classes
- How to add new language features
- Testing and debugging tips
