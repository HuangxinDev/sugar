package com.hxin.common.utils;

/**
 * @author huangxin
 * @date 2021/8/14
 */
public class Course {
    private String name;

    private boolean isAdvanced;

    public Course(String name, boolean isAdvanced) {
        this.name = name;
        this.isAdvanced = isAdvanced;
    }

    public boolean isAdvanced() {
        return this.isAdvanced;
    }

}
