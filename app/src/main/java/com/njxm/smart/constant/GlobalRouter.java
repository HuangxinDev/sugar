package com.njxm.smart.constant;

import java.util.HashMap;

/**
 * 全局路由地址
 */
public class GlobalRouter {

    private static final HashMap<String, String> ROUTER_MAP = new HashMap<>();

    static {
        ROUTER_MAP.put("app:safety:inspect", "");
    }

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
     *  用户学历页面
     */
    public static final String USER_CETIFICATION = "/user/education";

    /**
     * 用户紧急联系人页面
     */
    public static final String USER_EMERGENCY_CONTACT = "/user/emergency/contact";
}