package com.njxm.smart;

/**
 * 发布环境全局变量
 */
public class GlobalConst {

    private static final String HTTP_PROTOCOL = "http://";


    public static final String PRD_IP = "119.3.136.127";

    /**
     * H5 端口号
     */
    public static final String H5_PORT = "7775";

    /**
     * 业务端口号
     */
    public static final String BIZ_PORT = "7776";

    /**
     * 校验端口号
     */
    public static final String GRANT_PORT = "7777";


    public static final String URL_GRANT_PREFIX = HTTP_PROTOCOL + PRD_IP + ":" + GRANT_PORT;
    public static final String URL_BIZ_PREFIX = HTTP_PROTOCOL + PRD_IP + ":" + BIZ_PORT;
    public static final String URL_H5_PREFIX = HTTP_PROTOCOL + PRD_IP + ":" + H5_PORT;
}