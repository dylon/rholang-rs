package org.rholang.lang.parser;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * A wrapper for the Rholang parser using JNI.
 */
public class RholangParserJNI {
    private static final Logger LOG = Logger.getInstance(RholangParserJNI.class);
    private static boolean initialized = false;

    /**
     * Initialize the JNI bridge.
     */
    private static synchronized void initialize() {
        if (initialized) {
            return;
        }

        try {
            // Extract the native library from the plugin resources
            extractNativeLibrary();

            try {
                // Load the native library
                System.loadLibrary("rholang_parser");
                initialized = true;
            } catch (UnsatisfiedLinkError e) {
                LOG.warn("Failed to load native library: " + e.getMessage() + ". Some functionality may be limited.");
                // Don't set initialized to true, so we can try again later if needed
            }
        } catch (Exception e) {
            LOG.error("Failed to initialize JNI bridge", e);
        }
    }

    /**
     * Extract the native library from the plugin resources.
     */
    private static void extractNativeLibrary() throws IOException {
        // Create a temporary directory for the native library
        Path tempDir = Files.createTempDirectory("rholang-parser-jni");
        tempDir.toFile().deleteOnExit();

        // Extract the native library to the temporary directory
        String osName = System.getProperty("os.name").toLowerCase();
        String libraryName;
        if (osName.contains("win")) {
            libraryName = "librholang_parser.dll";
        } else if (osName.contains("mac")) {
            libraryName = "librholang_parser.dylib";
        } else {
            libraryName = "librholang_parser.so";
        }

        // Try to find the library in different locations
        Path sourcePath = null;

        // First try in the plugin's lib directory (when running in the IDE)
        Path pluginLibPath = Paths.get("lib", libraryName);
        if (Files.exists(pluginLibPath)) {
            sourcePath = pluginLibPath;
            LOG.info("Found library at: " + pluginLibPath);
        }

        // Then try in the build directory (when running tests)
        if (sourcePath == null) {
            Path buildLibPath = Paths.get("build", "resources", "main", "lib", libraryName);
            if (Files.exists(buildLibPath)) {
                sourcePath = buildLibPath;
                LOG.info("Found library at: " + buildLibPath);
            }
        }

        // If still not found, try in the plugin jar (when installed in IntelliJ)
        if (sourcePath == null) {
            try {
                // Try to find the library in the classpath first
                java.net.URL resource = RholangParserJNI.class.getClassLoader().getResource("lib/" + libraryName);
                if (resource != null) {
                    sourcePath = Paths.get(resource.toURI());
                    LOG.info("Found library in classpath: " + sourcePath);
                } else {
                    // Get the path to the plugin jar
                    java.net.URL location = RholangParserJNI.class.getProtectionDomain().getCodeSource().getLocation();
                    if (location != null) {
                        String pluginPath = location.getPath();

                        // Extract from the jar if it's a jar file
                        if (pluginPath.endsWith(".jar")) {
                            // TODO: Extract from jar
                            LOG.info("Plugin jar path: " + pluginPath);
                        }
                    } else {
                        LOG.warn("CodeSource location is null, trying alternative methods to find the library");

                        // Try to find the library in the system library path
                        String javaLibraryPath = System.getProperty("java.library.path");
                        if (javaLibraryPath != null) {
                            String[] paths = javaLibraryPath.split(File.pathSeparator);
                            for (String path : paths) {
                                File libFile = new File(path, libraryName);
                                if (libFile.exists()) {
                                    sourcePath = libFile.toPath();
                                    LOG.info("Found library in system path: " + sourcePath);
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOG.warn("Error while trying to locate the library", e);
            }

            // If we still don't have a source path, log a warning but don't throw an exception
            // This allows the plugin to continue loading even if the native library is not found
            if (sourcePath == null) {
                LOG.warn("Could not find library: " + libraryName + ". Some functionality may be limited.");
                return; // Exit the method without extracting the library
            }
        }

        // Copy the library to the temporary directory
        Path targetPath = tempDir.resolve(libraryName);
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

        // Set the java.library.path to include the temporary directory
        System.setProperty("java.library.path", tempDir.toString());
    }

    /**
     * Checks if the given code is valid Rholang.
     *
     * @param code the code to check
     * @return true if the code is valid Rholang, false otherwise
     */
    public static boolean isValid(@NotNull String code) {
        initialize();

        // If initialization failed, return true to avoid blocking the user
        if (!initialized) {
            LOG.warn("JNI bridge not initialized, assuming code is valid");
            return true;
        }

        try {
            // Call the native method
            return isValidNative(code);
        } catch (UnsatisfiedLinkError e) {
            LOG.warn("Native method not found: " + e.getMessage() + ". Assuming code is valid.");
            return true;
        } catch (Exception e) {
            LOG.warn("Error calling Rholang parser via JNI", e);
            return false;
        }
    }

    /**
     * Parses the given code and returns a parse tree.
     *
     * @param code the code to parse
     * @return the parse tree, or null if parsing failed
     */
    @Nullable
    public static String parse(@NotNull String code) {
        initialize();

        // If initialization failed, return null to indicate parsing failed
        if (!initialized) {
            LOG.warn("JNI bridge not initialized, cannot parse code");
            return null;
        }

        try {
            // Call the native method
            String result = parseNative(code);

            // Parse the JSON result
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(result);

            // Check if the parsing was successful
            boolean valid = (boolean) json.get("valid");
            if (!valid) {
                String error = (String) json.get("error");
                LOG.warn("Rholang parser error: " + error);
                return null;
            }

            // Return the parse tree
            return (String) json.get("tree");
        } catch (UnsatisfiedLinkError e) {
            LOG.warn("Native method not found: " + e.getMessage() + ". Cannot parse code.");
            return null;
        } catch (Exception e) {
            LOG.warn("Error calling Rholang parser via JNI", e);
            return null;
        }
    }

    // Native methods
    private static native boolean isValidNative(String code);
    private static native String parseNative(String code);
}
