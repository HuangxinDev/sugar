package com.njxm.smart.model.jsonbean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 区域Bean
 */
public class AreaBean extends BaseAddressBean {

    @JSONField(name = "code")
    private String areaId;

    @JSONField(name = "name")
    private String areaName;

    @JSONField(name = "pinyin")
    private String pinyin;

    public String getAreaId() {
        return areaId;
    }

    public AreaBean() {
        type = 2;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
        setBaseCode(areaId);
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
        setBaseName(areaName);
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
