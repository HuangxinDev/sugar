package com.njxm.smart.global;

public final class HttpUrlGlobal {

    ///////////////////////////////////////////////////////////////////////////
    // HTTP URL
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 图形验证码接口
     */
    public static final String HTTP_QR_URL = "http://119.3.136.127:7777/auth/kaptcha/get";

    /**
     * 密码登录接口
     */
    public static final String HTTP_USER_LOGIN_URL = "http://119.3.136.127:7777/auth/user/login";

    /**
     * 手机号登录接口
     */
    public static final String HTTP_MOBILE_LOGIN_URL = "http://119.3.136.127:7777/auth/mobile/login";

    /**
     * 短信请求接口
     */
    public static final String HTTP_SMS_URL = "http://119.3.136.127:7777/auth/sms/sendSms";

    /**
     * 重置密码接口
     */
    public static final String HTTP_RESET_PWD_URL = "http://119.3.136.127:7776/sys/user/updatePassByMobile";

    /**
     * 关于我们页面
     */
    public static final String HTTP_ABOUT_US = "http://119.3.136.127:7776/sys/link/aboutUsIndex";


    ///////////////////////////////////////////////////////////////////////////
    // ContentType类型
    ///////////////////////////////////////////////////////////////////////////
    public static final String CONTENT_JSON_TYPE = "application/json";

    public static final String CONTENT_TEXT_TYPE = "application/x-www-form-urlencoded";
}
