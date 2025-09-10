/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentgradecalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author Paledi Sebene
 */
public class AddEditStudentDialog extends JDialog {
    private final JTextField txtName = new JTextField(20);
    private final JTextField txtAssignment = new JTextField(5);
    private final JTextField txtTest = new JTextField(5);
    private final JTextField txtExam = new JTextField(5);

    private Student student;
    private boolean confirmed = false;

    public AddEditStudentDialog(Frame owner, Student existing) {
        super(owner, true);
        setTitle(existing == null ? "Add Student" : "Edit Student");
        setLayout(new GridLayout(5, 2, 5, 5));

        add(new JLabel("Name:"));
        add(txtName);
        add(new JLabel("Assignment Score:"));
        add(txtAssignment);
        add(new JLabel("Test Score:"));
        add(txtTest);
        add(new JLabel("Exam Score:"));
        add(txtExam);

        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        add(btnOk);
        add(btnCancel);

        if (existing != null) {
            txtName.setText(existing.getName());
            txtAssignment.setText(String.valueOf(existing.getAssignmentScore()));
            txtTest.setText(String.valueOf(existing.getTestScore()));
            txtExam.setText(String.valueOf(existing.getExamScore()));
        }

        btnOk.addActionListener(e -> {
            try {
                String name = txtName.getText().trim();
                double a = Double.parseDouble(txtAssignment.getText());
                double t = Double.parseDouble(txtTest.getText());
                double ex = Double.parseDouble(txtExam.getText());

                GradeBook.validateScore(a);
                GradeBook.validateScore(t);
                GradeBook.validateScore(ex);

                if (existing == null) student = new Student(name, a, t, ex);
                else {
                    existing.setName(name);
                    existing.setAssignmentScore(a);
                    existing.setTestScore(t);
                    existing.setExamScore(ex);
                    student = existing;
                }

                confirmed = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for scores.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnCancel.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(owner);
    }

    public Student getStudent() {
        return confirmed ? student : null;
    }
}