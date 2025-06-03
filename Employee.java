package com.employeemanagement;

public abstract class Employee {
    private String id;
    private String name;
    private String department;

    public Employee(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public abstract double calculatePay();

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Department: " + department;
    }

	public double gateSalary() {
		// TODO Auto-generated method stub
		return 0;
	}
}