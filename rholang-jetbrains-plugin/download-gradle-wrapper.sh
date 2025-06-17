#!/bin/bash

# Script to download the Gradle Wrapper JAR file

# Exit on error
set -e

WRAPPER_DIR="gradle/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/gradle-wrapper.jar"
WRAPPER_URL="https://github.com/gradle/gradle/raw/v8.0.0/gradle/wrapper/gradle-wrapper.jar"

echo "Downloading Gradle Wrapper JAR..."
mkdir -p "$WRAPPER_DIR"

if command -v curl > /dev/null; then
    curl -L -o "$WRAPPER_JAR" "$WRAPPER_URL"
elif command -v wget > /dev/null; then
    wget -O "$WRAPPER_JAR" "$WRAPPER_URL"
else
    echo "Error: Neither curl nor wget is available. Please install one of them or download the file manually."
    exit 1
fi

echo "Gradle Wrapper JAR downloaded successfully to $WRAPPER_JAR"