package com.employeemanagement;

import java.util.List;
import java.util.Scanner;

public class EmployeeTBI {
    private EmployeeManager manager;
    private Scanner scanner;

    public EmployeeTBI() {
        this.manager = new EmployeeManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Sort by Name");
            System.out.println("4. Search by ID");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewEmployees();
                case 3 -> sortEmployees();
                case 4 -> searchEmployee();
                case 5 -> System.out.println("Exiting TBI...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }

    private void addEmployee() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();
        System.out.print("Is Manager (yes/no): ");
        String managerInput = scanner.nextLine();

        try {
            System.out.print("Enter Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());
            Employee emp;
            if (managerInput.equalsIgnoreCase("yes")) {
                System.out.print("Enter Bonus: ");
                double bonus = Double.parseDouble(scanner.nextLine());
                emp = new Manager(id, name, dept, salary, bonus);
            } else {
                emp = new RegularEmployee(id, name, dept, salary);
            }
            manager.addEmployee(emp);
            System.out.println("Employee added.");
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number format for salary/bonus! Employee not added.");
        }
    }


    private void viewEmployees() {
        List<Employee> employees = manager.getEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            employees.forEach(System.out::println);
        }
    }

    private void sortEmployees() {
        manager.sortEmployeesByName();
        System.out.println("Employees sorted by name.");
    }

    private void searchEmployee() {
        System.out.print("Enter ID to search: ");
        String id = scanner.nextLine();
        Employee emp = manager.searchEmployeeById(id);
        if (emp != null) {
            System.out.println("Found: " + emp);
        } else {
            System.out.println("No employee found.");
        }
    }
}
