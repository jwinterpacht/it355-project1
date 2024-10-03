import java.util.Arrays;

/**
 * The EXP02J class contains an example of how to compare two integer arrays in Java.
 * Rule: Do not use the Object.equals() method to compare two arrays
 */
public class EXP02J {

    /**
     * The ArrayComparison class provides a method to demonstrate the comparison of two arrays.
     */
    public static class ArrayComparison {

        /**
         * The main method where the program execution begins.
         * It initializes two integer arrays and compares them for equality,
         * demonstrating the correct way to compare arrays in Java.
         *
         * @param args Command-line arguments (not used in this example)
         */
        public static void main(String[] args) {
            int[] array1 = {1, 2, 3};  // First array
            int[] array2 = {1, 2, 3};  // Second array

            // Correct way to compare arrays
            if (Arrays.equals(array1, array2)) {
                System.out.println("Arrays are equal.");
            } else {
                System.out.println("Arrays are not equal.");
            }
        }
    }
}


