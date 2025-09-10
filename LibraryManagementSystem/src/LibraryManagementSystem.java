/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import model.User;
import model.Book;
import service.Library;
import util.DataStorage;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Paledi Sebene
 */
    public class LibraryManagementSystem  {
    private static Scanner scanner = new Scanner(System.in);
    private static List<User> users;
    private static Library library;

    // Admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "qwert@admin";

    // Hardcoded sample books
    private static final Book[] SAMPLE_BOOKS = {
        new Book("Clean Code", "Robert C. Martin"),
        new Book("Effective Java", "Joshua Bloch"),
        new Book("Head First Java", "Kathy Sierra")
    };

    public static void main(String[] args) {
        // Load users
        users = DataStorage.loadUsers();

        // Load books
        library = new Library();
        library.getBooks().addAll(DataStorage.loadBooks());

        // Ensure sample books always exist unless deleted
        for (Book sample : SAMPLE_BOOKS) {
            boolean exists = false;
            for (Book b : library.getBooks()) {
                if (b.getTitle().equalsIgnoreCase(sample.getTitle())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) library.addBook(sample);
        }

        // Save books after restoring missing samples
        DataStorage.saveBooks(library.getBooks());

        while (true) {
            System.out.println("\n=== Advanced Library System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Goodbye!");
                    DataStorage.saveUsers(users);
                    DataStorage.saveBooks(library.getBooks());
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }
    }

    // Safe integer input
    private static int getIntInput() {
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // --- Registration ---
    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        users.add(new User(username, password));
        DataStorage.saveUsers(users);
        System.out.println("Registration successful!");
    }

    // --- Login ---
    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Admin login
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Admin login successful!");
            adminMenu();
            return;
        }

        // User login
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.checkPassword(password)) {
                System.out.println("Login successful! Welcome " + username);
                userMenu(u);
                return;
            }
        }

        System.out.println("Invalid credentials.");
    }

    // --- Admin Menu ---
    private static void adminMenu() {
        int choice = -1;
        while (choice != 3) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");

            choice = getIntInput();
            switch (choice) {
                case 1 -> addBook();
                case 2 -> deleteBook();
                case 3 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please enter 1-3.");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        Book book = new Book(title, author);
        library.addBook(book);
        DataStorage.saveBooks(library.getBooks());
        System.out.println("Book added successfully!");
    }

    private static void deleteBook() {
        System.out.println("\n=== All Books in the Library ===");
        library.showAllBooks();

        System.out.print("Enter book title to delete: ");
        String title = scanner.nextLine();
        Book book = library.searchBookByTitle(title);

        if (book == null) System.out.println("Book not found.");
        else if (book.isBorrowed()) System.out.println("Cannot delete a borrowed book.");
        else {
            library.getBooks().remove(book);
            DataStorage.saveBooks(library.getBooks());
            System.out.println("Book deleted successfully!");
        }
    }

    // --- User Menu ---
    private static void userMenu(User user) {
        int choice = -1;
        while (choice != 5) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Show all books");
            System.out.println("2. Borrow book");
            System.out.println("3. Return book");
            System.out.println("4. My borrowed books");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            choice = getIntInput();
            switch (choice) {
                case 1 -> library.showAllBooks();
                case 2 -> borrowBook(user);
                case 3 -> returnBook(user);
                case 4 -> showBorrowedBooks(user);
                case 5 -> {
                    System.out.println("Logging out...");
                    DataStorage.saveUsers(users);
                    DataStorage.saveBooks(library.getBooks());
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    // --- User Actions ---
    private static void borrowBook(User user) {
        System.out.print("Enter book title to borrow: ");
        String title = scanner.nextLine();
        Book book = library.searchBookByTitle(title);

        if (book == null) System.out.println("Book not found.");
        else if (book.isBorrowed()) System.out.println("Book is already borrowed.");
        else {
            user.borrowBook(book);
            System.out.println("You borrowed: " + book.getTitle());
            DataStorage.saveUsers(users);
            DataStorage.saveBooks(library.getBooks());
        }
    }

    private static void returnBook(User user) {
        System.out.print("Enter book title to return: ");
        String title = scanner.nextLine();
        Book book = library.searchBookByTitle(title);

        if (book != null && user.getBorrowedBooks().contains(book)) {
            user.returnBook(book);
            System.out.println("You returned: " + book.getTitle());
            DataStorage.saveUsers(users);
            DataStorage.saveBooks(library.getBooks());
        } else System.out.println("You did not borrow this book.");
    }

    private static void showBorrowedBooks(User user) {
        List<Book> borrowed = user.getBorrowedBooks();
        if (borrowed.isEmpty()) System.out.println("You have not borrowed any books.");
        else {
            System.out.println("Your borrowed books:");
            borrowed.forEach(System.out::println);
        }
    }
}