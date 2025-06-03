package com.employeemanagement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeManager {

    private ArrayList<Employee> employees = new ArrayList<>();
    private final String DATA_FILE = "src/com/employeemanagement/employee_data.csv";

    public EmployeeManager() {
        loadEmployeesFromFile();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        saveEmployeesToFile();
    }

    public void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        employees.forEach(System.out::println);
    }

    // Delete by ID
    public boolean deleteEmployee(String id) {
        boolean removed = employees.removeIf(emp -> emp.getId().equals(id));
        if (removed) {
            saveEmployeesToFile();
        }
        return removed;
    }

    // Optional: Delete by Name
    public boolean deleteEmployeeByName(String name) {
        boolean removed = employees.removeIf(emp -> emp.getName().equalsIgnoreCase(name));
        if (removed) {
            saveEmployeesToFile();
        }
        return removed;
    }

    public Employee searchEmployeeById(String id) {
        return employees.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> searchEmployeesByName(String name) {
        return employees.stream()
                .filter(emp -> emp.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    private void loadEmployeesFromFile() {
        employees.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String department = parts[2].trim();
                    String role = parts[3].trim();
                    double salary = Double.parseDouble(parts[4].trim());
                    if (role.equalsIgnoreCase("Manager")) {
                        double bonus = 0;
                        if (parts.length >= 6 && !parts[5].trim().isEmpty()) {
                            bonus = Double.parseDouble(parts[5].trim());
                        }
                        employees.add(new Manager(id, name, department, salary, bonus));
                    } else if (role.equalsIgnoreCase("Regular Employee")) {
                        employees.add(new RegularEmployee(id, name, department, salary));
                    }
                }
            }
            System.out.println("Employee data loaded from " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("Error loading employee data from " + DATA_FILE + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing pay from " + DATA_FILE + ": " + e.getMessage());
        }
    }

    private void saveEmployeesToFile() {
        File csvFile = new File(DATA_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("ID,Name,Department,Role,Salary,Bonus");
            writer.newLine();
            for (Employee emp : employees) {
                if (emp instanceof Manager) {
                    Manager m = (Manager) emp;
                    writer.write(String.join(",",
                            m.getId(),
                            m.getName(),
                            m.getDepartment(),
                            "Manager",
                            String.valueOf(m.gateSalary()),
                            String.valueOf(m.getBonus())));
                } else if (emp instanceof RegularEmployee) {
                    RegularEmployee re = (RegularEmployee) emp;
                    writer.write(String.join(",",
                            re.getId(),
                            re.getName(),
                            re.getDepartment(),
                            "Regular Employee",
                            String.valueOf(re.gateSalary()),
                            "")); // blank bonus
                }
                writer.newLine();
            }
            System.out.println("✅ Employee data saved to " + csvFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Error saving employee data to CSV: " + e.getMessage());
        }
    }

    public String getAllEmployees() {
        StringBuilder sb = new StringBuilder();
        for (Employee e : employees) {
            sb.append(e.toString()).append("\n");
        }
        return sb.toString();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void sortEmployeesByName() {
        employees.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
    }

    public List<Employee> searchByName(String query) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getName().toLowerCase().contains(query.toLowerCase())) {
                result.add(e);
            }
        }
        return result;
    }

    public boolean canAddEmployee(String id) {
        return employees.stream().noneMatch(emp -> emp.getId().equals(id));
    }

    // Not used in current logic
    public boolean addEmployee(String id) {
        return false;
    }
}
