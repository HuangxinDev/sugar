/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

/**
 * Created by Hxin on 2021/4/21 Function:
 */
class ContactsBean {
    private String name;
    private String headUrl;
    private String job;
    private boolean superWorker;
    private boolean selected = false;

    public ContactsBean(String name, String headUrl, String job, boolean superWorker) {
        this.name = name;
        this.headUrl = headUrl;
        this.job = job;
        this.superWorker = superWorker;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return this.headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public boolean isSuperWorker() {
        return this.superWorker;
    }

    public void setSuperWorker(boolean superWorker) {
        this.superWorker = superWorker;
    }
}
