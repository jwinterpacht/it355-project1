import java.util.logging.Level;
import java.util.logging.Logger;
public class ERR01J {
public class SensitiveDataExample {
    private static final Logger logger = Logger.getLogger(SensitiveDataExample.class.getName());
    public void riskyOperation() {
        try {
        } catch (ArithmeticException e) {
            // Log the exception internally without exposing sensitive information
            logger.log(Level.SEVERE, "An error occurred during a risky operation.", e);
            // Provide a generic message to the user
            System.out.println("An error occurred. Please try again later.");
        } catch (Exception e) {
            // Log unexpected exceptions
            logger.log(Level.SEVERE, "Unexpected error occurred.", e);
            System.out.println("An unexpected error occurred. Please contact support.");
        }
    }
}
}
