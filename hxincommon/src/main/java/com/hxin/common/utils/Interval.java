package com.hxin.common.utils;

import java.util.List;
import java.util.Observable;

/**
 * @author huangxin
 * @date 2021/8/14
 */
public class Interval extends Observable {
    private Interval subject;

    private String end;

    private String[] skills;

    private static final int A = 317;
    private static final int B = 3;
    private static final int C = 478;
    private static final int AB = 635;

    private int bloodGroup;

    public Interval(int bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(int bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    List<String> skillss;

    String[] getSkills() {
        return skillss.toArray(new String[0]);
    }

    public Interval getSubject() {
        return subject;
    }

    public void setSubject(Interval subject) {
        this.subject = subject;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
        setChanged();
        notifyObservers();
    }
}
