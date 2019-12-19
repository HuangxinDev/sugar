package com.njxm.smart.model.jsonbean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class AddressBean {

    @JSONField(name = "code")
    private String id;  // 地区ID

    @JSONField(name = "name")
    private String name; // 地区名称

    @JSONField(name = "pinyin")
    private String pinyin; //地区拼音

    @JSONField(name = "children")
    private List<AddressBean> areas; // 下级地区

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public List<AddressBean> getAreas() {
        return areas;
    }

    public void setAreas(List<AddressBean> areas) {
        this.areas = areas;
    }
}
