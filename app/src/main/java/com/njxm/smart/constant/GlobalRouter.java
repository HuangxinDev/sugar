/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.constant;

import java.util.HashMap;

/**
 * 全局路由地址
 */
public class GlobalRouter {

    /**
     * 用户手机页面
     */
    public static final String USER_PHONE = "/user/phone";
    /**
     * 用户
     */
    public static final String USER_ADDRESS = "/user/address";
    /**
     * 录入人脸
     */
    public static final String USER_INPUT_FACE = "/user/inputface";
    /**
     * 用户学历页面
     */
    public static final String USER_CETIFICATION = "/user/education";
    /**
     * 用户紧急联系人页面
     */
    public static final String USER_EMERGENCY_CONTACT = "/user/emergency/contact";
    private static final HashMap<String, String> ROUTER_MAP = new HashMap<>();

    static {
        com.njxm.smart.constant.GlobalRouter.ROUTER_MAP.put("app:safety:inspect", "");
    }
}
