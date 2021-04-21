/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.model.jsonbean;

public class CertificateParentBean {

    private String modifyUser;

    private String sctParentId;

    private String modifyTime;

    private String sctDescription;

    private String createTime;

    private boolean childExit;

    private int createUser;

    private String id;

    private int delFlag;

    private String sctIcon;

    private String sctName;

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getSctParentId() {
        return sctParentId;
    }

    public void setSctParentId(String sctParentId) {
        this.sctParentId = sctParentId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSctDescription() {
        return sctDescription;
    }

    public void setSctDescription(String sctDescription) {
        this.sctDescription = sctDescription;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isChildExit() {
        return childExit;
    }

    public void setChildExit(boolean childExit) {
        this.childExit = childExit;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getSctIcon() {
        return sctIcon;
    }

    public void setSctIcon(String sctIcon) {
        this.sctIcon = sctIcon;
    }

    public String getSctName() {
        return sctName;
    }

    public void setSctName(String sctName) {
        this.sctName = sctName;
    }
}
