/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

/**
 * Created by Hxin on 2021/4/7 Function:
 */
public class PhoneNews implements News {

    String phoneNumber;

    public PhoneNews(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getSecretNews() {
        StringBuilder phone = new StringBuilder(this.phoneNumber);
        return phone.replace(3, 7, "****").toString();
    }
}
