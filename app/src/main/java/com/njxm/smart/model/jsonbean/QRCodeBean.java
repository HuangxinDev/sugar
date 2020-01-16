package com.njxm.smart.model.jsonbean;

import com.alibaba.fastjson.annotation.JSONField;

public class QRCodeBean {

    @JSONField(name = "kaptcha")
    private String image;

    @JSONField(name = "kaptchaToken")
    private String imageToken;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageToken() {
        return imageToken;
    }

    public void setImageToken(String imageToken) {
        this.imageToken = imageToken;
    }
}
