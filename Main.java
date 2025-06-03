package com.employeemanagement;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== Employee Management System =====");
        System.out.println("Select Interface Mode:");
        System.out.println("1. Graphical User Interface (GUI)");
        System.out.println("2. Text-Based Interface (TBI)");
        System.out.print("Enter your choice (1 or 2): ");
        int mode = scanner.nextInt();

        if (mode == 1) {
            // Launch GUI
            javax.swing.SwingUtilities.invokeLater(() -> {
                new EmployeeGUI();
            });
        } else if (mode == 2) {
            // Launch TBI
            EmployeeTBI tbi = new EmployeeTBI();
            tbi.start();
        } else {
            System.out.println("Invalid selection. Please restart and choose 1 or 2.");
        }

        scanner.close();
    }
}
