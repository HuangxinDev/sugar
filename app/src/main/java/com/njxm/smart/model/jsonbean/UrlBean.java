package com.njxm.smart.model.jsonbean;

import com.alibaba.fastjson.annotation.JSONField;

public class UrlBean {
    @JSONField(name = "key")
    public String name;

    public String url;

    public UrlBean() {
    }

    public UrlBean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
