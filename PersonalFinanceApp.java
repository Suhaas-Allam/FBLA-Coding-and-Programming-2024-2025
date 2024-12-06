import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
public class PersonalFinanceApp {
    private static FinanceManager manager = new FinanceManager();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Welcome to the Personal Finance Manager!");
        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Transaction");
            System.out.println("2. View Balance");
            System.out.println("3. View All Transactions");
            System.out.println("4. Generate Summary");
            System.out.println("5. View Spending by Category");
            System.out.println("6. Delete Transaction");
            System.out.println("7. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    System.out.printf("Current Balance: $%.2f%n", manager.getBalance());
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
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void addTransaction() {
        System.out.print("Enter type (Income/Expense): ");
        String type = scanner.nextLine();
        while (!Objects.equals(type, "Income") && !Objects.equals(type, "Expense")){
            System.out.println("Invalid Input");
            System.out.print("Enter type (Income/Expense): ");
            type = scanner.nextLine();
        }
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            manager.addTransaction(type, category, amount, date, description);
            System.out.println("Transaction added successfully.");
        } catch (Exception e) {
            System.out.println("Invalid date format.");
        }
    }
    private static void viewTransactions() {
        List<Transaction> transactions = manager.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (int i = 0; i < transactions.size(); i++) {
                System.out.printf("%d. %s%n", i+1, transactions.get(i).toString());
            }
        }
    }
    private static void generateSummary() {
        Date[] dates = getDateRange();
        manager.printSummary(dates[0], dates[1]);
    }
    private static void viewSpendingByCategory() {
        Date[] dates = getDateRange();
        manager.printSpendingByCategory(dates[0], dates[1]);
    }
    private static void deleteTransaction() {
        viewTransactions();
        System.out.print("Enter the transaction index to delete: ");
        int index = scanner.nextInt();
        manager.deleteTransaction(index-1);
        System.out.println("Transaction deleted.");
    }
    private static Date[] getDateRange() {
        try {
            System.out.print("Enter start date (yyyy-MM-dd): ");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
            System.out.print("Enter end date (yyyy-MM-dd): ");
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
            return new Date[]{startDate, endDate};
        } catch (Exception e) {
            System.out.println("Invalid date format.");
            return new Date[]{null, null};
        }
    }
}
