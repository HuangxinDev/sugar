package com.hxin.common.utils;

/**
 * @author huangxin
 * @date 2021/8/14
 */
public class BloodGroup {
    public static final BloodGroup A = createBloodGroup(0);
    public static final BloodGroup B = createBloodGroup(1);
    public static final BloodGroup AB = createBloodGroup(2);

    private static final BloodGroup[] values = {A, B, AB};

    private int code;

    private BloodGroup(int code) {
        this.code = code;
    }

    private static BloodGroup createBloodGroup(int code) {
        return new BloodGroup(code);
    }

    private int getCode() {
        return code;
    }

    private static BloodGroup code(int arg) {
        return values[arg];
    }
}
