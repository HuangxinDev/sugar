package com.njxm.smart.model.jsonbean;

/**
 * 日常巡检数据Bean
 */
public class DailyCheckTaskBean {

    private String taskName;

    private String taskContent;

    /**
     * 0: 未开始 1:开始 2: 完成
     */
    private int taskState;

    private String createTime;

    public DailyCheckTaskBean(String taskName, String taskContent, int taskState, String createTime) {
        this.taskName = taskName;
        this.taskContent = taskContent;
        this.taskState = taskState;
        this.createTime = createTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
