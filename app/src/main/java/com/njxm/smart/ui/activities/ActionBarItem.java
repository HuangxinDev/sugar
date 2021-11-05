package com.njxm.smart.ui.activities;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class ActionBarItem {
    private int leftRes;
    private int rightRes;
    private String title;

    public ActionBarItem(int leftRes, int rightRes, String title) {
        this.leftRes = leftRes;
        this.rightRes = rightRes;
        this.title = title;
    }

    public int getLeftRes() {
        return leftRes;
    }

    public int getRightRes() {
        return rightRes;
    }

    public String getTitle() {
        return title;
    }
}
