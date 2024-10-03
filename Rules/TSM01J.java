/**
 * The TSM01J class demonstrates the use of a static nested class 
 * and a factory method for object creation in Java.
 * Rule: Do not let the this reference escape during object construction
 */
public class TSM01J {
    
    /**
     * The Example class represents an example object with a name property.
     * It is static to allow instantiation without an instance of the outer class.
     */
    public static class Example {  
        private String name;

        /**
         * Constructor initializes the Example object with a name.
         *
         * @param name The name to be assigned to the Example object.
         */
        public Example(String name) {
            this.name = name;
        }

        /**
         * Factory method for creating an instance of Example.
         *
         * @param name The name to be assigned to the new Example instance.
         * @return A new Example object initialized with the provided name.
         */
        public static Example createInstance(String name) {
            return new Example(name);  // No reference to 'this' escapes
        }

        /**
         * Getter for the name property of the Example object.
         *
         * @return The name of the Example object.
         */
        public String getName() {
            return name;
        }
    }

    /**
     * The main method serves as the entry point for the program.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Example example = Example.createInstance("Test Name");
        System.out.println(example.getName());  // Output: Test Name
    }
}
