package com.njxm.smart.model.component;

import com.njxm.smart.utils.TimeUtils;

public class SuggestionDetailItem extends BaseItem {
    private String headPath;
    private String name;
    private String work;
    private String message;
    private String time;

    public SuggestionDetailItem(String name, String work, String message) {
        this.headPath = headPath;
        this.name = name;
        this.work = work;
        this.message = message;
        this.time = TimeUtils.formatTime(TimeUtils.FORMAT_TIME_1, System.currentTimeMillis());
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
