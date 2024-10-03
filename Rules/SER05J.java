import java.io.*;

/**
 * The SER05J class demonstrates the serialization and deserialization
 * of a static nested class in Java.
 * Rule: Do not serialize instances of inner classes
 */
public class SER05J {
    
    /**
     * The OuterClass contains a static nested class that can be serialized.
     */
    public static class OuterClass {
        // Static nested class
        /**
         * The NestedClass represents a simple serializable class with a single field.
         */
        public static class NestedClass implements Serializable {
            private String nestedField = "Nested field";

            /**
             * Returns a string representation of the NestedClass.
             *
             * @return A string describing the NestedClass instance.
             */
            @Override
            public String toString() {
                return "NestedClass{" +
                        "nestedField='" + nestedField + '\'' +
                        '}';
            }
        }
        /**
         * The main method demonstrates serialization and deserialization of the NestedClass.
         *
         * @param args Command-line arguments (not used).
         */
        public static void main(String[] args) {
            NestedClass nested = new NestedClass();
            
            // Serialize the nested class
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("nestedClass.ser"))) {
                oos.writeObject(nested);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Deserialize the nested class
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("nestedClass.ser"))) {
                NestedClass deserializedNested = (NestedClass) ois.readObject();
                System.out.println(deserializedNested);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
