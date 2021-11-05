package com.hxin.common.utils;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class Site {
    private Customer customer;

    Customer getCustomer() {
        return customer == null ? Customer.newNUll() : customer;
    }

    public static void main(String[] args) {
        Site site = new Site();
        Customer customer = site.getCustomer();
        BillingPlan billingPlan = customer.getPlan();
        customer.setPlan(BillingPlan.special());
        String customerName = customer.getName();
        int weekDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
    }
}
