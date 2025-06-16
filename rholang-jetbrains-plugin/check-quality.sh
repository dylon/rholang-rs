#!/bin/bash

# Script to check code quality and run tests for the Rholang plugin

# Exit on error
set -e

echo "Running Checkstyle..."
./gradlew checkstyleMain checkstyleTest

echo "Running PMD..."
./gradlew pmdMain pmdTest

echo "Running tests with JaCoCo coverage..."
./gradlew test jacocoTestReport

echo "All checks completed successfully!"
echo "JaCoCo coverage report is available at: build/reports/jacoco/test/html/index.html"