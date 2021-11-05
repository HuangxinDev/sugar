package com.hxin.common.utils;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class Engineer2 extends Employee {
    Engineer2(int type) {
        super(type);
    }

    public int payAmount(Employee employee) {
        return employee.getMonthlySalary();
    }
}
