public class MET00J {   
    public static class AgeValidator {  
        private int age;

        public void setAge(int age) {
            if (age < 0 || age > 150) {
                throw new IllegalArgumentException("Age must be between 0 and 150.");
            }
            this.age = age;
        }
    }
    public static void main(String[] args) {
        AgeValidator validator = new AgeValidator();
        try {
            validator.setAge(25); // Valid
            System.out.println("Age set to: " + validator.age);
            validator.setAge(-5); // Invalid, will throw exception
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
