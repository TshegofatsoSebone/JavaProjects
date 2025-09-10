/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import model.Book;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paledi Sebene
 */
public class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) { books.add(book); }

    public void showAllBooks() {
        if (books.isEmpty()) System.out.println("No books in the library.");
        else books.forEach(System.out::println);
    }

    // Case-insensitive search
    public Book searchBookByTitle(String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) return b;
        }
        return null;
    }

    public List<Book> getBooks() { return books; }
}
