import java.util.Scanner;

class Account {
    protected int accnumber;
    protected double balance;

    public Account(int accnumber, double balance) {
        this.accnumber = accnumber;
        this.balance = balance;
    }
}

class SBAccount extends Account {
    public SBAccount(int accnumber, double init_balance) {
        super(accnumber, init_balance);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. New balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        double minBalance = 1000;
        if (balance - amount >= minBalance) {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
        } else {
            System.out.println("Insufficient balance for withdrawal.");
        }
    }

    public void calc_interest() {
        double interestRate = 0.04;
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest calculated and added to the balance. New balance: " + balance);
    }
}

class FDAccount extends Account {
    private int period;

    public FDAccount(int accnumber, int period, double depositAmount) {
        super(accnumber, depositAmount);
        this.period = period;
    }

    public double calc_interest() {
        double interestRate = 0.0825;
        double interest = balance * interestRate * period;
        return interest;
    }

    public void close() {
        double interest = calc_interest();
        balance += interest;
        System.out.println("Account closed. Final balance after adding interest: " + balance);
    }
}

class Customer {
    private int cust_id;
    private String name;
    private String address;
    private SBAccount sbAccount;
    private FDAccount fdAccount;

    public Customer(int cust_id, String name, String address) {
        this.cust_id = cust_id;
        this.name = name;
        this.address = address;
    }

    public void createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the type of account to create:");
        System.out.println("1. Savings Account");
        System.out.println("2. Fixed Deposit Account");
        int type = scanner.nextInt();

        if (type == 1) {
            System.out.print("Enter account number: ");
            int accnumber = scanner.nextInt();
            System.out.print("Enter initial balance: ");
            double init_balance = scanner.nextDouble();
            sbAccount = new SBAccount(accnumber, init_balance);
        } else if (type == 2) {
            System.out.print("Enter account number: ");
            int accnumber = scanner.nextInt();
            System.out.print("Enter deposit amount: ");
            double depositAmount = scanner.nextDouble();
            System.out.print("Enter the period (in years): ");
            int period = scanner.nextInt();
            fdAccount = new FDAccount(accnumber, period, depositAmount);
        } else {
            System.out.println("Invalid account type choice.");
        }
    }

    public void transaction(int type, double amount) {
        if (type == 1) {
            sbAccount.deposit(amount);
        } else if (type == 2) {
            sbAccount.withdraw(amount);
        } else if (type == 3) {
            sbAccount.calc_interest();
        } else if (type == 4) {
            fdAccount.close();
        }
    }
}

public class BankDemo {
    public static void main(String[] args) {
        Customer[] customers = new Customer[5];

        // Create customer objects
        customers[0] = new Customer(1, "John Doe", "123 Main St.");
        customers[1] = new Customer(2, "Jane Smith", "456 Oak St.");
        customers[2] = new Customer(3, "Bob Johnson", "789 Elm St.");
        customers[3] = new Customer(4, "Alice Brown", "321 Pine St.");
        customers[4] = new Customer(5, "Mike Wilson", "654 Cedar St.");

        Scanner scanner = new Scanner(System.in);

        // Perform manipulations on customer accounts
        for (Customer customer : customers) {
            customer.createAccount();
            System.out.println("Enter transaction type:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Calculate interest (only for Savings Account)");
            System.out.println("4. Close FD Account (only for Fixed Deposit Account)");
            int transactionType = scanner.nextInt();
            System.out.print("Enter transaction amount: ");
            double amount = scanner.nextDouble();
            customer.transaction(transactionType, amount);
        }
    }
}
