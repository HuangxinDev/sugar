/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.model.jsonbean;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

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
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public List<AddressBean> getAreas() {
        return this.areas;
    }

    public void setAreas(List<AddressBean> areas) {
        this.areas = areas;
    }
}
