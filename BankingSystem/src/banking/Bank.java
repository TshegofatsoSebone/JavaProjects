/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banking;

import java.util.Scanner;
/**
 *
 * @author Paledi Sebene
 */
public class Bank {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankOperations.applyAutoInterest();

        while (true) {
            System.out.println(ConsoleUtils.CYAN + "\n=== Banking System ===" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.YELLOW + "1. Create Account" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.YELLOW + "2. Delete Account" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.YELLOW + "3. View Account" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.YELLOW + "4. Deposit Money" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.YELLOW + "5. Withdraw Money" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.YELLOW + "6. Transfer Money" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.YELLOW + "7. Show Account Statement" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.YELLOW + "8. List All Accounts" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.YELLOW + "9. Search Account (by Name or Number)" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.RED + "0. Exit" + ConsoleUtils.RESET);
            System.out.print(ConsoleUtils.CYAN + "Enter choice: " + ConsoleUtils.RESET);

            int choice;
            try { choice = Integer.parseInt(sc.nextLine().trim()); } 
            catch (NumberFormatException e) { ConsoleUtils.printError("Invalid input. Please enter a number."); continue; }

            switch (choice) {
                case 1: createAccount(sc); break;
                case 2: deleteAccount(sc); break;
                case 3: viewAccount(sc); break;
                case 4: depositMoney(sc); break;
                case 5: withdrawMoney(sc); break;
                case 6: transferMoney(sc); break;
                case 7: showStatement(sc); break;
                case 8: BankOperations.listAllAccounts(); break;
                case 9: searchAccount(sc); break;
                case 0: ConsoleUtils.printInfo("Exiting... Goodbye!"); sc.close(); return;
                default: ConsoleUtils.printError("Invalid choice."); break;
            }
        }
    }

    private static void createAccount(Scanner sc) {
        String accNum = BankOperations.generateAccountNumber();
        ConsoleUtils.printInfo("Generated account number: " + accNum);

        System.out.print("Enter holder name: ");
        String name = sc.nextLine().trim();

        String type;
        while (true) {
            System.out.print("Enter type (savings/current): ");
            type = sc.nextLine().trim().toLowerCase();
            if (type.equals("savings") || type.equals("current")) break;
            ConsoleUtils.printError("Invalid type. Enter 'savings' or 'current'.");
        }

        String pin;
        while (true) {
            System.out.print("Set a 4-digit PIN: ");
            pin = sc.nextLine().trim();
            if (pin.matches("\\d{4}")) break;
            ConsoleUtils.printError("Invalid PIN. Must be exactly 4 digits.");
        }

        Account acc = new Account(accNum, name, type, pin);
        ConsoleUtils.loading("Creating account");
        BankOperations.createAccount(acc);
    }

    private static void deleteAccount(Scanner sc) {
        System.out.print("Enter account number to delete: ");
        String accNum = sc.nextLine().trim();
        ConsoleUtils.loading("Deleting account");
        BankOperations.deleteAccount(accNum);
    }

    private static void viewAccount(Scanner sc) {
        System.out.print("Enter account number: ");
        String accNum = sc.nextLine().trim();
        Account acc = BankOperations.getAccount(accNum);
        if (acc != null) {
            System.out.print("Enter 4-digit PIN: ");
            String pin = sc.nextLine().trim();
            if (acc.authenticate(pin)) ConsoleUtils.printInfo(acc.toString());
            else ConsoleUtils.printError("Incorrect PIN.");
        } else ConsoleUtils.printError("Account not found.");
    }

    private static void depositMoney(Scanner sc) {
        System.out.print("Enter account number: ");
        String accNum = sc.nextLine().trim();
        System.out.print("Enter amount to deposit: ");
        double amount;
        try { amount = Double.parseDouble(sc.nextLine()); if (amount <= 0) throw new NumberFormatException(); }
        catch (NumberFormatException e) { ConsoleUtils.printError("Invalid amount."); return; }

        System.out.print("Enter 4-digit PIN: ");
        String pin = sc.nextLine().trim();
        ConsoleUtils.loading("Processing deposit");
        BankOperations.deposit(accNum, amount, pin);
    }

    private static void withdrawMoney(Scanner sc) {
        System.out.print("Enter account number: ");
        String accNum = sc.nextLine().trim();
        System.out.print("Enter amount to withdraw: ");
        double amount;
        try { amount = Double.parseDouble(sc.nextLine()); if (amount <= 0) throw new NumberFormatException(); }
        catch (NumberFormatException e) { ConsoleUtils.printError("Invalid amount."); return; }

        System.out.print("Enter 4-digit PIN: ");
        String pin = sc.nextLine().trim();
        ConsoleUtils.loading("Processing withdrawal");
        BankOperations.withdraw(accNum, amount, pin);
    }

    private static void transferMoney(Scanner sc) {
        BankOperations.listAllAccounts();
        System.out.print("Enter your account number: ");
        String fromAcc = sc.nextLine().trim();
        System.out.print("Enter recipient account number: ");
        String toAcc = sc.nextLine().trim();
        System.out.print("Enter amount to transfer: ");
        double amount;
        try { amount = Double.parseDouble(sc.nextLine()); if (amount <= 0) throw new NumberFormatException(); }
        catch (NumberFormatException e) { ConsoleUtils.printError("Invalid amount."); return; }

        System.out.print("Enter your 4-digit PIN: ");
        String pin = sc.nextLine().trim();
        ConsoleUtils.loading("Processing transfer");
        BankOperations.transferMoney(fromAcc, toAcc, amount, pin);
    }

    private static void showStatement(Scanner sc) {
        System.out.print("Enter account number: ");
        String accNum = sc.nextLine().trim();
        System.out.print("Enter 4-digit PIN: ");
        String pin = sc.nextLine().trim();
        ConsoleUtils.loading("Generating statement");
        BankOperations.showStatement(accNum, pin);
    }

    private static void searchAccount(Scanner sc) {
        System.out.print("Enter name or account number to search: ");
        String query = sc.nextLine().trim();
        BankOperations.searchAccount(query);
    }
}