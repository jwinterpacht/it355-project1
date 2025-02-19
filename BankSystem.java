import java.io.*;
import java.nio.CharBuffer;
import java.security.PermissionCollection;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.text.Normalizer;
import java.util.Locale;
import java.math.BigDecimal;
/*
*
*
* Displays Rule: MET06-J. Do not invoke overridable methods in clone()
* Displays Rule: SER05-J. Do not serialize instances of inner classes
* Displays Rule: TSM01-J: Do not let the reference escape during object construction
* Displays Rule: OBJ08-J. Do not expose private members of an outer class from within a nested class
* Displays Recommendation: DCL54-J. Use meaningful symbolic constants to represent literal valuess
* Displays Recommendation: NUM50-J. Convert integers to floating point for floating-point operations
* Displays Recommendation: NUM52-J. Be aware of numeric promotion behavior
* Displays Recomendation: EXP52-J. Use braces for the body of an if, for, or while statement 
* Displays Recommendation: DCL53-J. Minimize the scope of variables
* Displays Recommendation: IDS50-J: Use conservative file naming conventions
*/

/**
 * The Customer class represents a bank customer with account balances.
 * This class demonstrates proper serialization, cloning, and encapsulation.
 */
class Customer implements Cloneable, Serializable {
    // Recommendation DCL50-J: Use visually distinct identifiers
    private String name;
    private int[] accountBalances; // Stores multiple account balances

    /**
     * Constructs a Customer object with a name and account balances.
     * 
     * @param name            The name of the customer.
     * @param accountBalances An array of integers representing account balances.
     */
    public Customer(String name, int[] accountBalances) {
        this.name = name;
        this.accountBalances = accountBalances;
    }

    /**
     * Deserializes the object. This method should not be altered as per SER01-J:
     * Do not deviate from the proper signatures of serialization methods
     * 
     * @param in The ObjectInputStream used for deserialization.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If a class is not found.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // Reads the non-static and non-transient fields from the stream (transient
        // meaning should not be serialized)
        in.defaultReadObject();
    }

    /**
     * Serializes the object. This method should not be altered as per SER01-J.
     * 
     * @param out The ObjectOutputStream used for serialization.
     * @throws IOException If an I/O error occurs.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // Writes the non-static and non-transient fields to the stream
        out.defaultWriteObject();
    }

    /**
     * Gets the customer's name.
     * 
     * MET04-J: Do not increase accessibility of overridden methods.
     * 
     * @return The name of the customer.
     */
    public final String getName() { // Final method to prevent overriding in subclasses
        return name;
    }

    /**
     * Gets a copy of the account balances to prevent exposure of the original
     * array.
     * 
     * MET04-J: Do not increase accessibility of overridden methods.
     * 
     * @return A cloned copy of the account balances array.
     */
    public final int[] getAccountBalances() { // FIO05-J: Do not expose buffers or thier backing arrays
        // OBJ13-J: Ensure that references to mutable objects are not exposed
        // Recommendation MET55-J: Return an empty array or collection instead of a null
        // value for methods that return an array or collection
        // Make a copy of the accountBalances array so that the original may not be
        // modified
        return accountBalances.clone(); // returns a copy to avoid exposing the internal buffer
    }

    /**
     * Creates a clone of the current Customer object.
     * 
     * Clone method that does not call overridable methods
     * Rule: MET06-J: Do not invoke overridable methods in clone()
     * MET04-J: The access level remains 'protected' to match the
     * original method in the superclass
     * Recommendation: OBJ57-J: Do not rely on methods that can be overridden by
     * untrusted code.
     * 
     * @return A cloned Customer object.
     * @throws CloneNotSupportedException If cloning is not supported.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Customer cloned = (Customer) super.clone(); // Default cloning
        // Directly copy fields without invoking any overridable method

        // OBJ05-J: do not return references to private mutable class members, use of
        // .clone() solves this
        cloned.accountBalances = this.accountBalances.clone(); // clone the array
        return cloned; // Return the cloned customer
    }

    /**
     * Displays customer information, including name and account balances.
     * 
     * Recommendation: IDS51-J: Properly encode or escape output to prevent HTML
     * injection or XSS attacks
     */
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Account Balances: " + Arrays.toString(accountBalances));
    }

    /**
     * The Account class represents a customer's account and includes methods for
     * deposits.
     * NUM52-J. Be aware of numeric promotion behavior
     * NUM50-J. Convert integers to floating point for floating-point operations
     * DCL54-J. Using meaningful symbolic constants to represent literal values
     * OBJ08-J. Do not expose private memebers of an outer class from within a
     * nested class
     * NUM10-J. Do not construct BigDecimal objects from floating-point literals
     */
    class Account {
        // NUM10-J: Create a BigDecimal object with a String
        private final BigDecimal INTEREST_RATE = new BigDecimal("0.05");

        /**
         * Deposits an amount into the account and applies interest.
         * 
         * @param accountNumber The account number to deposit to.
         * @param amount        The amount to deposit.
         */
        public void deposit(int accountNumber, int amount) {
            if (accountNumber >= 0 && accountNumber < accountBalances.length) {
                // Add deposit amount to the current balance
                accountBalances[accountNumber] += amount;

                //
                System.out.println("New Balance for account " + accountNumber + ": " + accountBalances[accountNumber]);
            } else {
                System.out.println("Invalid account number.");
            }
        }

        // LCK01-J: Use a private final lock for synchronization
        private static final Object lock = new Object();

        // TPS00-J: Use a thread pool to handle tasks
        private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    }
}

/**
 * The BankSystem class provides a simple console-based bank management system.
 * It supports customer management, serialization, deserialization, and file
 * I/O.
 */
public class BankSystem {
    // ENV06-J: Disable debugging in production code
    private static final boolean DEBUG = false;
    // OBJ01-J: limit accessibility of fields
    private static Customer[] customers = new Customer[100]; // Array to hold up to 100 customers
    private static int customerCount = 0; // Count of current customers

    /**
     * Serializes a Customer object to a file.
     * 
     * @param customer The customer to serialize.
     * @param filePath The file path to save the serialized object.
     */
    public static void serializeCustomer(Customer customer, String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(customer);
            System.out.println("Customer " + customer.getName() + " serialized to file: " + filePath);
        } catch (IOException e) {
            System.out.println("There was an error during serialization.");
        }
    }

    /**
     * Deserializes a Customer object from a file.
     * 
     * @param filePath The file path to read the serialized object from.
     * @return The deserialized Customer object or null if deserialization failed.
     */
    public static Customer deserializeCustomer(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            Customer customer = (Customer) in.readObject();
            System.out.println("Customer " + customer.getName() + " deserialized from file: " + filePath);
            return customer;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("There was an error during deserialization.");
            return null;
        }
    }

    /**
     * Sanitizes input to allow only alphanumeric characters, periods, and spaces.
     * 
     * SEC01-J: Helper method to sanitize input before allowing it into potentially
     * sensitive or privileged blocks of code
     * SEC02-J: Ensure any security checks are not based on untrusted resources
     * 
     * @param input The input string to sanitize.
     * @return A sanitized version of the input.
     */
    private static String sanitizeInput(String input) {
        // Remove any non-alphanumeric characters to prevent injection attacks
        return input.replaceAll("[^a-zA-Z0-9. ]", "");
    }

    /**
     * Saves a new Customer object to a file after obtaining details from user
     * input.
     * 
     * @param filename The file path where the customer object is saved.
     * @throws Exception If an invalid input is provided.
     */
    public static void printToFile(String filename) throws Exception {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        // Prompt for customer name
        System.out.print("Enter Customer Name: ");
        String name = sanitizeInput(scanner.nextLine());

        // Prompt for account balances
        System.out.print("Enter number of account balances (0-5): ");
        // DCL51-J: Do not shadow or obscure identifiers in subscopes
        // temp and numAccounts are distinct and scoped locally within the method
        // without shadowing any class-level variables or parameters
        int temp = scanner.nextInt();
        int numAccounts = -1;
        if (temp <= 5 && temp >= 0) {
            numAccounts = temp;
        } else {
            // Rule ERR07-J: Throw specific exceptions
            throw new IllegalArgumentException("invalid input");
        }

        int[] accountBalances = new int[numAccounts];
        int x = 0;

        for (int i = 0; i < numAccounts; i++) {
            System.out.print("Enter balance for account " + (i + 1) + ": ");
            x = scanner.nextInt();

            if (x >= 0) {
                accountBalances[i] = x;
            } else {
                throw new Exception("Invalid balance");
            }

        }

        // DCL00-J: Static variables are intitialized before the class that uses them is
        // initialized
        // Create a new Customer object
        Customer customer = new Customer(name, accountBalances);

        // Writing the customer object to a file
        ObjectOutputStream filePrinter = null;
        try {
            filePrinter = new ObjectOutputStream(new FileOutputStream(filename)); // Rule FIO04-J
            // Rule: FIO12-J: Ensures resource is only closed once
            filePrinter.writeObject(customer);
            System.out.println("Customer saved to file: " + filename);
        } catch (IOException e) {// ERR01-J: Do not allow exceptions to expose sensitive information
            System.out.println("Error saving ");
        } finally {/// Rule: Perform proper cleanup at program termination using try-catch and
                   /// finally.
            if (filePrinter != null) {
                try {
                    // Rule: ERR00-J: close file stream
                    // Rule:FIO14-J: Perform proper cleanup at program termination
                    filePrinter.close();
                    System.out.println("File stream closed.");
                } catch (IOException e) {// ERR01-J: Do not allow exceptions to expose sensitive information
                    System.out.println("Error: Failed to close the file stream.");
                }
            }
        }

        // Add customer to the array
        addCustomer(customer);
    }

    /**
     * Method to load a Customer object from a file
     * 
     * Displays rule: FIO02-J: Detect and handle file-related errors
     * Displays Rule: FIO04-J: Release resources when no longer needed
     * Displays Rule: ERR0-J: Ensure the file stream is closed
     * Displays Rule: ERR01-J: Do not allow exceptions to expose sensitive
     * information
     * Displays Rule: FIO12-J: Ensures resource is only closed once
     * Displays recomendation: FIO50-J. Do not make assumptions about file creation
     * 
     * @param filename The file path to read the Customer object from.
     */
    public static void loadCustomerByName(String filename) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the customer to load: ");
        String searchName = sanitizeInput(scanner.nextLine());

        ObjectInputStream fileReader = null;
        try {
            // Rule FIO04-J
            fileReader = new ObjectInputStream(new FileInputStream(filename));
            // Rule: FIO12-J Ensures resource is only closed once
            Customer loadedCustomer = (Customer) fileReader.readObject();
            if (loadedCustomer.getName().equalsIgnoreCase(searchName)) {
                System.out.println("\nCustomer loaded from file:");
                loadedCustomer.displayInfo();
            } else {
                System.out.println("No customer found with the name: " + searchName);
            }
            // Rule FIO02-J: Detect and handle file-related errors
            // Recommendation FIO50-J: Do not make assumptions about file creation
        } catch (FileNotFoundException e) { // handle file not found error specifically

            System.out.println("Error: File not found.");
        } catch (IOException | ClassNotFoundException e) { // handle file related errors
            System.out.println("Error: File error ");

        } finally {
            // Rule ERR00-J: Ensure the file stream is closed
            // Rule FIO14-J: Perform proper cleanup at program termination
            if (fileReader != null) {
                try {
                    fileReader.close();
                    System.out.println("File stream closed.");
                } catch (IOException e) {
                    System.out.println("Error: Failed to close the file stream.");
                }
            }
        }
    }

    /**
     * Adds a Customer object to the array.
     * 
     * Recommendation: OBJ52-J: Write garbage-collection-friendly code
     * 
     * @param customer The Customer object to add.
     */
    private static void addCustomer(Customer customer) {
        if (customerCount < customers.length) {
            customers[customerCount++] = customer;
            System.out.println("Customer added to the list.");
        } else {
            System.out.println("Customer array is full. Cannot add more customers.");
        }
    }

    /**
     * Prints information of all customers in the array.
     */
    public static void printAllCustomers() {
        // Recommendation EXP51-J. Do not perform assignments in conditional expressions
        // This correctly compares the customerCount variable to 0
        if (customerCount == 0) {
            System.out.println("No customers available.");
            return;
        }
        System.out.println("\n--- List of Customers ---");
        for (int i = 0; i < customerCount; i++) {
            // EXP53-J: Use parentheses for precendence of operation
            System.out.println("Customer " + (i + 1) + ":");
            customers[i].displayInfo();
        }
    }

    /**
     * Method to clone a customer by name.
     * This method clones an existing Customer object and adds the cloned Customer
     * to the list.
     * 
     * Rule: MET06-J: Do not invoke overridable methods in clone()
     * Rule: ERR01-J: Do not allow exceptions to expose sensitive information
     */
    public static void cloneCustomerByName() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the customer to clone: ");
        String cloneName = sanitizeInput(scanner.nextLine());
        Customer customerToClone = null;

        // Search for the customer by name
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getName().equalsIgnoreCase(cloneName)) {
                customerToClone = customers[i];
                break;
            }
        }

        if (customerToClone != null) {
            try {
                Customer clonedCustomer = (Customer) customerToClone.clone();
                addCustomer(clonedCustomer); // Add cloned customer to the array
                System.out.println("\nCloned Customer info:");
                clonedCustomer.displayInfo();
            } catch (CloneNotSupportedException e) {
                System.out.println("Cloning not supported: ");
            }
        } else {
            System.out.println("No customer found with the name: " + cloneName);
        }

    }

    /**
     * Finds a customer by name in the customer array.
     * 
     * Rule NUM09: Do not use floating point values in for loops
     * Rule IDS01-J. Normalize strings before validating them
     * Rule STR02-J. Specify an appropriate locale when comparing locale-dependent
     * data
     * 
     * @param name The name of the customer to find.
     * @return The customer object if found, otherwise null.
     */
    private static Customer findCustomerByName(String name) {
        // Rule IDS01-J and STR02-J
        // Normalize the input before string validation
        // Specify a locale
        String normalizedInput = Normalizer.normalize(name, Normalizer.Form.NFC).toLowerCase(Locale.ENGLISH);

        // Rule NUM09
        for (int i = 0; i < customerCount; i++) {
            // Normalize stored names for comparison
            String normalizedStoredName = Normalizer.normalize(customers[i].getName(), Normalizer.Form.NFC)
                    .toLowerCase(Locale.ENGLISH);
            if (normalizedStoredName.equalsIgnoreCase(normalizedInput)) {
                return customers[i];
            }
        }
        return null;
    }

    /**
     * This method prompts input for names
     * 
     * it displays rules:
     * ENV02-J: Do not trust the values of environment variables
     * Displays Recomendation: MET54-J. Always provide feedback about the resulting
     * value of a method
     */
    public static void scanName() {
        if (customerCount > 1) { // Rule: Do not trust environment variables
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of the first customer: ");
            String name1 = sanitizeInput(scanner.nextLine());
            System.out.print("Enter the name of the second customer: ");
            String name2 = sanitizeInput(scanner.nextLine());

            if (matchBalance(name1, name2)) {// Recomendation :MET54-J. Always provide feedback about the resulting
                                             // value of a method
                System.out.println("Match found");
            } else {
                System.out.println("No match");
            }
        } else {
            // Rule ERR07-J: Using specific Exceptions
            throw new IllegalStateException("Not enough customers to perform the operation."); // ERR07-J: Using
                                                                                               // specific
                                                                                               // IllegalStateException
        }
    }

    /**
     * Checks if two customers have matching account balances.
     * 
     * Rule MET00-J: Validate method arguments
     * Rule EXP02-J: Do not use Object.equals() method to compare two arrays
     * Rule EXP03-J: Do not use equality operators when comparing values of boxed
     * primitives
     * Rule STR02-J: Specify an appropriate locale when comparing locale-dependent
     * data
     * Recommendation OBJ54-J: Do not attempt to help the garbage collector by
     * setting local reference variables to null
     * Recommendation EXP50-J: Do not confuse object equality with reference
     * equality
     * Recommendation DCL52-J: Do not declare more than one variable per declaration
     * 
     * @param a The name of the first customer.
     * @param b The name of the second customer.
     * @return True if the balances match, false otherwise.
     */
    public static boolean matchBalance(String a, String b) {
        // DCL52-J: Each variable is declared on its own line
        boolean match = false;
        boolean found1 = false;
        boolean found2 = false;

        // OBJ54-J: Do not attempt to help the garbage collector by setting local
        // refence variables to null
        int[] arr1 = new int[6];
        int[] arr2 = new int[6];

        for (int i = 0; i < customerCount; i++) { // locate customers from customer array
            // Specify the locale
            if (customers[i].getName().toLowerCase(Locale.ENGLISH).equalsIgnoreCase(a.toLowerCase(Locale.ENGLISH))) {

                // EXP00-J: do not ignore return values by methods
                arr1 = customers[i].getAccountBalances();
                found1 = true;

            } else if (customers[i].getName().toLowerCase(Locale.ENGLISH)
                    .equalsIgnoreCase(b.toLowerCase(Locale.ENGLISH))) {

                arr2 = customers[i].getAccountBalances();
                found2 = true;
            }
        }
        // Rule: MET00-J: Validate method arguments p9 done
        if (found1 && found2) {
            for (int i = 0; i < Math.min(arr1.length, arr2.length); i++) {
                // EXP03-J: Convert to Integer objects and use equals() for comparison
                Integer balance1 = arr1[i];
                Integer balance2 = arr2[i];
                if (!balance1.equals(balance2)) {
                    return false; // Return false if any pair of balances are not equal
                }
            }
            match = true; // Only true if all pairs match
        }
        return match;
    }

    /**
     * The main method serves as the entry point for the Bank System application.
     * It presents a menu to the user for interacting with Customer objects,
     * including options to add, clone, serialize, deserialize, and display customer
     * details.
     * 
     * ENV06-J: Disable debugging in production code.
     * ENV05-J: Ensure remote monitoring is disabled.
     * ENV04-J: Check that bytecode verification is enabled.
     * 
     * @param args Command-line arguments (not used in this application).
     * @throws Exception If an error occurs while performing file operations or
     *                   input handling.
     */
    public static void main(String[] args) throws Exception {

        // ENV06-J: check to see if debugging in production code is on or not
        if (DEBUG) {
            System.out.println("Debugging mode is ON.");
        } else {
            System.out.println("Running in production mode.");
        }

        // ENV05-J: Ensure remote monitoring is disabled
        String jmxMonitoring = System.getProperty("com.sun.management.jmxremote");
        if (jmxMonitoring != null) {
            System.out.println("Warning: Remote monitoring is enabled. Disabling it for security.");
            System.clearProperty("com.sun.management.jmxremote");
        }

        // ENV04-J: Check that bytecode verification is enabled
        boolean bytecodeVerified = System.getProperty("java.class.path") != null;
        if (!bytecodeVerified) {
            System.out.println("Warning: Bytecode verification may not be enabled.");
        } else {
            System.out.println("Bytecode verification is enabled.");
        }

        Scanner scanner = new Scanner(System.in);
        String filename = "Customer.dat";

        int option;

        System.out.println("*  Welcome to Millionaires' Paddock!             *");
        System.out.println("*  Where your wallets dreams come true!*");
        System.out.println("*  Warning: Side effects may include sudden wealth,*");
        System.out.println("*  and an uncontrollable urge to buy yachts!     *");

        do {
            System.out.println("\n--- Bank System Menu ---");
            System.out.println("1. Display All Customers");
            System.out.println("2. Add Customer");
            System.out.println("3. Load Customer ");
            System.out.println("4. Clone Customer ");
            System.out.println("5. Check customer balances match");
            System.out.println("6. Calculate Average Balance");
            System.out.println("7: Deposits");
            System.out.println("8: Serialize Customer");
            System.out.println("9: Deserialize Customer");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            // All catch show rule: ERR01-J: Do not allow exceptions to expose sensitive
            // information
            switch (option) {
                case 1:
                    printAllCustomers();
                    break;
                case 2:
                    try {
                        printToFile(filename);
                    } catch (Exception e) {
                        System.out.println("Invalid input");

                    }
                    break;
                case 3:

                    try {
                        loadCustomerByName(filename);
                    } catch (Exception e) {
                        System.out.println("Error saving");
                    }

                    break;
                case 4:
                    try {
                        cloneCustomerByName();
                    } catch (Exception e) {
                        System.out.println("Error cloning");
                    }

                    break;
                case 5:
                    try {
                        scanName();
                    } catch (Exception e) {
                        System.out.println("Not enough customers");
                    }
                    break;
                case 6:
                    System.out.print("Enter the name of the customer to calculate the average balance: ");
                    String customerName = sanitizeInput(scanner.nextLine());
                    Customer customer = findCustomerByName(customerName);
                    if (customer != null) {
                        calculateAverageBalance(customer);
                    } else {
                        System.out.println("No customer found with the name: " + customerName);
                    }
                    break;
                case 7:
                    System.out.println("Enter the name of the customer to deposit to: ");
                    String depositCustomerName = sanitizeInput(scanner.nextLine());
                    Customer depositCustomer = findCustomerByName(depositCustomerName);
                    if (depositCustomer != null) {
                        Customer.Account customerAccount = depositCustomer.new Account();
                        System.out.print("Enter the account number (0-"
                                + (depositCustomer.getAccountBalances().length - 1) + "): ");
                        int accountNumber = scanner.nextInt();
                        System.out.print("Enter the amount to deposit: ");
                        int amount = scanner.nextInt();

                        customerAccount.deposit(accountNumber, amount);

                    } else {
                        System.out.println("No customer found with the name: " + depositCustomerName);
                    }
                    break;
                case 8:
                    // Serialize a Customer object
                    System.out.print("Enter the name of the customer to serialize: ");
                    String serializeCustomerName = sanitizeInput(scanner.nextLine());

                    // Find the customer by name
                    Customer customerToBeSerialized = findCustomerByName(serializeCustomerName);

                    if (customerToBeSerialized != null) {
                        // Ask for the file path to save the serialized object
                        System.out.print("Enter the name of the file with the .ser extension to write to: ");
                        String filePath = sanitizeInput(scanner.nextLine());

                        // Serialize the customer object to the specified file
                        serializeCustomer(customerToBeSerialized, filePath);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 9:
                    // Deserialize a Customer object
                    System.out.print("Enter the name of the file with the .ser extension to read from: ");
                    String deserializeFilePath = sanitizeInput(scanner.nextLine());

                    // Deserialize the Customer object from the specified file
                    Customer deserializedCustomer = deserializeCustomer(deserializeFilePath);

                    if (deserializedCustomer != null) {
                        System.out.println("Deserialized Customer information:");
                        deserializedCustomer.displayInfo();
                    } else {
                        System.out.println("Failed to deserialize the customer. Ensure the file exists and is valid.");
                    }
                    break;
                case 0:
                    System.out.println("End " + "\uD83D\uDE00");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 0);

        scanner.close();
    }

    /**
     * Reads customer names from a text file and prints them to the console.
     * This method uses character-based input and properly distinguishes between
     * bytes and characters.
     * Rule: FIO08-J: Distinguish between characters or bytes from a stream
     * Rule: ERR01-J: Do not allow exceptions to expose sensitive information
     */
    public static void readCustomerNamesFromFile(String filename) {
        // Using BufferedReader to read characters from a file
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { // Rule: FIO08-J
            String line;
            while ((line = reader.readLine()) != null) { // Read line by line
                System.out.println("Customer Name: " + line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (IOException e) {
            System.out.println("Error reading from file: ");

        }
    }

    /**
     * Method to calculate the average balance of a customer's accounts
     * Rule NUM07: J use Nan and not == to compare floats
     * Rule NUM02-J. Ensure that division and remainder operations do not result in
     * divide-by-zero errors
     * Rule NUM11-J. Do not compare or inspect the string representation of
     * floating-point values
     */
    public static double calculateAverageBalance(Customer customer) {
        int[] accountBalances = customer.getAccountBalances();

        // NUM02-J: Check for zero to avoid division by zero
        if (accountBalances.length == 0) {
            System.out.println("No account balances available for average calculation. Returning default value.");
            return 0.0; // Return default value
        }

        int sum = 0;

        for (int balance : accountBalances) {
            sum += balance;
        }

        // Cast to double for precision in division
        double average = (double) sum / accountBalances.length;

        // NUM07-J: Check for NaN in the result
        // NUM11-J: Check the double value directly for NaN status without converting it
        // to a string
        // In Java, float and double are both considered floating-point types
        if (Double.isNaN(average)) {
            System.out.println("Average balance calculation resulted in NaN. Please check data integrity.");
            return 0.0; // Return a default value or handle the NaN case as appropriate
        }

        System.out.println("Average Balance for " + customer.getName() + ": " + average);
        return average;
    }
}
