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
