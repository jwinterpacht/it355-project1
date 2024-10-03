
import java.io.File;
import java.io.IOException;

/**
 * The FIO50J class demonstrates file operations in Java,checking if a file exists and creating a new file if it doesn't.
 * Rule: Do not make assumptions about file creation.
 */
public class FIO50J {

    /**
     * The main method where the program execution begins.
     * It checks if a file exists in the current directory and attempts to create it if it doesn't.
     * The method also handles potential IOExceptions that may occur during file creation.
     *
     * @param args Command-line arguments (not used in this example)
     */
    public static void main(String[] args) {
        // Define the file path
        File file = new File("example.txt");

        // Attempt to create the file and handle errors
        try {
            // Check if the file already exists
            if (file.exists()) {
                System.out.println("File already exists: " + file.getPath());
            } else {
                // Attempt to create the file
                if (file.createNewFile()) {
                    System.out.println("File created successfully: " + file.getPath());
                } else {
                    System.out.println("Failed to create the file.");
                }
            }
        } catch (IOException e) {
            // Handle any IOException that occurs during file creation
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }
}

