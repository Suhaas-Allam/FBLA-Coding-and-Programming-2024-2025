import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


/**
 * PersonalFinanceApp is the main entry point for the Personal Finance Manager application.
 * It provides a command-line interface for managing personal finances, including adding transactions,
 * viewing the current balance, listing all transactions, generating summaries, analyzing spending by category,
 * updating or deleting transactions, and an interactive Q&A feature for finance-related inquiries.


 * The application uses a FinanceManager instance (assumed to handle business logic) and a Scanner for user input.
 */
public class PersonalFinanceApp {
    // Create a static instance of FinanceManager to handle financial transactions.
    private static FinanceManager manager = new FinanceManager(); // FinanceManager instance


    // Create a static Scanner to read input from the console.
    private static Scanner scanner = new Scanner(System.in); // For user input


    /**
     * The main method serves as the entry point of the application.
     * It displays a menu to the user and processes menu selections in a loop until the user exits.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Personal Finance Manager!");
        boolean running = true;
        // Main loop: continues to display the menu until the user chooses to exit.
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Transaction");
            System.out.println("2. View Balance");
            System.out.println("3. View All Transactions");
            System.out.println("4. Generate Summary");
            System.out.println("5. View Spending by Category");
            System.out.println("6. Delete Transaction");
            System.out.println("7. Update Transaction");          // New option for updating a transaction
            System.out.println("8. Interactive Q&A");               // New option for interactive finance-related Q&A
            System.out.println("9. Exit");


            // Get the user's menu choice
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after reading the number


            // Process the user's choice with a switch-case structure.
            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    viewBalance();
                    break;
                case 3:
                    viewTransactions();
                    break;
                case 4:
                    generateSummary();
                    break;
                case 5:
                    viewSpendingByCategory();
                    break;
                case 6:
                    deleteTransaction();
                    break;
                case 7:
                    updateTransaction();  // Allow the user to update an existing transaction
                    break;
                case 8:
                    interactiveQandA();   // Launch the interactive Q&A session
                    break;
                case 9:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    /**
     * Prompts the user to add a new transaction by entering details such as type, category,
     * amount, date, and description. Validates the input and adds the transaction using the FinanceManager.
     */
    private static void addTransaction() {
        System.out.print("Enter type (Income/Expense): ");
        String type = scanner.nextLine();
        // Validate the type input; must be either "Income" or "Expense"
        while (!Objects.equals(type, "Income") && !Objects.equals(type, "Expense")) {
            System.out.println("Invalid Input. Please enter 'Income' or 'Expense'.");
            System.out.print("Enter type (Income/Expense): ");
            type = scanner.nextLine();
        }


        // Prompt for the transaction category
        System.out.print("Enter category: ");
        String category = scanner.nextLine();


        // Prompt for the transaction amount
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline


        // Prompt for the transaction date in the specified format
        System.out.print("Enter date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();


        // Prompt for a brief description of the transaction
        System.out.print("Enter description: ");
        String description = scanner.nextLine();


        try {
            // Parse the input date string into a Date object
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            // Add the transaction using the FinanceManager instance
            manager.addTransaction(type, category, amount, date, description);
            System.out.println("Transaction added successfully.");
            // Display the updated balance after adding the transaction
            viewBalance();
        } catch (Exception e) {
            // Catch any exceptions (e.g., parse errors) and notify the user of an invalid date format.
            System.out.println("Invalid date format.");
        }
    }


    /**
     * Retrieves and displays the current balance from the FinanceManager.
     * The balance is formatted to two decimal places.
     */
    private static void viewBalance() {
        System.out.printf("Current Balance: $%.2f%n", manager.getBalance());
    }


    /**
     * Retrieves the list of all transactions from the FinanceManager and displays them.
     * If no transactions are recorded, it informs the user accordingly.
     */
    private static void viewTransactions() {
        List<Transaction> transactions = manager.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            // Loop through the list and display each transaction with an index.
            for (int i = 0; i < transactions.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, transactions.get(i).toString());
            }
        }
    }


    /**
     * Generates a financial summary for transactions within a user-specified date range.
     * It prompts the user for a start and end date, then calls the FinanceManager's printSummary method.
     */
    private static void generateSummary() {
        // Get the date range from the user
        Date[] dates = getDateRange();
        // Generate and print the summary for the provided date range
        manager.printSummary(dates[0], dates[1]);
    }


    /**
     * Displays spending by category for transactions within a user-specified date range.
     * It prompts the user for a start and end date, then calls the FinanceManager's printSpendingByCategory method.
     */
    private static void viewSpendingByCategory() {
        // Get the date range from the user
        Date[] dates = getDateRange();
        // Generate and print the spending report by category for the provided date range
        manager.printSpendingByCategory(dates[0], dates[1]);
    }


    /**
     * Deletes a transaction based on the index selected by the user.
     * It first displays all transactions, then prompts the user for the transaction index to delete.
     * After deletion, it shows the updated balance.
     */
    private static void deleteTransaction() {
        // Display the list of transactions so the user can choose one to delete
        viewTransactions();
        System.out.print("Enter the transaction index to delete: ");
        int index = scanner.nextInt();
        // Delete the specified transaction using the FinanceManager
        manager.deleteTransaction(index);
        System.out.println("Transaction deleted.");
        // Display the updated balance after deletion
        viewBalance();
    }


    /**
     * Updates an existing transaction.
     * The user selects a transaction by its index, then enters new details (type, category, amount, date, description).
     * The FinanceManager is then used to update the transaction.
     */
    private static void updateTransaction() {
        // Display all transactions for the user to choose one to update.
        viewTransactions();
        System.out.print("Enter the transaction index to update: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline


        // Prompt the user for new transaction type with validation.
        System.out.print("Enter new type (Income/Expense): ");
        String type = scanner.nextLine();
        while (!Objects.equals(type, "Income") && !Objects.equals(type, "Expense")) {
            System.out.println("Invalid Input. Please enter 'Income' or 'Expense'.");
            System.out.print("Enter new type (Income/Expense): ");
            type = scanner.nextLine();
        }


        // Prompt for new category
        System.out.print("Enter new category: ");
        String category = scanner.nextLine();


        // Prompt for new amount
        System.out.print("Enter new amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline


        // Prompt for new date in the specified format
        System.out.print("Enter new date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();


        // Prompt for new description
        System.out.print("Enter new description: ");
        String description = scanner.nextLine();


        try {
            // Parse the new date string into a Date object
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            // Create a new Transaction object with the updated details
            Transaction newTransaction = new Transaction(type, category, amount, date, description);
            // Update the transaction in the FinanceManager using the specified index
            manager.updateTransaction(index, newTransaction);
            System.out.println("Transaction updated successfully.");
            // Display the updated balance after the transaction update
            viewBalance();
        } catch (Exception e) {
            // Catch any exceptions (e.g., date parsing errors) and notify the user.
            System.out.println("Invalid input. Update failed.");
        }
    }


    /**
     * Prompts the user to enter a date range (start date and end date) in the format "yyyy-MM-dd".
     * It then parses these inputs into Date objects and returns them in an array.
     *
     * @return An array containing the start date and end date, or {null, null} if parsing fails.
     */
    private static Date[] getDateRange() {
        try {
            System.out.print("Enter start date (yyyy-MM-dd): ");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
            System.out.print("Enter end date (yyyy-MM-dd): ");
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
            return new Date[]{startDate, endDate};
        } catch (Exception e) {
            // Notify the user if the date format is invalid and return null dates.
            System.out.println("Invalid date format.");
            return new Date[]{null, null};
        }
    }


    /**
     * Provides an interactive Q&A session where the user can ask finance-related questions.
     * The session runs in a loop until the user types "exit". It uses simple keyword matching
     * to determine the appropriate response.
     */
    private static void interactiveQandA() {
        System.out.println("Interactive Q&A: Ask any finance-related question (type 'exit' to quit)");
        while (true) {
            System.out.print("Your question: ");
            String question = scanner.nextLine();
            // Exit the Q&A loop if the user types "exit"
            if (question.equalsIgnoreCase("exit")) {
                break;
            }
            // Check for keywords in the question and respond accordingly
            if (question.toLowerCase().contains("balance")) {
                System.out.printf("Your current balance is: $%.2f%n", manager.getBalance());
            } else if (question.toLowerCase().contains("summary")) {
                System.out.println("To generate a summary, please use the Generate Summary option in the menu.");
            } else if (question.toLowerCase().contains("update")) {
                System.out.println("To update a transaction, select the Update Transaction option from the menu.");
            } else if (question.toLowerCase().contains("delete")) {
                System.out.println("To delete a transaction, select the Delete Transaction option from the menu.");
            } else if (question.toLowerCase().contains("category")) {
                System.out.println("To view spending by category, choose the View Spending by Category option.");
            } else if (question.toLowerCase().contains("add")) {
                System.out.println("To add a transaction, select the Add Transaction option from the menu.");
            } else if (question.contains("income") || question.contains("expense")) {
                System.out.println("You can add income or expenses by clicking 'Add Transaction'.");
            } else {
                // Default response if no keyword matches are found.
                System.out.println("I'm sorry, I don't have an answer for that question.");
            }
        }
    }
}
