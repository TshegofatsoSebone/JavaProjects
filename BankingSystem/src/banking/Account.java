/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banking;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paledi Sebene
 */

public class Account implements Serializable {
    private String accountNumber;
    private String holderName;
    private double balance;
    private String type; // savings or current
    private LocalDate lastInterestDate;
    private List<String> transactions;
    private String pinHash; // hashed 4-digit PIN

    public Account(String accountNumber, String holderName, String type, String pin) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.type = type;
        this.balance = 0.0;
        this.lastInterestDate = LocalDate.now();
        this.transactions = new ArrayList<>();
        setPin(pin);
        logCustomTransaction("Account created");
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public String getType() { return type; }
    public LocalDate getLastInterestDate() { return lastInterestDate; }
    public List<String> getTransactions() { return transactions; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            logCustomTransaction("Deposited +" + String.format("%.2f", amount));
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            logCustomTransaction("Withdrew -" + String.format("%.2f", amount));
            return true;
        }
        return false;
    }

    public void addInterest(double interest) {
        balance += interest;
        logCustomTransaction("Interest +" + String.format("%.2f", interest));
    }

    public void logCustomTransaction(String message) {
        String entry = LocalDate.now() + " - " + message;
        transactions.add(entry);
    }

    public void setLastInterestDate(LocalDate date) {
        this.lastInterestDate = date;
    }

    // PIN handling
    public void setPin(String pin) {
        this.pinHash = hashPin(pin);
    }

    public boolean authenticate(String pin) {
        return hashPin(pin).equals(this.pinHash);
    }

    private String hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(pin.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available");
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", holderName='" + holderName + '\'' +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                ", lastInterestDate=" + lastInterestDate +
                '}';
    }
}