/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.constant;

import org.jetbrains.annotations.NotNull;

import com.njxm.smart.GlobalConst;

/**
 * 服务端接口url定义
 */
public enum UrlPath {

    PATH_PICTURE_VERIFY(1, "/auth/kaptcha/get"), // 图形验证码
    PATH_SMS(1, "/auth/sms/sendSms"), //发送短信接口
    PATH_PASSWORD_LOGIN(1, "/auth/user/login"), // 用户名密码登录
    PATH_SYS_LOGOUT(1, "/auth/user/logout"), // 登出
    PATH_SMS_PASSWORD_LOGIN(1, "/auth/mobile/login"), // 动态短信验证码登录
    PATH_MODIFY_PWD(2, "/sys/user/updatePassByMobile"), // 修改密码
    PATH_RESET_PWD(2, "/api/sys/user/updatePass"), // 重置密码
    PATH_USER_PHONE_REPLACE(2, "/api/sys/user/updateMobile"), // 更换手机号
    PATH_ABOUT_US(2, "/sys/link/aboutUsIndex"), // 关于我们页面
    PATH_MEDICAL_REPORT_STATUS(2, "/api/sys/userMedical/updateStatus"), // 体检报告状态检查
    PATH_MEDICAL_REPORT_COMMIT(2, "/api/sys/userMedical/insert"), // 提交体检报告
    PATH_MEDICAL_REPORT_PULL(2, "/api/sys/userMedical/findByUserId"), // 拉取体检报告
    PATH_USER_BASE_NEWS(2, "/api/sys/user/findUserForIndex"), // 用户基本信息
    PATH_USER_DETAILS_NEWS(2, "/api/sys/user/findById"), // 用户详细信息
    PATH_USER_HEAD_COMMIT(2, "/api/sys/user/updateIcon"), // 上传用户头像
    PATH_USER_EDU_NEWS_COMMIT(2, "/api/sys/user/updateEducation"), // 上传学历信息
    PATH_USER_EDU_PULL(2, "/api/sys/dict/findList"), // 拉取用户学历信息列表
    PATH_USER_CERTIFICATE_PULL(2, "/api/sys/userCertificate/findCertificateList"), // 拉取用户证书信息
    PATH_USER_CERTIFICATE_COMMIT(2, "/api/sys/userCertificate/insertCertificate"), // 上传证书信息
    PATH_USER_ADDRESS_COMMIT(2, "/api/sys/user/updateAddress"), // 上传用户地址
    PATH_USER_EMERGENCY_PEOPLE_NEWS_COMMIT(2, "/api/sys/user/updateContact"), // 上传紧急联系人
    PATH_USER_REAL_AUTH(2, "/api/sys/user/personVerify"), // 上传用户信息进行实名认证
    PATH_PROVINCE_CITY_AREA(2, "/sys/provinceAndCity/findAll"), // 省市区数据
    PATH_CERTIFICATE_TYPE_PULL(2, "/api/sys/userCertificate/findTypePage"), // 获取常见证书类型(大类)
    PATH_SUB_CERTIFICATE_TYPE_PULL(2, "/api/sys/userCertificate/findTypeList"), // 获取常见证书类型(子类)
    PATH_MAIN_KAO_QIN(3, "/#/attendance/sign"), // 首页考勤
    PATH_PICTURE_PREFIX(3, "/business_modules_api_app/proxy/show/"); // 图片信息前缀url

    private final int type; // 接口类型
    private final String path;


    /**
     * @param type  业务类型： 1 鉴权, 2 业务, 3 H5
     * @param param url path
     */
    UrlPath(int type, String param) {
        this.type = type;
        this.path = param;
    }

    public String getUrl() {
        String host;
        host = this.getHost();
        return host + this.getPath();
    }

    @NotNull
    private String getHost() {
        String host;
        switch (this.type) {
            case 1:
                host = GlobalConst.GRANT_HOST;
                break;
            case 2:
                host = GlobalConst.BIZ_HOST;
                break;
            case 3:
            default:
                host = GlobalConst.H5_HOST;
                break;
        }
        return host;
    }

    public String getPath() {
        return this.path;
    }
}
