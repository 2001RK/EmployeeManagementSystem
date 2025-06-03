package com.employeemanagement;

public class RegularEmployee extends Employee {
    private double salary;

    public RegularEmployee(String id, String name, String dept, double salary) {
        super(id, name, dept);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public double calculatePay() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("Regular Employee [ID=%s, Name=%s, Dept=%s, Salary=%.2f]",
                getId(), getName(), getDepartment(), salary);
    }
}
