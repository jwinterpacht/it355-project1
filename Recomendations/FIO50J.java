
    
import java.io.File;
import java.io.IOException;
public class FIO50J {
    public static void main(String[] args) {
        // Define the file path
        File file = new File("example.txt");
        // Attempt to create the file and handle errors
        try {
            if (file.exists()) {
                System.out.println("File already exists: ");
            } else {
                // Attempt to create the file
                if (file.createNewFile()) {
                    System.out.println("File created successfully: ");
                } else {
                    System.out.println("Failed to create the file.");
                }
            }
        } catch (IOException e) {
            // Handle any IOException 
            System.out.println("An error occurred while creating the file: ");
        }
    }
}


