package com.hxin.common.utils;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class Customer {
    private String name;
    private BillingPlan plan;
    private PaymentHistory history;

    public String getName() {
        return name;
    }

    public BillingPlan getPlan() {
        return plan == null ? BillingPlan.newNull() : plan;
    }

    public PaymentHistory getHistory() {
        return history == null ? PaymentHistory.newNull() : history;
    }

    public void setPlan(BillingPlan plan) {
        this.plan = plan;
    }

    public void setHistory(PaymentHistory history) {
        this.history = history;
    }

    static Customer newNUll() {
        return new NullCustomer();
    }
}
