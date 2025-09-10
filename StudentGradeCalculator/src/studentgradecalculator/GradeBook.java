/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentgradecalculator;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author Paledi Sebene
 */
public class GradeBook {
    private final List<Student> students = new ArrayList<>();
    private GradePolicy policy = new GradePolicy(0.3, 0.3, 0.4);

    public List<Student> getStudents() { return students; }
    public GradePolicy getPolicy() { return policy; }

    public void setPolicy(GradePolicy newPolicy) {
        this.policy = newPolicy;
        recalcAll();
    }

    public void add(Student s) {
        s.recalc(policy);
        students.add(s);
    }

    public void removeIndices(List<Integer> indicesDesc) {
        for (int idx : indicesDesc) if (idx >= 0 && idx < students.size()) students.remove(idx);
    }

    public void clear() { students.clear(); }

    public void recalcAll() {
        for (Student s : students) s.recalc(policy);
    }

    public void loadFromCsv(Path path) throws IOException {
        clear();
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            boolean headerChecked = false;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("\\s*,\\s*");
                if (!headerChecked) {
                    headerChecked = true;
                    if (parts.length >= 4 && parts[0].equalsIgnoreCase("Name")) continue;
                }
                if (parts.length < 4) continue;
                String name = parts[0];
                double a = Double.parseDouble(parts[1]);
                double t = Double.parseDouble(parts[2]);
                double e = Double.parseDouble(parts[3]);
                validateScore(a); validateScore(t); validateScore(e);
                add(new Student(name, a, t, e));
            }
        }
    }

    public void saveResultsCsv(Path path) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write("Name,Assignment,Test,Exam,Final,Letter");
            bw.newLine();
            for (Student s : students) {
                bw.write(String.format("%s,%.2f,%.2f,%.2f,%.2f,%s",
                        s.getName(), s.getAssignmentScore(), s.getTestScore(),
                        s.getExamScore(), s.getFinalScore(), s.getLetterGrade()));
                bw.newLine();
            }
        }
    }

    public static void validateScore(double v) {
        if (v < 0 || v > 100) throw new IllegalArgumentException("Scores must be between 0 and 100.");
    }
}