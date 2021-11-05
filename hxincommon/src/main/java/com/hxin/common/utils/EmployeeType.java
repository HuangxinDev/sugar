package com.hxin.common.utils;

/**
 * @author huangxin
 * @date 2021/8/14
 */
public abstract class EmployeeType {
    static final int ENGINEER = 988;
    static final int SALESMAN = 350;
    static final int MANAGER = 192;

    abstract int getTypeCode();

    public static EmployeeType newType(int code) {
        switch (code) {
            case ENGINEER:
                return new Engineer();
            case SALESMAN:
                return new Salesman();
            case MANAGER:
                return new Manager();
            default:
                throw new IllegalArgumentException("Incorrect Employee Code");

        }
    }

    public int payAmount(Employee employee) {
        switch (employee.getType()) {
            case ENGINEER:
                return employee.getMonthlySalary();
            case SALESMAN:
                return employee.getMonthlySalary() + employee.getCommission();
            case MANAGER:
                return employee.getMonthlySalary() + employee.getBonus();
            default:
                throw new RuntimeException("Incorrect Employee.");
        }
    }

}
