package com.hxin.common.utils;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class BillingPlan {
    public static BillingPlan newNull() {
        return new NullBillingPlan();
    }

    public static BillingPlan basic() {
        return null;
    }

    public static BillingPlan special() {
        return null;
    }
}
