import java.util.Arrays;  
public class EXP02J {
    public static class ArrayComparison {  
        public static void main(String[] args) {
            int[] array1 = {1, 2, 3};
            int[] array2 = {1, 2, 3};
            // Correct way to compare arrays
            if (Arrays.equals(array1, array2)) {
                System.out.println("Arrays are equal.");
            } else {
                System.out.println("Arrays are not equal.");
            }
        }
    }
}
