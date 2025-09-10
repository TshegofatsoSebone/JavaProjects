/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentgradecalculator;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Paledi Sebene
 */
public class StudentTableModel extends AbstractTableModel {
    private final GradeBook gradeBook;
    private final String[] columns = {"Name", "Assignment", "Test", "Exam", "Final", "Letter"};

    public StudentTableModel(GradeBook gradeBook) {
        this.gradeBook = gradeBook;
    }

    @Override
    public int getRowCount() {
        return gradeBook.getStudents().size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student s = gradeBook.getStudents().get(rowIndex);
        switch (columnIndex) {
            case 0: return s.getName();
            case 1: return s.getAssignmentScore();
            case 2: return s.getTestScore();
            case 3: return s.getExamScore();
            case 4: return s.getFinalScore();
            case 5: return s.getLetterGrade();
            default: return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}