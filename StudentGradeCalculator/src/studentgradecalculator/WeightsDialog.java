/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentgradecalculator;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Paledi Sebene
 */

public class WeightsDialog extends JDialog {
    private boolean confirmed = false;
    private double assignment, test, exam;

    public WeightsDialog(Frame owner, GradePolicy policy) {
        super(owner, true);
        setTitle("Set Grade Weights");
        setLayout(new GridLayout(4, 2, 5, 5));

        JTextField txtAssignment = new JTextField(String.valueOf(policy.getAssignmentWeight()));
        JTextField txtTest = new JTextField(String.valueOf(policy.getTestWeight()));
        JTextField txtExam = new JTextField(String.valueOf(policy.getExamWeight()));

        add(new JLabel("Assignment Weight:"));
        add(txtAssignment);
        add(new JLabel("Test Weight:"));
        add(txtTest);
        add(new JLabel("Exam Weight:"));
        add(txtExam);

        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        add(btnOk);
        add(btnCancel);

        btnOk.addActionListener(e -> {
            try {
                assignment = Double.parseDouble(txtAssignment.getText());
                test = Double.parseDouble(txtTest.getText());
                exam = Double.parseDouble(txtExam.getText());

                if (assignment < 0 || test < 0 || exam < 0) {
                    throw new IllegalArgumentException("Weights cannot be negative.");
                }
                if (Math.abs(assignment + test + exam - 1.0) > 1e-9) {
                    throw new IllegalArgumentException("Weights must sum to 1.0");
                }

                confirmed = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnCancel.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(owner);
    }

    public boolean isConfirmed() { return confirmed; }
    public double getAssignmentWeight() { return assignment; }
    public double getTestWeight() { return test; }
    public double getExamWeight() { return exam; }
}