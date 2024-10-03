import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * The FIO14J class provides functionality to read a text file line by line.
 * Rule: Perform proper cleanup at program termination using the try catch and finally.
 */
public class FIO14J {
    /**
     * Reads the content of a specified file and prints each line to the console while ensuring proper clean up and hadnling resources
     * @param filePath The path of the file to read.
     */
    public void readFile(String filePath) {
        BufferedReader reader = null;  // Declare reader outside the try block
        try {
            // Initialize the BufferedReader with UTF-8 encoding
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String line;
            // Read and print each line of the file
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Output each line
            }
        } catch (IOException e) {
            // Print an error message if there's an issue
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();  // Print the stack trace for debugging
        } finally {
            // Cleanup: Close the reader if it was initialized
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("An error occurred while closing the file: " + e.getMessage());
                }
            } } }}
