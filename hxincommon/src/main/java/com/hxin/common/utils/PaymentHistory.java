package com.hxin.common.utils;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class PaymentHistory {
    public int getWeeksDelinquentInLastYear() {
        return 0;
    }

    public boolean isNull() {
        return false;
    }

    static PaymentHistory newNull() {
        return new NullPaymentHistory();
    }
}
