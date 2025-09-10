/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banking;

/**
 *
 * @author Paledi Sebene
 */
public class ConsoleUtils {
    // ANSI escape codes for colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";

    public static void printError(String msg) {
        System.out.println(RED + msg + RESET);
    }

    public static void printSuccess(String msg) {
        System.out.println(GREEN + msg + RESET);
    }

    public static void printInfo(String msg) {
        System.out.println(CYAN + msg + RESET);
    }

    public static void printWarning(String msg) {
        System.out.println(YELLOW + msg + RESET);
    }

    // Simple loading animation
    public static void loading(String msg) {
        System.out.print(CYAN + msg + " ");
        for (int i = 0; i < 3; i++) {
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            System.out.print(".");
        }
        System.out.println(RESET);
    }
}
