import java.io.File;

/**
 * The ENV02J class demonstrates how to fetch and validate an environment variable in Java,
 *  and provides a fallback if the variable is invalid.
 * rule: Do not trust the values of environment variables
 */
public class ENV02J {

    /**
     * The main method where the program execution begins.
     * It retrieves the "CONFIG_FILE_PATH" environment variable, checks if it's valid,
     * and falls back to a default path if the variable is missing or incorrect.
     *
     * @param args Command-line arguments (not used in this example)
     */
    public static void main(String[] args) {
        // Fetch the environment variable
        String configFilePath = System.getenv("CONFIG_FILE_PATH");

        // Validate the environment variable
        if (configFilePath == null || configFilePath.isEmpty() || !new File(configFilePath).isFile()) {
            System.err.println("Invalid CONFIG_FILE_PATH. Using default configuration.");
            configFilePath = "/default/path/to/config.properties";  // Fallback path
        }

        // Print the configuration file path being used
        System.out.println("Loading configuration from: " + configFilePath);

        // Proceed with loading configuration
    }
}



