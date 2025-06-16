# Rholang Plugin for JetBrains IDEs

This plugin provides support for the [Rholang](https://rholang.github.io/) programming language in JetBrains IDEs (IntelliJ IDEA, PyCharm, WebStorm, etc.).

## Features

- Syntax highlighting for Rholang files (.rho)
- File type recognition for .rho files

## Requirements

- Java 17 (the plugin is not compatible with Java 24)
- Gradle 8.0 (provided via the Gradle wrapper)

## Setup

1. Clone this repository
2. Download the Gradle wrapper JAR:
   ```
   ./download-gradle-wrapper.sh
   ```
3. Set JAVA_HOME to point to a Java 17 installation:
   ```
   export JAVA_HOME=/path/to/java17
   ```

## Building from Source

1. Run `./gradlew buildPlugin`
2. The plugin will be built in `build/distributions/`

## Testing

Run the tests with:
```
./gradlew test
```

## Code Quality

Check code quality with:
`./check-quality.sh`

This will run:
- Checkstyle
- PMD
- Tests with JaCoCo coverage

## Installation

### From JetBrains Marketplace

*Coming soon*

### Manual Installation

1. Build the plugin as described above
2. In your JetBrains IDE, go to Settings/Preferences > Plugins
3. Click the gear icon and select "Install Plugin from Disk..."
4. Navigate to the built plugin in `build/distributions/` and select it
5. Restart the IDE

For detailed instructions on installing and using the plugin, see [USING_THE_PLUGIN.md](USING_THE_PLUGIN.md).

## Development

This plugin is built using the [IntelliJ Platform Plugin SDK](https://plugins.jetbrains.com/docs/intellij/welcome.html).

The Rholang grammar is based on the tree-sitter grammar in the main project's `rholang-tree-sitter` module.

## Troubleshooting

### "Incompatible Java and Gradle version" Error

If you see an error like:
```
Your build is currently configured to use incompatible Java 24.0.1 and Gradle 8.0. Cannot sync the project.
The maximum compatible Gradle JVM version is 19.
```

This occurs because Gradle 8.0 is not compatible with Java versions higher than 19, but your system is using Java 24.

To fix this:
1. Install Java 19 or lower (Java 17 is recommended for this project)
2. Use one of these methods to configure Gradle to use the correct Java version:
   - Set the JAVA_HOME environment variable before running Gradle:
     ```
     export JAVA_HOME=/path/to/java19
     ./gradlew buildPlugin
     ```
   - Or, edit the `gradle.properties` file and uncomment the `org.gradle.java.home` line:
     ```
     org.gradle.java.home=/path/to/java19
     ```

### "Unsupported class file major version 68" Error

This error occurs when trying to build with Java 24. The plugin is configured to use Java 17, which is compatible with the IntelliJ Platform Plugin SDK.

To fix this:
1. Install Java 17
2. Set JAVA_HOME to point to your Java 17 installation
3. Run the build again

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- The Rholang team for creating the language
- The tree-sitter team for the parsing technology
