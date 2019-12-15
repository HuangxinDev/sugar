package com.njxm.smart.model.jsonbean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class CityBean extends BaseAddressBean {

    @JSONField(name = "code")
    private String cityId;

    @JSONField(name = "name")
    private String cityName;

    @JSONField(name = "pinyin")
    private String pinyin;

    @JSONField(name = "children")
    private List<AreaBean> areas;

    public CityBean() {
        type = 1;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
        setBaseCode(cityId);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
        setBaseName(cityName);
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public List<AreaBean> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaBean> areas) {
        this.areas = areas;
        setBaseAddressBeans(areas);
    }
}
