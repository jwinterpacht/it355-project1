/**
 * The MET00J class contains a demonstration of an AgeValidator that checks the validity of an age value.
 * Rule: Validate method arguments
 */
public class MET00J {   

    /**
     * The AgeValidator class is responsible for validating and storing an age.
     */
    public static class AgeValidator {  
        private int age;

        /**
         * Sets the age after validating that it falls within the specified range (0 to 150).
         *
         * @param age The age to set.
         * @throws IllegalArgumentException if the age is less than 0 or greater than 150.
         */
        public void setAge(int age) {
            if (age < 0 || age > 150) {
                throw new IllegalArgumentException("Age must be between 0 and 150.");
            }
            this.age = age;
        }
    }

    /**
     * The main method demonstrates the use of the AgeValidator class.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        AgeValidator validator = new AgeValidator(); // Create an instance of AgeValidator
        try {
            validator.setAge(25); // Valid age setting
            System.out.println("Age set to: " + validator.age); // Print the valid age
            validator.setAge(-5); // Invalid age, will throw exception
        } catch (IllegalArgumentException e) {
            // Handle the exception for invalid age input
            System.err.println(e.getMessage());
        }
    }
}
