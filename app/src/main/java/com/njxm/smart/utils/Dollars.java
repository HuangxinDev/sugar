package com.njxm.smart.utils;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class Dollars {
    private int height;
    private int width;

    public Dollars(double result) {

    }

    protected Dollars baseCharge() {
        double result = usageInRange(0, 100) * 0.03;
        result += usageInRange(100, 200) * 0.05;
        result += usageInRange(200, Integer.MAX_VALUE) * 0.07;
        return new Dollars(result);
    }

    public int usageInRange(int start, int end) {
        if (lastUsage() > start) {
            return Math.min(lastUsage(), end) - start;
        } else {
            return 0;
        }
    }

    private int lastUsage() {
        return 0;
    }

    void setValue(String name, int value) {
        if ("height".equals(name)) {
            setHeight(value);
            return;
        }
        if ("width".equals(name)) {
            setWidth(value);
            return;
        }
    }

    private void setWidth(int value) {
        width = value;
    }

    private void setHeight(int value) {
        height = value;
    }
}
