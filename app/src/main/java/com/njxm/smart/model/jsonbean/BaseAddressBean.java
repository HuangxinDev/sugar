package com.njxm.smart.model.jsonbean;

import java.util.List;

public class BaseAddressBean {
    /**
     * 0: 省份
     * 1:城市
     * 2:地区
     */
    protected int type;

    private String baseName;

    private String baseCode;

    private List<? extends BaseAddressBean> baseAddressBeans;

    public List<? extends BaseAddressBean> getBaseAddressBeans() {
        return baseAddressBeans;
    }

    public void setBaseAddressBeans(List<? extends BaseAddressBean> baseAddressBeans) {
        this.baseAddressBeans = baseAddressBeans;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String name) {
        this.baseName = name;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String code) {
        this.baseCode = code;
    }
}
