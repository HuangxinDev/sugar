package com.njxm.smart;

/**
 * 测试环境全局变量
 */
public class GlobalConst {
    private static final String HTTP_PROTOCOL = "http://";


    public static final String PRD_IP = "192.168.3.112";

    /**
     * H5 端口号
     */
    public static final String H5_PORT = "8080";

    /**
     * 业务端口号
     */
    public static final String BIZ_PORT = "8083";

    /**
     * 校验端口号
     */
    public static final String GRANT_PORT = "8081";


    public static final String URL_GRANT_PREFIX = HTTP_PROTOCOL + PRD_IP + ";" + GRANT_PORT;
    public static final String URL_BIZ_PREFIX = HTTP_PROTOCOL + PRD_IP + ":" + BIZ_PORT;
    public static final String URL_H5_PREFIX = HTTP_PROTOCOL + PRD_IP + ":" + H5_PORT;
}
