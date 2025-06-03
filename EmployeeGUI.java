package com.employeemanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeGUI {
    private JFrame frame;
    private JTextField idField, nameField, departmentField, payField, bonusField, searchField, deleteField;
    private JCheckBox isManagerCheckbox;
    private JTextArea outputArea;
    private EmployeeManager manager;

    public EmployeeGUI() {
        manager = new EmployeeManager();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Employee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));

        inputPanel.add(new JLabel("Employee ID:"));
        idField = new JTextField(15);
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Department:"));
        departmentField = new JTextField(15);
        inputPanel.add(departmentField);

        inputPanel.add(new JLabel("Salary:"));
        payField = new JTextField(15);
        inputPanel.add(payField);

        inputPanel.add(new JLabel("Bonus (Managers only):"));
        bonusField = new JTextField(15);
        bonusField.setVisible(false); // Hide by default
        inputPanel.add(bonusField);

        inputPanel.add(new JLabel("Is Manager:"));
        isManagerCheckbox = new JCheckBox();
        inputPanel.add(isManagerCheckbox);

        // Show/hide bonus field when checkbox is toggled
        isManagerCheckbox.addItemListener(e -> {
            boolean selected = isManagerCheckbox.isSelected();
            bonusField.setVisible(selected);
            frame.revalidate();
            frame.repaint();
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        JButton addButton = new JButton("Add Employee");
        JButton viewButton = new JButton("View All");
        JButton sortButton = new JButton("Sort by Name");
        JButton searchButton = new JButton("Search by ID");
        JButton deleteButton = new JButton("Delete by ID");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);

        JPanel searchDeletePanel = new JPanel(new GridLayout(2, 2, 5, 5));
        searchDeletePanel.add(new JLabel("Search ID:"));
        searchField = new JTextField(10);
        searchDeletePanel.add(searchField);
        searchDeletePanel.add(new JLabel("Delete ID:"));
        deleteField = new JTextField(10);
        searchDeletePanel.add(deleteField);

        outputArea = new JTextArea(12, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Action listeners
        addButton.addActionListener(e -> addEmployee());
        viewButton.addActionListener(e -> viewEmployees());
        sortButton.addActionListener(e -> sortEmployees());
        searchButton.addActionListener(e -> searchEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());

        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(searchDeletePanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void addEmployee() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String dept = departmentField.getText().trim();
        String salaryText = payField.getText().trim();
        boolean isManager = isManagerCheckbox.isSelected();

        if (id.isEmpty() || name.isEmpty() || dept.isEmpty() || salaryText.isEmpty() ||
                (isManager && bonusField.getText().trim().isEmpty())) {
            outputArea.setText("Error: Please fill all fields!");
            return;
        }

        if (!manager.canAddEmployee(id)) {
            idField.setBackground(Color.PINK);
            outputArea.setText("Error: ID " + id + " already exists!\n" +
                    "Delete the existing employee first to reuse this ID.");
            return;
        }

        try {
            double salary = Double.parseDouble(salaryText);
            Employee emp;
            if (isManager) {
                double bonus = Double.parseDouble(bonusField.getText().trim());
                emp = new Manager(id, name, dept, salary, bonus);
            } else {
                emp = new RegularEmployee(id, name, dept, salary);
            }
            manager.addEmployee(emp);
            outputArea.setText("Added successfully:\n" + emp);
            clearFormFields();
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid salary/bonus format! Use numbers only.");
            payField.setBackground(Color.PINK);
            bonusField.setBackground(Color.PINK);
        }
    }

    private void viewEmployees() {
        List<Employee> employees = manager.getEmployees();
        if (employees.isEmpty()) {
            outputArea.setText("No employees found.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Employee emp : employees) {
                sb.append(emp).append("\n");
            }
            outputArea.setText(sb.toString());
        }
    }

    private void sortEmployees() {
        manager.sortEmployeesByName();
        outputArea.setText("Employees sorted by name.");
    }

    private void searchEmployee() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            outputArea.setText("Enter an ID to search.");
            return;
        }
        Employee emp = manager.searchEmployeeById(id);
        if (emp != null) {
            outputArea.setText("Found:\n" + emp);
        } else {
            outputArea.setText("No employee found with ID: " + id);
        }
    }

    private void deleteEmployee() {
        String id = deleteField.getText().trim();
        if (id.isEmpty()) {
            outputArea.setText("Enter an ID to delete.");
            return;
        }
        boolean removed = manager.deleteEmployee(id);
        if (removed) {
            outputArea.setText("Employee with ID " + id + " deleted.");
        } else {
            outputArea.setText("No employee found with ID: " + id);
        }
    }

    private void clearFormFields() {
        idField.setText("");
        nameField.setText("");
        departmentField.setText("");
        payField.setText("");
        bonusField.setText("");
        isManagerCheckbox.setSelected(false);
        searchField.setText("");
        deleteField.setText("");
        idField.setBackground(Color.WHITE);
        payField.setBackground(Color.WHITE);
        bonusField.setBackground(Color.WHITE);
    }
}
