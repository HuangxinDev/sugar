/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.model.jsonbean;

/**
 * 巡检记录Bean
 */
public class SafeInspectRecordBean {

    private boolean isExistProblem;

    private String inspectTime;

    public SafeInspectRecordBean(boolean isExistProblem, String inspectTime) {
        this.isExistProblem = isExistProblem;
        this.inspectTime = inspectTime;
    }

    public boolean isExistProblem() {
        return isExistProblem;
    }

    public void setExistProblem(boolean existProblem) {
        isExistProblem = existProblem;
    }

    public String getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(String inspectTime) {
        this.inspectTime = inspectTime;
    }
}
