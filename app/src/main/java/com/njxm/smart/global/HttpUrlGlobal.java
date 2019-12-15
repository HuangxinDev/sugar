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

    /**
     * 设置页面-登出
     */
    public static final String HTTP_MY_SETTING_LOGOUT = "http://119.3.136.127:7777/auth/user/logout";


    /**
     * 体检报告状态检查接口
     */
    public static final String HTTP_MEDICAL_STATE = "http://119.3.136.127:7776/api/sys/userMedical/updateStatus";

    /**
     * 体检报告提交或者更新接口
     */
    public static final String HTTP_MEDICAL_COMMIT_UPDATE = "http://119.3.136.127:7776/api/sys/userMedical/insert";

    /**
     * 体检报告获取已经上传的文件
     */
    public static final String HTTP_MEDICAL_GET_IMAGE = "http://119.3.136.127:7776/api/sys/userMedical/findByUserId";

    /**
     * 个人信息-初始化页面
     */
    public static final String HTTP_MY_USER_INIT_NEWS = "http://119.3.136.127:7776/api/sys/user/findUserForIndex";

    /**
     * 个人信息-信息详情
     */
    public static final String HTTP_MY_USER_DETAIL_NEWS = "http://119.3.136.127:7776/api/sys/user/findById";

    /**
     * 个人信息-修改头像
     */
    public static final String HTTP_MY_USER_HEAD = "http://119.3.136.127:7776/api/sys/user/updateIcon";

    /**
     * 个人信息-录入人脸
     */
    public static final String HTTP_MY_USER_INPUT_FACE = "http://119.3.136.127:7776/api/sys/user/updateFace";

    /**
     * 个人信息-修改学历
     */
    public static final String HTTP_MY_USER_EDUCATION = "http://119.3.136.127:7776/api/sys/user/updateEducation";

    /**
     * 个人信息-紧急联系人
     */
    public static final String HTTP_MY_USER_EMERGENCY_CONTACT = "http://119.3.136.127:7776/api/sys/user/updateContact";

    /**
     * 个人信息-用户头像图片地址前缀
     */
    public static final String HTTP_MY_USER_HEAD_URL_PREFIX = "http://119.3.136.127:8080/business_modules_api_app/proxy/show/";


    ///////////////////////////////////////////////////////////////////////////
    // ContentType类型
    ///////////////////////////////////////////////////////////////////////////
    public static final String CONTENT_JSON_TYPE = "application/json";

    public static final String CONTENT_TEXT_TYPE = "application/x-www-form-urlencoded";
}
