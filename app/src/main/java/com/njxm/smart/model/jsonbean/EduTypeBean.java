/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.model.jsonbean;

import com.alibaba.fastjson.annotation.JSONField;

public class EduTypeBean {

    @JSONField(name = "sdCode")
    private String code;

    @JSONField(name = "simpleName")
    private String name;

    @JSONField(name = "sdValue")
    private String value;

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "sdName")
    private String sdName;

    private boolean selected = false;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSdName() {
        return sdName;
    }

    public void setSdName(String sdName) {
        this.sdName = sdName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
