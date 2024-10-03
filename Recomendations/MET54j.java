/**
 * The MET54j class demonstrates a method to check if a number is even or odd and provides feedback to the user.
 * Rule:Always provide feedback about the resulting value of a method
 */
public class MET54j {

    /**
     * The main method where the program execution begins.
     * It calls the {checkIfEven(int)} method to determine whether a given number is even or odd.
     * The result is displayed to the user, and an exception is handled in case of invalid input.
     *
     * @param args Command-line arguments (not used in this example)
     * @throws Exception Throws a custom exception if a non-integer value is encountered
     */
    public static void main(String[] args) throws Exception {
        int number = 10;
        boolean isEven = checkIfEven(number);

        // Always provide feedback about the result
        try {
            if (isEven) {
                System.out.println("The number " + number + " is even.");
            } else {
                System.out.println("The number " + number + " is odd.");
            }
        } catch (Exception e) {
            // Inform the user if a non-digit is found
            throw new Exception("Found NAN (Not a Number)");
        }
    }

    /**
     * Checks if a given integer is even.
     *
     * @param num The integer to be checked
     * @return true if the number is even, false otherwise
     */
    public static boolean checkIfEven(int num) {
        return num % 2 == 0;
    }
}

    



