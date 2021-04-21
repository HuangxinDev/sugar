/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

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
