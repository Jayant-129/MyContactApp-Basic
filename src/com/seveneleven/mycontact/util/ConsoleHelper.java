// Utility wrapper around Scanner providing clean console input methods
package com.seveneleven.mycontact.util;

import java.util.Scanner;

public class ConsoleHelper {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    public static boolean confirm(String prompt) {
        String input = readLine(prompt + " (y/n): ");
        return input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes");
    }

    public static void printDivider() {
        System.out.println("--------------------------------------------------");
    }

    public static void printHeader(String title) {
        System.out.println("\n==================================================");
        System.out.println("  " + title);
        System.out.println("==================================================");
    }
}
