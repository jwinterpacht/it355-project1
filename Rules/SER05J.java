import java.io.*;
public class SER05J {
    public static class OuterClass {
        // Static nested class
        public static class NestedClass implements Serializable {
            private String nestedField = "Nested field";
            @Override
            public String toString() {
                return "NestedClass{" +
                        "nestedField='" + nestedField + '\'' +
                        '}';
            }
        }
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
