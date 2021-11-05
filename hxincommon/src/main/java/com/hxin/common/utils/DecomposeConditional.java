package com.hxin.common.utils;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class DecomposeConditional {
    private static final Date SUMMER_START = new Date();
    private Date date;
    private Date SUMMER_END;

    private double quantity;
    private double winterRate;
    private double winterServiceCharge;
    private double summerRate;
    private int seniority;
    private int monthsDisabled;
    private boolean isPartTime;
    private double price;

    public void test() {
        double charge;
        if (notSummer(date)) {
            charge = winterCharge(quantity);
        } else {
            charge = summerCharge(quantity);
        }


        charge = price * getRate();
        send();
    }

    private double getRate() {
        return isSpecialDeal() ? 0.95 : 0.98;
    }

    private void send() {
    }

    private boolean isSpecialDeal() {
        return false;
    }

    private double winterCharge(double quantity) {
        return quantity * winterRate + winterServiceCharge;
    }

    private double summerCharge(double quantity) {
        return quantity * summerRate;
    }

    private boolean notSummer(Date date) {
        return date.before(SUMMER_START) || this.date.after(SUMMER_END);
    }

    double disabilityAmount() {
        return isNotEligibleForDisability() ? 0 : -1;
    }

    @Nullable
    private boolean isNotEligibleForDisability() {
        return seniority < 2 || monthsDisabled > 12 || isPartTime;
    }

    public String checkSecurity(String[] people) {
        List<String> conditions = Arrays.asList("Don", "John");
        for (String person : people) {
            if (conditions.contains(person)) {
                sendAlert();
                return person;
            }
        }
        return "";

    }

    private void sendAlert() {

    }


}
