package com.njxm.smart.model.jsonbean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 省份Bean
 */
public class ProvinceBean extends BaseAddressBean {

    @JSONField(name = "code")
    private String provinceId;

    @JSONField(name = "name")
    private String provinceName;

    @JSONField(name = "pinyin")
    private String pinyin;

    @JSONField(name = "children")
    private List<CityBean> cities;


    public String getProvinceId() {
        return provinceId;
    }

    public ProvinceBean() {
        type = 0;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
        setBaseCode(provinceId);
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        setBaseName(provinceName);
        this.provinceName = provinceName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public List<CityBean> getCities() {
        return cities;
    }

    public void setCities(List<CityBean> cities) {
        this.cities = cities;
        setBaseAddressBeans(cities);
    }
}
