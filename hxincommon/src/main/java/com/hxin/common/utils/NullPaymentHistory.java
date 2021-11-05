package com.hxin.common.utils;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class NullPaymentHistory extends PaymentHistory {

    @Override
    public int getWeeksDelinquentInLastYear() {
        return 0;
    }

    public boolean isNull() {
        return true;
    }
}
