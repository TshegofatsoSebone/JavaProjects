/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentgradecalculator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Paledi Sebene
 */




public class MainFrame extends JFrame {
    private final GradeBook gradeBook = new GradeBook();
    private final StudentTableModel tableModel = new StudentTableModel(gradeBook);
    private final JTable table = new JTable(tableModel);

    public MainFrame() {
        setTitle("Student Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel panelButtons = new JPanel();

        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        JButton btnLoad = new JButton("Load CSV");
        JButton btnSave = new JButton("Save CSV");
        JButton btnWeights = new JButton("Weights");

        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        panelButtons.add(btnWeights);

        add(panelButtons, BorderLayout.SOUTH);

        // Add Button
        btnAdd.addActionListener(e -> {
            AddEditStudentDialog dialog = new AddEditStudentDialog(this, null);
            dialog.setVisible(true);
            Student s = dialog.getStudent();
            if (s != null) {
                gradeBook.add(s);
                tableModel.fireTableDataChanged();
            }
        });

        // Edit Button
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Student s = gradeBook.getStudents().get(row);
                AddEditStudentDialog dialog = new AddEditStudentDialog(this, s);
                dialog.setVisible(true);
                tableModel.fireTableDataChanged();
            }
        });

        // Delete Button
        btnDelete.addActionListener(e -> {
            int[] rows = table.getSelectedRows();
            if (rows.length > 0) {
                List<Integer> indices = new java.util.ArrayList<>();
                for (int r : rows) indices.add(r);
                Collections.sort(indices, Collections.reverseOrder());
                gradeBook.removeIndices(indices);
                tableModel.fireTableDataChanged();
            }
        });

        // Load CSV Button
        btnLoad.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                Path path = chooser.getSelectedFile().toPath();
                try {
                    gradeBook.loadFromCsv(path);
                    tableModel.fireTableDataChanged();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error loading CSV: " + ex.getMessage());
                }
            }
        });

        // Save CSV Button
        btnSave.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showSaveDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                Path path = chooser.getSelectedFile().toPath();
                try {
                    gradeBook.saveResultsCsv(path);
                    JOptionPane.showMessageDialog(this, "Saved successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error saving CSV: " + ex.getMessage());
                }
            }
        });

        // Weights Button
        btnWeights.addActionListener(e -> {
            WeightsDialog dialog = new WeightsDialog(this, gradeBook.getPolicy());
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                gradeBook.setPolicy(new GradePolicy(
                        dialog.getAssignmentWeight(),
                        dialog.getTestWeight(),
                        dialog.getExamWeight()
                ));
                tableModel.fireTableDataChanged();
            }
        });
    }
}
