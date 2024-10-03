public class MET54j {
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
            } catch (Exception e) {//inform user if non digit is found.
                
                throw new Exception("Found NAN");
            }
        }
        // check result
        public static boolean checkIfEven(int num) {
            return num % 2 == 0;
        }
    }
    


