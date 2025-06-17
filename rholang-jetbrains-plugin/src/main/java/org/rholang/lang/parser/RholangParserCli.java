package org.rholang.lang.parser;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.application.PathManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A wrapper for the Rholang parser CLI.
 */
public class RholangParserCli {
    private static final Logger LOG = Logger.getInstance(RholangParserCli.class);
    private static final int TIMEOUT_SECONDS = 5;

    /**
     * Gets the path to the rholang-parser-cli binary.
     * 
     * @return the path to the rholang-parser-cli binary
     */
    private static String getParserCliPath() {
        // First, try to find the binary in the plugin's resources directory
        String pluginPath = PathManager.getPluginsPath();
        Path binaryPath = Paths.get(pluginPath, "rholang-jetbrains-plugin", "rholang-parser-cli");

        if (Files.exists(binaryPath)) {
            return binaryPath.toString();
        }

        // If not found, try to find it in the build directory
        String buildPath = System.getProperty("user.dir");
        binaryPath = Paths.get(buildPath, "build", "resources", "main", "rholang-parser-cli");

        if (Files.exists(binaryPath)) {
            return binaryPath.toString();
        }

        // If still not found, assume it's in the PATH
        LOG.warn("rholang-parser-cli binary not found in plugin resources or build directory, assuming it's in PATH");
        return "rholang-parser-cli";
    }

    /**
     * Checks if the given code is valid Rholang.
     *
     * @param code the code to check
     * @return true if the code is valid Rholang, false otherwise
     */
    public static boolean isValid(@NotNull String code) {
        try {
            // Create a temporary file with the code
            File tempFile = FileUtil.createTempFile("rholang", ".rho", true);
            Files.write(tempFile.toPath(), code.getBytes(StandardCharsets.UTF_8));

            // Build the command
            List<String> command = new ArrayList<>();
            command.add(getParserCliPath());
            command.add("check");
            command.add("--input");
            command.add(tempFile.getAbsolutePath());

            // Execute the command
            Process process = new ProcessBuilder(command)
                    .redirectErrorStream(true)
                    .start();

            // Wait for the process to complete with a timeout
            if (!process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                process.destroy();
                LOG.warn("Rholang parser CLI timed out");
                return false;
            }

            // Read the output
            String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            LOG.debug("Rholang parser CLI output: " + output);

            // Parse the output
            // For now, just check if the process exited with code 0
            return process.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            LOG.warn("Error executing Rholang parser CLI", e);
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
        try {
            // Create a temporary file with the code
            File tempFile = FileUtil.createTempFile("rholang", ".rho", true);
            Files.write(tempFile.toPath(), code.getBytes(StandardCharsets.UTF_8));

            // Build the command
            List<String> command = new ArrayList<>();
            command.add(getParserCliPath());
            command.add("parse");
            command.add("--input");
            command.add(tempFile.getAbsolutePath());

            // Execute the command
            Process process = new ProcessBuilder(command)
                    .redirectErrorStream(true)
                    .start();

            // Wait for the process to complete with a timeout
            if (!process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                process.destroy();
                LOG.warn("Rholang parser CLI timed out");
                return null;
            }

            // Read the output
            String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            LOG.debug("Rholang parser CLI output: " + output);

            // Parse the output
            // For now, just return the raw output
            return output;
        } catch (IOException | InterruptedException e) {
            LOG.warn("Error executing Rholang parser CLI", e);
            return null;
        }
    }
}
