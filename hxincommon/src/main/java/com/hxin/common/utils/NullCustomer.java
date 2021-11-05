package com.hxin.common.utils;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class NullCustomer extends Customer {

    @Override
    public String getName() {
        return "occupant";
    }

    @Override
    public PaymentHistory getHistory() {
        return PaymentHistory.newNull();
    }

    @Override
    public void setPlan(BillingPlan plan) {
    }

    public boolean isNull() {
        return true;
    }
}
