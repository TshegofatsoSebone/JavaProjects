Banking System (Java Console Application)

 Overview
This is a **console-based banking system** implemented in Java. It simulates basic banking operations with features such as account management, deposits, withdrawals, transfers, and automatic interest calculations for savings accounts. All account data is stored in files, and account statements can be exported.  

The system is designed to be **robust, secure, and user-friendly**, with:

- 4-digit **hashed PIN authentication**
- Automatic **monthly interest** for savings accounts
- **File-based storage** for accounts and statements
- **Console colors and loading animations** for better UX
- Safe **input validation** to prevent crashes  

---

features

 Account Management
- Create new accounts with auto-generated account numbers
- Delete existing accounts
- View account details (requires PIN)

 Transactions
- Deposit money
- Withdraw money
- Transfer funds between accounts

 Account Statements
- Generate and view account statements
- Statements saved under `data/statements/`
- Each transaction logged with date and description

 Interest
- Savings accounts receive **automatic monthly interest**
- Interest is calculated based on real-time system dates

 Search & List Accounts
- List all accounts with balances
- Search for accounts by **account number or holder name**

 Security
- 4-digit PIN required for all sensitive operations
- PINs are hashed using SHA-256 for security

 User Interface
- Console-based menu with **color-coded messages**:
  - Green = Success
  - Red = Error
  - Yellow = Warnings
  - Cyan = Info
- Simple loading animations for deposit, withdrawal, transfer, and statement generation  

---

 Project Structure


BankingSystem/
│
├─ src/
│   └─ banking/
│       ├─ Account.java
│       ├─ Bank.java
│       ├─ BankOperations.java
│       └─ ConsoleUtils.java
│
├─ data/
│   ├─ accounts/        <-- All account files are stored here
│   └─ statements/      <-- All exported account statements are saved here
│
└─ README.md


---

 How to Run

1. **Open the project in NetBeans**  
2. Make sure the folders `data/accounts` and `data/statements` exist (the program will create them if they don’t).  
3. Run the `Bank.java` file.  
4. The **console menu** will appear with numbered options:  
   - Enter the number corresponding to your choice and follow prompts.
5. For sensitive operations like deposit, withdraw, or viewing statements, you will need your **4-digit PIN**.

---

 Notes
- **Invalid input** (like letters where numbers are expected) will be safely handled; the program will prompt for correct input without crashing.
- **Statements** are automatically exported under `data/statements/` as text files.
- Savings account interest is applied **automatically** if 30 days have passed since the last interest calculation.

---

 Example Usage

text
=== Banking System ===
1. Create Account
2. Delete Account
3. View Account
4. Deposit Money
5. Withdraw Money
6. Transfer Money
7. Show Account Statement
8. List All Accounts
9. Search Account (by Name or Number)
0. Exit
Enter choice: 1


- Enter account details and a **4-digit PIN**  
- Account is created and saved in `data/accounts`  
- Transactions are logged and can be exported as statements

---

 Requirements
- **Java 8** or higher
- NetBeans IDE (or any Java IDE that supports standard Java projects)

---

 Future Improvements
- GUI version using JavaFX or Swing  
- Monthly report generation and summary  
- Multi-user support with login sessions

