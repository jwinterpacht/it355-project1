import java.io.File;
public class ENV02J {
    public static void main(String[] args) {
        // Fetch the environment variable
        String configFilePath = System.getenv("CONFIG_FILE_PATH");
        // Validate the environment variable
        if (configFilePath == null || configFilePath.isEmpty() || !new File(configFilePath).isFile()) {
            System.err.println("Invalid CONFIG_FILE_PATH. Using default configuration.");
            configFilePath = "/default/path/to/config.properties";  // Fallback path
        }
        System.out.println("Loading configuration from: " + configFilePath);
        // Proceed with loading configuration
    }
}
