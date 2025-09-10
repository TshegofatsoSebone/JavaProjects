/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import util.PasswordUtils;
/**
 *
 * @author Paledi Sebene
 */
public class User implements Serializable {
    private String username;
    private String hashedPassword;
    private List<Book> borrowedBooks;

    public User(String username, String password) {
        this.username = username;
        this.hashedPassword = PasswordUtils.hashPassword(password);
        this.borrowedBooks = new ArrayList<>();
    }

    public String getUsername() { return username; }

    public boolean checkPassword(String password) {
        return this.hashedPassword.equals(PasswordUtils.hashPassword(password));
    }

    public List<Book> getBorrowedBooks() { return borrowedBooks; }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
        book.setBorrowed(true);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
        book.setBorrowed(false);
    }
}