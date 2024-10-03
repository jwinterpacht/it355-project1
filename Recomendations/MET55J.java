import java.util.Arrays;
public class MET55J {
    public static void main(String[] args) {
        // Call the method and print the returned array
        String[] result = getNames();
        System.out.println("Resulting array: " + Arrays.toString(result));
    }
    // Method that returns an empty array instead of null
    public static String[] getNames() {
        // Instead of returning null, return an empty array
        return new String[0];
    }
}
