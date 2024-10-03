import java.util.Arrays;

/**
 * The MET55J class demonstrates a method that returns an empty array of Strings 
 * instead of null and prints the result.
 * Rule: Return an empty array or collection instead of a null value for methods that return an array or collection
 */
public class MET55J {

    /**
     * The main method where the program execution begins.
     * It calls the {getNames()} method and prints the resulting array using { ArraystoString(Object[])}.
     *
     * @param args Command-line arguments (not used in this example)
     */
    public static void main(String[] args) {
        // Call the method and print the returned array
        String[] result = getNames();
        System.out.println("Resulting array: " + Arrays.toString(result));
    }

    /**
     * This method returns an empty array of Strings instead of null.
     *
     * @return an empty array of Strings (never null)
     */
    public static String[] getNames() {
        // Instead of returning null, return an empty array
        return new String[0];
    }
}
