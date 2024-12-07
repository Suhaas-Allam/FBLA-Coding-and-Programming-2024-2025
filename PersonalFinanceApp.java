import java.text.SimpleDateFormat; // For parsing and formatting dates
import java.util.Date; // To work with dates
import java.util.List; // To handle lists of transactions
import java.util.Objects; // For comparing strings
import java.util.Scanner; // For reading user input


/**
* A console-based application for managing personal finances.
* Users can add transactions, view balances, generate summaries,
* and perform other financial management tasks.
*/
public class PersonalFinanceApp {
   private static FinanceManager manager = new FinanceManager(); // FinanceManager instance to handle transactions
   private static Scanner scanner = new Scanner(System.in); // Scanner for user input


   /**
    * Entry point for the application. Displays a menu and handles user actions.
    */
   public static void main(String[] args) {
       System.out.println("Welcome to the Personal Finance Manager!");
       boolean running = true;


       // Main loop for the application menu
       while (running) {
           System.out.println("\nMenu:");
           System.out.println("1. Add Transaction");
           System.out.println("2. View Balance");
           System.out.println("3. View All Transactions");
           System.out.println("4. Generate Summary");
           System.out.println("5. View Spending by Category");
           System.out.println("6. Delete Transaction");
           System.out.println("7. Exit");


           // Get user choice and handle it
           int choice = scanner.nextInt();
           scanner.nextLine(); // Consume the newline character
           switch (choice) {
               case 1:
                   addTransaction(); // Add a new transaction
                   break;
               case 2:
                   System.out.printf("Current Balance: $%.2f%n", manager.getBalance()); // View current balance
                   break;
               case 3:
                   viewTransactions(); // View all transactions
                   break;
               case 4:
                   generateSummary(); // Generate income and expense summary
                   break;
               case 5:
                   viewSpendingByCategory(); // View spending grouped by category
                   break;
               case 6:
                   deleteTransaction(); // Delete a transaction
                   break;
               case 7:
                   running = false; // Exit the application
                   System.out.println("Goodbye!");
                   break;
               default:
                   System.out.println("Invalid choice. Please try again."); // Handle invalid input
           }
       }
   }


   /**
    * Handles adding a new transaction. Prompts the user for details.
    */
   private static void addTransaction() {
       System.out.print("Enter type (Income/Expense): ");
       String type = scanner.nextLine();


       // Validate input type
       while (!Objects.equals(type, "Income") && !Objects.equals(type, "Expense")) {
           System.out.println("Invalid Input");
           System.out.print("Enter type (Income/Expense): ");
           type = scanner.nextLine();
       }


       System.out.print("Enter category: ");
       String category = scanner.nextLine();


       System.out.print("Enter amount: ");
       double amount = scanner.nextDouble();
       scanner.nextLine(); // Consume the newline character


       System.out.print("Enter date (yyyy-MM-dd): ");
       String dateString = scanner.nextLine();


       System.out.print("Enter description: ");
       String description = scanner.nextLine();


       // Parse the date and add the transaction
       try {
           Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
           manager.addTransaction(type, category, amount, date, description);
           System.out.println("Transaction added successfully.");
       } catch (Exception e) {
           System.out.println("Invalid date format."); // Handle invalid date input
       }
   }


   /**
    * Displays all transactions recorded in the system.
    */
   private static void viewTransactions() {
       List<Transaction> transactions = manager.getTransactions();
       if (transactions.isEmpty()) {
           System.out.println("No transactions recorded.");
       } else {
           // Print each transaction with its index
           for (int i = 0; i < transactions.size(); i++) {
               System.out.printf("%d. %s%n", i + 1, transactions.get(i).toString());
           }
       }
   }


   /**
    * Generates and displays a summary of income, expenses, and net balance for a date range.
    */
   private static void generateSummary() {
       Date[] dates = getDateRange(); // Get date range from user
       manager.printSummary(dates[0], dates[1]);
   }


   /**
    * Displays spending grouped by category for a specified date range.
    */
   private static void viewSpendingByCategory() {
       Date[] dates = getDateRange(); // Get date range from user
       manager.printSpendingByCategory(dates[0], dates[1]);
   }


   /**
    * Deletes a transaction based on user-provided index.
    */
   private static void deleteTransaction() {
       viewTransactions(); // Show all transactions to the user
       System.out.print("Enter the transaction index to delete: ");
       int index = scanner.nextInt();
       manager.deleteTransaction(index); // Attempt to delete the selected transaction
       System.out.println("Transaction deleted.");
   }

    
   /**
    * Prompts the user to enter a start and end date for filtering transactions.
    *
    * @return An array of two dates: {startDate, endDate}.
    * If an invalid date is entered, null values are returned.
    */
   private static Date[] getDateRange() {
       try {
           System.out.print("Enter start date (yyyy-MM-dd): ");
           Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
           System.out.print("Enter end date (yyyy-MM-dd): ");
           Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
           return new Date[]{startDate, endDate};
       } catch (Exception e) {
           System.out.println("Invalid date format."); // Handle invalid date input
           return new Date[]{null, null};
       }
   }
}
