package com.njxm.smart.http;

import java.util.Hashtable;

/**
 * 网络域名配置
 *
 * @author huangxin
 * @date 2022/2/27
 */
public class GrsConfig {
    private static final String H5_SERVER_DOMAIN = "H5_SERVER_DOMAIN";

    private static final String BIZ_SERVER_DOMAIN = "BIZ_SERVER_DOMAIN";

    private static final String VERIFY_SERVER_DOMAIN = "VERIFY_SERVER_DOMAIN";

    static Hashtable<String, String> urlDomain = new Hashtable<>();

    static {
        urlDomain.put(H5_SERVER_DOMAIN, "");
        urlDomain.put(BIZ_SERVER_DOMAIN, "");
        urlDomain.put(VERIFY_SERVER_DOMAIN, "");
    }

    private static void setH5ServerDomain() {

    }

    public static String getH5ServerDomain() {
        return urlDomain.get(H5_SERVER_DOMAIN);
    }

    public static String getBizServerDomain() {
        return urlDomain.get(BIZ_SERVER_DOMAIN);
    }

    public static String getVerifyServerDomain() {
        return urlDomain.get(VERIFY_SERVER_DOMAIN);
    }
}
