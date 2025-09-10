/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentgradecalculator;

/**
 *
 * @author Paledi Sebene
 */


public class GradePolicy {
    private double assignmentWeight;
    private double testWeight;
    private double examWeight;

    public GradePolicy(double assignmentWeight, double testWeight, double examWeight) {
        setWeights(assignmentWeight, testWeight, examWeight);
    }

    public void setWeights(double a, double t, double e) {
        double sum = a + t + e;
        if (Math.abs(sum - 1.0) > 1e-9) {
            throw new IllegalArgumentException("Weights must sum to 1.0 (100%).");
        }
        if (a < 0 || t < 0 || e < 0) {
            throw new IllegalArgumentException("Weights cannot be negative.");
        }
        this.assignmentWeight = a;
        this.testWeight = t;
        this.examWeight = e;
    }

    public double weightedScore(double assignment, double test, double exam) {
        return assignment * assignmentWeight + test * testWeight + exam * examWeight;
    }

    public String letterFor(double finalScore) {
        if (finalScore >= 90) return "A";
        if (finalScore >= 80) return "B";
        if (finalScore >= 70) return "C";
        if (finalScore >= 60) return "D";
        return "F";
    }

    public double getAssignmentWeight() { return assignmentWeight; }
    public double getTestWeight() { return testWeight; }
    public double getExamWeight() { return examWeight; }
}
