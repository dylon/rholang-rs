# Rholang Plugin Implementation

This document describes the implementation of the Rholang plugin for JetBrains IDEs.

## Project Structure

The plugin is structured as follows:

```
rholang-jetbrains/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── rholang/
│   │   │           └── lang/
│   │   │               ├── highlighting/
│   │   │               │   ├── RholangColorSettingsPage.java
│   │   │               │   ├── RholangSyntaxHighlighter.java
│   │   │               │   └── RholangSyntaxHighlighterFactory.java
│   │   │               ├── lexer/
│   │   │               │   └── RholangLexer.java
│   │   │               ├── parser/
│   │   │               │   ├── RholangParser.java
│   │   │               │   ├── RholangParserDefinition.java
│   │   │               │   └── RholangTokenSets.java
│   │   │               ├── psi/
│   │   │               │   ├── RholangElementType.java
│   │   │               │   ├── RholangFile.java
│   │   │               │   ├── RholangPsiElement.java
│   │   │               │   └── RholangTypes.java
│   │   │               ├── RholangFileType.java
│   │   │               ├── RholangIcons.java
│   │   │               └── RholangLanguage.java
│   │   └── resources/
│   │       └── META-INF/
│   │           └── plugin.xml
├── examples/
│   ├── calculator.rho
│   └── hello.rho
├── .gitignore
├── build.gradle
├── README.md
└── settings.gradle
```

## Implementation Details

### Language Definition

The Rholang language is defined in `RholangLanguage.java`. This class extends the IntelliJ `Language` class and provides a singleton instance that is used throughout the plugin.

### File Type

The Rholang file type is defined in `RholangFileType.java`. This class extends the IntelliJ `LanguageFileType` class and associates the `.rho` extension with the Rholang language.

### Lexer

The Rholang lexer is defined in `RholangLexer.java`. This class extends the IntelliJ `LexerBase` class and is responsible for tokenizing Rholang code. The lexer recognizes comments, strings, identifiers, keywords, numbers, and operators.

### Parser

The Rholang parser is defined in `RholangParser.java`. This class implements the IntelliJ `PsiParser` interface and is responsible for parsing the tokens into a syntax tree. The current implementation is a simple parser that just consumes all tokens without building a proper syntax tree.

### Syntax Highlighting

Syntax highlighting is implemented in `RholangSyntaxHighlighter.java`, `RholangSyntaxHighlighterFactory.java`, and `RholangColorSettingsPage.java`. These classes define how different tokens should be highlighted and allow users to customize the colors used for syntax highlighting.

## Building and Testing

To build the plugin, run:

```
./gradlew buildPlugin
```

The plugin will be built in `build/distributions/`.

To test the plugin, you can install it in IntelliJ IDEA:

1. Go to Settings/Preferences > Plugins
2. Click the gear icon and select "Install Plugin from Disk..."
3. Navigate to the built plugin JAR file and select it
4. Restart IntelliJ IDEA

After restarting, you should be able to open the sample Rholang files in the `examples` directory and see syntax highlighting.

## Future Improvements

The current implementation is a basic plugin that provides syntax highlighting for Rholang files. Future improvements could include:

- Improved parsing to build a proper syntax tree
- Code completion
- Error highlighting
- Code formatting
- Navigation (go to definition, find usages, etc.)
- Refactoring support
- Integration with the Rholang compiler