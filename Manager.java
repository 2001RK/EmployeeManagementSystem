package com.employeemanagement;

public class Manager extends Employee {
    private double salary;
    private double bonus;

    public Manager(String id, String name, String dept, double salary, double bonus) {
        super(id, name, dept);
        this.salary = salary;
        this.bonus = bonus;
    }

    public double getSalaary() {
        return salary;
    }
    
    public double getBonus() {
        return bonus;
    }
    @Override
    public String toString() {
        return String.format("Manager [ID=%s, Name=%s, Dept=%s, Salary=%.2f, Bonus=%.2f, Total Pay=%.2f]",
                getId(), getName(), getDepartment(), salary, bonus, calculatePay());
    }

	@Override
	public double calculatePay() {
		// TODO Auto-generated method stub
		return 0;
	}
}
