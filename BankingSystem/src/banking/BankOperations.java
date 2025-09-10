/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banking;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
/**
 *
 * @author Paledi Sebene
 */

public class BankOperations {

    
   static final String DATA_DIR = "data";
    static final String ACCOUNTS_DIR = DATA_DIR + File.separator + "accounts";

    static {
        new File(ACCOUNTS_DIR).mkdirs();
        new File(DATA_DIR + File.separator + "statements").mkdirs();
    }

    private static File getAccountFile(String accNum) {
        return new File(ACCOUNTS_DIR + File.separator + accNum + ".dat");
    }

    public static void createAccount(Account account) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getAccountFile(account.getAccountNumber())))) {
            oos.writeObject(account);
            ConsoleUtils.printSuccess("Account created successfully.");
        } catch (IOException e) {
            ConsoleUtils.printError("Error creating account: " + e.getMessage());
        }
    }

    public static void deleteAccount(String accNum) {
        File file = getAccountFile(accNum);
        if (file.exists() && file.delete()) {
            ConsoleUtils.printSuccess("Account deleted successfully.");
        } else {
            ConsoleUtils.printError("Account not found.");
        }
    }

    public static Account getAccount(String accNum) {
        File file = getAccountFile(accNum);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Account) ois.readObject();
        } catch (Exception e) {
            ConsoleUtils.printError("Error reading account: " + e.getMessage());
            return null;
        }
    }

    private static void saveAccount(Account acc) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getAccountFile(acc.getAccountNumber())))) {
            oos.writeObject(acc);
        } catch (IOException e) {
            ConsoleUtils.printError("Error saving account: " + e.getMessage());
        }
    }

    public static void deposit(String accNum, double amount, String pin) {
        Account acc = getAccount(accNum);
        if (acc == null) { ConsoleUtils.printError("Account not found."); return; }
        if (!acc.authenticate(pin)) { ConsoleUtils.printError("Incorrect PIN."); return; }
        acc.deposit(amount);
        saveAccount(acc);
        ConsoleUtils.printSuccess("Deposit successful. New balance: " + String.format("%.2f", acc.getBalance()));
    }

    public static void withdraw(String accNum, double amount, String pin) {
        Account acc = getAccount(accNum);
        if (acc == null) { ConsoleUtils.printError("Account not found."); return; }
        if (!acc.authenticate(pin)) { ConsoleUtils.printError("Incorrect PIN."); return; }
        if (acc.withdraw(amount)) {
            saveAccount(acc);
            ConsoleUtils.printSuccess("Withdrawal successful. New balance: " + String.format("%.2f", acc.getBalance()));
        } else {
            ConsoleUtils.printError("Insufficient funds.");
        }
    }

    public static void transferMoney(String fromAccNum, String toAccNum, double amount, String pin) {
        Account fromAcc = getAccount(fromAccNum);
        Account toAcc = getAccount(toAccNum);
        if (fromAcc == null || toAcc == null) { ConsoleUtils.printError("One or both accounts not found."); return; }
        if (!fromAcc.authenticate(pin)) { ConsoleUtils.printError("Incorrect PIN. Transfer cancelled."); return; }

        if (fromAcc.withdraw(amount)) {
            toAcc.deposit(amount);
            fromAcc.logCustomTransaction("Transferred -" + amount + " to " + toAcc.getAccountNumber());
            toAcc.logCustomTransaction("Received +" + amount + " from " + fromAcc.getAccountNumber());
            saveAccount(fromAcc);
            saveAccount(toAcc);
            ConsoleUtils.printSuccess("Transfer successful!");
        } else {
            ConsoleUtils.printError("Insufficient balance.");
        }
    }

    public static void applyAutoInterest() {
        File folder = new File(ACCOUNTS_DIR);
        File[] files = folder.listFiles();
        if (files == null) return;

        double monthlyRate = 0.0025;

        for (File file : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Account acc = (Account) ois.readObject();
                if ("savings".equalsIgnoreCase(acc.getType())) {
                    LocalDate lastDate = acc.getLastInterestDate();
                    LocalDate today = LocalDate.now();
                    long daysPassed = ChronoUnit.DAYS.between(lastDate, today);
                    if (daysPassed >= 30) {
                        double interest = acc.getBalance() * monthlyRate;
                        acc.addInterest(interest);
                        acc.setLastInterestDate(today);
                        saveAccount(acc);
                        ConsoleUtils.printInfo("Interest applied to account " + acc.getAccountNumber());
                    }
                }
            } catch (Exception e) { ConsoleUtils.printError("Error applying interest for: " + file.getName()); }
        }
    }

    public static void listAllAccounts() {
        applyAutoInterest();
        File folder = new File(ACCOUNTS_DIR);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) { ConsoleUtils.printWarning("No accounts found."); return; }

        System.out.printf("%-15s %-20s %-12s %-10s%n", "Account Number", "Holder Name", "Balance", "Type");
        System.out.println("----------------------------------------------------");
        for (File file : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Account acc = (Account) ois.readObject();
                System.out.printf("%-15s %-20s %-12.2f %-10s%n", acc.getAccountNumber(), acc.getHolderName(), acc.getBalance(), acc.getType());
            } catch (Exception e) { ConsoleUtils.printError("Error reading account: " + file.getName()); }
        }
    }

    public static void searchAccount(String query) {
        File folder = new File(ACCOUNTS_DIR);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) { ConsoleUtils.printWarning("No accounts found."); return; }

        boolean found = false;
        System.out.printf("%-15s %-20s %-12s %-10s%n", "Account Number", "Holder Name", "Balance", "Type");
        System.out.println("----------------------------------------------------");

        for (File file : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Account acc = (Account) ois.readObject();
                if (acc.getAccountNumber().equalsIgnoreCase(query) || acc.getHolderName().toLowerCase().contains(query.toLowerCase())) {
                    System.out.printf("%-15s %-20s %-12.2f %-10s%n", acc.getAccountNumber(), acc.getHolderName(), acc.getBalance(), acc.getType());
                    found = true;
                }
            } catch (Exception e) { ConsoleUtils.printError("Error reading account: " + file.getName()); }
        }
        if (!found) ConsoleUtils.printWarning("No matching accounts found.");
    }

    public static void showStatement(String accNum, String pin) {
        Account acc = getAccount(accNum);
        if (acc == null) { ConsoleUtils.printError("Account not found."); return; }
        if (!acc.authenticate(pin)) { ConsoleUtils.printError("Incorrect PIN."); return; }

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Account Statement ===\n");
        sb.append("Account: ").append(acc.getAccountNumber()).append("\n");
        sb.append("Holder: ").append(acc.getHolderName()).append("\n");
        sb.append("Type: ").append(acc.getType()).append("\n");
        sb.append("Balance: ").append(String.format("%.2f", acc.getBalance())).append("\n");
        sb.append("-----------------------------\n");
        for (String entry : acc.getTransactions()) sb.append(entry).append("\n");
        sb.append("-----------------------------\n");

        ConsoleUtils.printInfo(sb.toString());

        // Save under data/statements
        File folder = new File(DATA_DIR + File.separator + "statements");
        if (!folder.exists()) folder.mkdirs();
        File file = new File(folder, "statement_" + acc.getAccountNumber() + ".txt");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
            ConsoleUtils.printSuccess("Statement exported to: " + file.getAbsolutePath());
        } catch (IOException e) { ConsoleUtils.printError("Error writing statement to file."); }
    }

    // Auto-generate account numbers
    private static int getLastAccountNumber() {
        File file = new File(DATA_DIR + File.separator + "lastAccountNumber.txt");
        if (!file.exists()) return 100000;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return Integer.parseInt(br.readLine().trim());
        } catch (Exception e) { return 100000; }
    }

    private static void saveLastAccountNumber(int number) {
        File file = new File(DATA_DIR + File.separator + "lastAccountNumber.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) { bw.write(String.valueOf(number)); }
        catch (IOException e) { ConsoleUtils.printError("Error saving last account number."); }
    }

    public static String generateAccountNumber() {
        int lastNum = getLastAccountNumber();
        int newNum = lastNum + 1;
        saveLastAccountNumber(newNum);
        return String.valueOf(newNum);
    }
}