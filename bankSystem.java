import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

// Customer class implementing Cloneable, with no invocation of overridable methods in clone()
//Also displays Rule: Do not serialize instances of inner classes, customer is a top level class.
class Customer implements Cloneable, Serializable {
    private String name;
    private int[] accountBalances;  // Stores multiple account balances as integers

    public Customer(String name, int[] accountBalances) {
        this.name = name;
        this.accountBalances = accountBalances;
    }

    // Final method to prevent overriding in subclasses
    public final String getName() {
        return name;
    }

    public final int[] getAccountBalances() {
        return accountBalances;
    }

    // Clone method that does not call overridable methods
    //// Rule: Do not invoke overridable methods in clone()
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Customer cloned = (Customer) super.clone();  // Default cloning
        // Directly copy fields without invoking any overridable method
        cloned.accountBalances = this.accountBalances.clone();  //clone the array
        return cloned; // Return the cloned customer
    }

    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Account Balances: " + Arrays.toString(accountBalances));
    }
}

public class bankSystem {
    private static Customer[] customers = new Customer[100];  // Array to hold up to 100 customers
    private static int customerCount = 0;  // Count of current customers

    // Save the Customer object to a file (with proper error handling)
    public static void printToFile(String filename) {
        Scanner scanner = new Scanner(System.in);
        
        // Prompt for customer name
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        
        // Prompt for account balances
        System.out.print("Enter number of account balances: ");
        int numAccounts = scanner.nextInt();
        int[] accountBalances = new int[numAccounts];

        for (int i = 0; i < numAccounts; i++) {
            System.out.print("Enter balance for account " + (i + 1) + ": ");
            accountBalances[i] = scanner.nextInt();
        }

        // Create a new Customer object
        Customer customer = new Customer(name, accountBalances);

        // Writing the customer object to a file
        ObjectOutputStream filePrinter = null;
        try {
            filePrinter = new ObjectOutputStream(new FileOutputStream(filename));
            filePrinter.writeObject(customer);
            System.out.println("Customer saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {///Rule: Perform proper cleanup at program termination using try-catch and finally.
            if (filePrinter != null) {
                try {
                    filePrinter.close();
                    System.out.println("File stream closed.");
                } catch (IOException e) {
                    System.out.println("Error: Failed to close the file stream.");
                }
            }
        }

        // Add customer to the array
        addCustomer(customer);
    }

    /**  Method to load a Customer object from a file 
     * 
     * Displays rule: handle file related errors using try catch
     * */
    public static void loadCustomerByName(String filename) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the customer to load: ");
        String searchName = scanner.nextLine();

        ObjectInputStream fileReader = null;
        try {
            fileReader = new ObjectInputStream(new FileInputStream(filename));
            Customer loadedCustomer = (Customer) fileReader.readObject();
            if (loadedCustomer.getName().equalsIgnoreCase(searchName)) {
                System.out.println("\nCustomer loaded from file:");
                loadedCustomer.displayInfo();
            } else {
                System.out.println("No customer found with the name: " + searchName);
            }
        } catch (IOException | ClassNotFoundException e) {//handle file related errors
            System.out.println("Error: " + e.getMessage());
        } finally {//// Rule: Perform proper cleanup at program termination using try-catch and finally
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

    // Method to add a customer to the array
    private static void addCustomer(Customer customer) {
        if (customerCount < customers.length) {
            customers[customerCount++] = customer;
            System.out.println("Customer added to the list.");
        } else {
            System.out.println("Customer array is full. Cannot add more customers.");
        }
    }

    // Method to print all customers in the array
    public static void printAllCustomers() {
        if (customerCount == 0) {
            System.out.println("No customers available.");
            return;
        }
        System.out.println("\n--- List of Customers ---");
        for (int i = 0; i < customerCount; i++) {
            System.out.println("Customer " + (i + 1) + ":");
            customers[i].displayInfo();
        }
    }

    // Method to clone a customer by name
    public static void cloneCustomerByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the customer to clone: ");
        String cloneName = scanner.nextLine();
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
                addCustomer(clonedCustomer);  // Add cloned customer to the array
                System.out.println("\nCloned Customer info:");
                clonedCustomer.displayInfo();
            } catch (CloneNotSupportedException e) {
                System.out.println("Cloning not supported: " + e.getMessage());
            }
        } else {
            System.out.println("No customer found with the name: " + cloneName);
        }
     

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filename = "Customer.dat";

        int option;
        do {
            System.out.println("\n--- Bank System Menu ---");
            System.out.println("1. Display All Customers");
            System.out.println("2. Save Customer");
            System.out.println("3. Load Customer ");
            System.out.println("4. Clone Customer ");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    printAllCustomers();
                    break;
                case 2:
                    printToFile(filename);
                    break;
                case 3:
                    loadCustomerByName(filename);
                    break;
                case 4:
                    cloneCustomerByName();
                    break;
                case 0:
                    System.out.println("End"+ "\uD83D\uDE00" );
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 0);

        scanner.close();
    }
}
