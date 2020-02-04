package com.njxm.smart.global;

import com.njxm.smart.GlobalConst;

public final class HttpUrlGlobal {

    public static final String URL_WORKCENTER_ITEMS = GlobalConst.URL_BIZ_PREFIX + "/api/sys/user/findResourceList";

    /**
     * 图形验证码接口
     */
    public static final String HTTP_QR_URL =  GlobalConst.URL_GRANT_PREFIX + "/auth/kaptcha/get";

    /**
     * 密码登录接口
     */
    public static final String HTTP_USER_LOGIN_URL =  GlobalConst.URL_GRANT_PREFIX + "/auth/user/login";

    /**
     * 手机号登录接口
     */
    public static final String HTTP_MOBILE_LOGIN_URL =  GlobalConst.URL_GRANT_PREFIX + "/auth/mobile/login";

    /**
     * 短信请求接口
     */
    public static final String HTTP_SMS_URL =  GlobalConst.URL_GRANT_PREFIX + "/auth/sms/sendSms";

    /**
     * 忘记密码修改密码
     */
    public static final String HTTP_RESET_PWD_URL =  GlobalConst.URL_BIZ_PREFIX + "/sys/user/updatePassByMobile";

    /**
     * 重置密码修改密码
     */
    public static final String URL_SETTINGS_RESET_PWD =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/user/updatePass";

    /**
     * 关于我们页面
     */
    public static final String HTTP_ABOUT_US =  GlobalConst.URL_BIZ_PREFIX + "/sys/link/aboutUsIndex";

    /**
     * 设置页面-登出
     */
    public static final String HTTP_MY_SETTING_LOGOUT =  GlobalConst.URL_GRANT_PREFIX + "/auth/user/logout";


    /**
     * 体检报告状态检查接口
     */
    public static final String HTTP_MEDICAL_STATE =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/userMedical/updateStatus";

    /**
     * 体检报告提交或者更新接口
     */
    public static final String HTTP_MEDICAL_COMMIT_UPDATE =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/userMedical/insert";

    /**
     * 体检报告获取已经上传的文件
     */
    public static final String HTTP_MEDICAL_GET_IMAGE =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/userMedical/findByUserId";

    /**
     * 个人信息-初始化页面
     */
    public static final String HTTP_MY_USER_INIT_NEWS =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/user/findUserForIndex";

    /**
     * 个人信息-信息详情
     */
    public static final String HTTP_MY_USER_DETAIL_NEWS =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/user/findById";

    /**
     * 个人信息-修改头像
     */
    public static final String HTTP_MY_USER_HEAD =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/user/updateIcon";

    /**
     * 个人信息-录入人脸
     */
    public static final String HTTP_MY_USER_INPUT_FACE =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/user/updateFace";

    /**
     * 个人信息-修改学历
     */
    public static final String HTTP_MY_USER_EDUCATION =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/user/updateEducation";

    /**
     * 个人信息-紧急联系人
     */
    public static final String HTTP_MY_USER_EMERGENCY_CONTACT =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/user/updateContact";

    /**
     * 个人信息-用户头像图片地址前缀
     */
    public static final String HTTP_MY_USER_HEAD_URL_PREFIX = "http://119.3.136.127:8080" +
        "/business_modules_api_app/proxy/show/";

    /**
     * 个人信息-省市区数据
     */
    public static final String HTTP_COMMON_CITY_URL =  GlobalConst.URL_BIZ_PREFIX + "/sys/provinceAndCity/findAll";

    /**
     * 个人信息-上传现居地址
     */
    public static final String URL_UPLOAD_USER_ADDRESS =
             GlobalConst.URL_BIZ_PREFIX + "/api/sys/user/updateAddress";

    public static final String URL_USER_REAL_Authentication =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/user/personVerify";

    /**
     * 个人信息-获取学历列表
     */
    public static final String URL_EDUCATION_LIST =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/dict/findList";

    /**
     * 个人信息-持有证书-获取证书列表
     */
    public static final String URL_GET_USER_CERTIFICATE_LIST =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/userCertificate/findCertificateList";

    /**
     * 获取证书类型(主类)分页
     */
    public static final String URL_GET_CERTIFICATE_MAIN_LIST =
             GlobalConst.URL_BIZ_PREFIX + "/api/sys/userCertificate/findTypePage";

    public static final String URL_GET_CERTIFICATE_SUB_TYPE =
            GlobalConst.URL_BIZ_PREFIX + "/api/sys/userCertificate/findTypeList";

    /**
     * 上传证书
     */
    public static final String URL_UPLOAD_CERTIFICATE_LIST =
             GlobalConst.URL_BIZ_PREFIX + "/api/sys/userCertificate" +
                    "/insertCertificate";


    /**
     * 设置-更换手机号
     */
    public static final String URL_SETTINGS_UPDATE_TEL_PHONE =  GlobalConst.URL_BIZ_PREFIX + "/api/sys/user/updateMobile";


    ///////////////////////////////////////////////////////////////////////////
    // ContentType类型
    ///////////////////////////////////////////////////////////////////////////
    public static final String CONTENT_JSON_TYPE = "application/json";

    public static final String CONTENT_TEXT_TYPE = "application/x-www-form-urlencoded";
}
