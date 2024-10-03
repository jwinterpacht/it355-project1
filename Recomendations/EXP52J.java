/**
 * EXP52J class demonstrates the Use braces for the body of an if, for, or while statement
 * 
 */
public class EXP52J {

    /**
     * The main method where the program execution begins.
     * It contains examples of an if statement and a while loop with corresponding braces.
     *
     * @param args Command-line arguments (not used in this example)
     */
    public static void main(String[] args) {
        int number = 10;

        // Check if the number is greater than 5
        if (number > 5) { // open - braces
            System.out.println("The number is greater than 5.");
        } // close - braces

        // Continue looping while number is greater than 2
        while (number > 2) { // open - braces
            System.out.println("Take 2");
            number -= 2;
        } // close - braces
    }
}


