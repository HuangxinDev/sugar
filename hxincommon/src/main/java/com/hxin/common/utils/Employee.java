package com.hxin.common.utils;

/**
 * @author huangxin
 * @date 2021/8/14
 */
public class Employee extends Party implements IEmployee, Billable {
    private EmployeeType employeeType;

    private int type;
    private int commission;
    private int bonus;

    private int monthlySalary;

    public Employee(String name, String id, int annualCost) {
        super(name);

    }

    Employee(int type) {
        this("", "", 0);
        this.employeeType = EmployeeType.newType(type);
    }

    public int getType() {
        return employeeType.getTypeCode();
    }

    public int getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(int monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public void setType(int type) {
        this.employeeType = EmployeeType.newType(type);
    }

    public static void main(String[] args) {
        Employee employee = new Employee(EmployeeType.ENGINEER);
        employee.setType(EmployeeType.ENGINEER);
        employee.employeeType.payAmount(employee);
    }

    @Override
    public int payAmount() {
        return employeeType.payAmount(this);
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    @Override
    public boolean hasSpecialSkill() {
        return false;
    }

    public Department getDepartment() {
        return null;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public int getRate() {
        return 0;
    }

    public double charge(Billable emp, int days) {
        int base = emp.getRate() * days;
        if (emp.hasSpecialSkill()) {
            base *= 1.05;
        }
        return base;
    }


    public int calAngle(int srcAngle) {
        int abc = Math.abs(srcAngle);
        if (abc < 85) {

        } else {
            abc = 360 - abc;
        }
        return 0;
    }
}
