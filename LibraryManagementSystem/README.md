 Library Management System (Java, OOP)

This is a Library Management System** built in **Core Java** using
**Object-Oriented Programming (OOP)** principles.\
The system supports two types of users:

-   **Admin**: Can add or delete books.\
-   **User**: Can register, log in, borrow, return, and view their
    borrowed books.

The system uses **file-based storage** (users.dat`, `books.dat`) to
persist data across runs.\
Books include **default sample books** that will always exist unless
deleted by the admin.

------------------------------------------------------------------------

 Requirements

-   **Java JDK 8 or higher**\
-   **NetBeans IDE 12+** (or any Java IDE)\
-   Operating System: Windows / Linux / macOS\
-   Minimum PC Specs:
    -   CPU: Intel i3 or equivalent\
    -   RAM: 4GB+\
    -   Disk: 500MB free



------------------------------------------------------------------------

 How to Compile and Run

 Option 1: Using NetBeans IDE

1.  Open **NetBeans**.\
2.  Click **File \> New Project \> Java \> Java Application**.\
3.  Name it **LibraryManagementSystem**.\
4.  Inside the project `src` folder, create the packages:
    -   `model`\
    -   `service`\
    -   `util`\
5.  Copy the provided `.java` files into the corresponding packages.\
6.  Create a `data` folder under the **project root** (same level as
    `src`).\
7.  Set `LibrarySystemAdvanced.java` as the **Main Class**.\
8.  Press **Run** (F6).

------------------------------------------------------------------------

 Option 2: Using Command Line

1.  Open a terminal or command prompt.\

2.  Navigate to the project's `src` directory.\

3.  Compile all `.java` files:

     bash
    javac model/*.java service/*.java util/*.java LibrarySystemAdvanced.java
    

4.  Run the program:

    bash
    java LibrarySystemAdvanced
    

 Ensure you create a `data` folder in the project root so that
`users.dat` and `books.dat` can be saved.

------------------------------------------------------------------------

 Login Details

-   **Admin Login**
    -   Username: `admin`\
    -   Password: `qwert@admin`
-   **User Login**
    -   Users must **register first** with a username and password.\
    -   Passwords are stored in **hashed format** for security.

------------------------------------------------------------------------

 Features

✅ User registration and login\
✅ Password hashing for security\
✅ Borrow and return books\
✅ Track borrowed books per user\
✅ Admin panel for book management\
✅ Persistent storage of users and books\
✅ Default sample books always included\
✅ Case-insensitive book search\
✅ Safe input handling (no program crashes)

------------------------------------------------------------------------

 Example Usage

1.  Run the program.\
2.  Choose `Register` → create a user account.\
3.  Login as the user → view, borrow, and return books.\
4.  Login as `admin` → add or delete books.\
5.  Exit → all data is saved to the `data` folder.

------------------------------------------------------------------------

💻 Author

Developed as part of a **Java OOP portfolio project** for showcasing
skills in:\
- Object-Oriented Design\
- File Persistence\
- Secure Authentication\
- Admin/User Role Management
