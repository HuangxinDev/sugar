package com.smart.cloud.utils;

import java.util.Date;

/**
 * @author huangxin
 * @date 2021/8/13
 */
public class MFDateWrap {
    private Date original;

    public MFDateWrap(Date original) {
        this.original = original;
    }

    public Date nextDay() {
        return new Date(original.getYear(), original.getMonth(), original.getDate() + 1);
    }

    public boolean after(Date arg) {
        return arg.after(original);
    }

    public boolean equalsDate(MFDateWrap wrap) {
        return this == wrap;
    }

    public boolean equalsDate(Date object) {
        return original == object;
    }
}
