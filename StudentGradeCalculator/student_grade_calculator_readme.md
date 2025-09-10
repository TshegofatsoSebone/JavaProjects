 Student Grade Calculator

 Overview

**Student Grade Calculator** is a Java desktop application that allows users to manage student grades, calculate final scores based on customizable grading weights, and generate letter grades. The program supports:

- Adding, editing, and deleting student records  
- Calculating final scores automatically based on **Assignment, Test, and Exam** weights  
- Loading and saving student data from/to a **CSV file**  
- Adjusting grading weights dynamically  

The application is designed with a **professional GUI** using **Swing** and is **portfolio-ready**.

---

features

1. **Add/Edit Student** – Enter student name and scores for assignments, tests, and exams.  
2. **Delete Student** – Remove selected students from the grade book.  
3. **Load CSV** – Import student data from a CSV file (`students.csv`).  
4. **Save CSV** – Export student data along with final scores and letter grades.  
5. **Weights Adjustment** – Customize the weights of assignments, tests, and exams.  
6. **Automatic Calculation** – Final score and letter grade are recalculated whenever grades or weights change.

---

Requirements

- **Java Development Kit (JDK) 8 or higher**  
- Optional: **IDE** such as IntelliJ, Eclipse, or VS Code for easier compilation  
- A **CSV file** (`students.csv`) with the following format:

sv
Name,Assignment,Test,Exam
Alice,85,78,92
Bob,70,82,75
Charlie,90,88,94


Folder Structure


StudentGradeCalculator/
├── src/
│   └── studentgradecalculator/
│       ├── App.java
│       ├── Student.java
│       ├── GradePolicy.java
│       ├── GradeBook.java
│       ├── StudentTableModel.java
│       ├── AddEditStudentDialog.java
│       ├── WeightsDialog.java
│       └── MainFrame.java
├── students.csv
└── README.md


---

#How to Compile and Run

1. Using Command Line

1. Open terminal/command prompt and navigate to the `src` folder:

bash
cd path/to/StudentGradeCalculator/src


2. Compile all Java files:

ash
javac -d ../out studentgradecalculator/*.java


3. Run the program:

bash
java -cp ../out studentgradecalculator.App


---

 2. Using an IDE

1. Create a new *Java Project*.  
2. Copy all `.java` files into `src/studentgradecalculator/`.  
3. Make sure the *package declaration* at the top is:

java
package studentgradecalculator;


4. Build/Compile the project.  
5. Run the `App.java` file.  

---

 How to Use the Application

1. **Load CSV** – Click “Load CSV” and select `students.csv` to populate the table.  
2. **Add Student** – Click “Add” and enter student name and scores.  
3. **Edit Student** – Select a student and click “Edit” to modify their details.  
4. **Delete Student** – Select one or more students and click “Delete”.  
5. **Adjust Weights** – Click “Weights” to change assignment, test, and exam weights.  
6. **Save CSV** – Click “Save CSV” to export all data, including final scores and letter grades.  

> Note: Scores must be between 0 and 100. Weights must sum to 1.0.

---

## Sample CSV File

csv
Name,Assignment,Test,Exam
Alice,85,78,92
Bob,70,82,75
Charlie,90,88,94
Diana,65,70,60
Ethan,80,85,78
Fiona,92,90,96
George,55,60,58
Hannah,88,84,90
Ian,73,76,70
Julia,95,98,97


---

## Notes

- The application automatically calculates **final scores** and **letter grades** using the formu

Final Score = Assignment * assignmentWeight + Test * testWeight + Exam * examWeight


- Letter grades are assigned as:

| Score | Grade |
|-------|-------|
| 90+   | A     |
| 80–89 | B     |
| 70–79 | C     |
| 60–69 | D     |
| <60   | F     |

- All student data is stored in-memory and can be exported to CSV for persistence.  

