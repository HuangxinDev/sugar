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
