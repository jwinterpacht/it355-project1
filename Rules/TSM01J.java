public class TSM01J {
    public static class Example {  // Make the Example class static
        private String name;
        // Constructor initializes the object
        public Example(String name) {
            this.name = name;
        }
        // Factory method for object creation
        public static Example createInstance(String name) {
            return new Example(name);  // No reference to 'this' escapes
        }
        // Getter for name
        public String getName() {
            return name;
        }
    }
    public static void main(String[] args) {
        Example example = Example.createInstance("Test Name");
        System.out.println(example.getName());  // Output: Test Name
    }
}
