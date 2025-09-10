/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentgradecalculator;

/**
 *
 * @author Paledi Sebene
 */


public class Student {
    private String name;
    private double assignmentScore;
    private double testScore;
    private double examScore;

    private double finalScore;
    private String letterGrade;

    public Student(String name, double assignmentScore, double testScore, double examScore) {
        this.name = name;
        this.assignmentScore = assignmentScore;
        this.testScore = testScore;
        this.examScore = examScore;
    }

    public void recalc(GradePolicy policy) {
        this.finalScore = policy.weightedScore(assignmentScore, testScore, examScore);
        this.letterGrade = policy.letterFor(finalScore);
    }

    // Getters / Setters
    public String getName() { return name; }
    public double getAssignmentScore() { return assignmentScore; }
    public double getTestScore() { return testScore; }
    public double getExamScore() { return examScore; }
    public double getFinalScore() { return finalScore; }
    public String getLetterGrade() { return letterGrade; }

    public void setName(String name) { this.name = name; }
    public void setAssignmentScore(double assignmentScore) { this.assignmentScore = assignmentScore; }
    public void setTestScore(double testScore) { this.testScore = testScore; }
    public void setExamScore(double examScore) { this.examScore = examScore; }

    @Override
    public String toString() {
        return name + " | Final: " + String.format("%.2f", finalScore) + " | Grade: " + letterGrade;
    }
}

