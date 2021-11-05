package com.njxm.smart.activities.login;

import com.njxm.smart.global.KeyConstant;

import java.util.HashMap;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class LoginData {
    private String token;

    private String username;

    private String password;

    private String verifyCode;

    private final HashMap<String, String> requestParams = new HashMap<>();

    public void setUsername(String username) {
        requestParams.put("username", username);
    }

    public void setPassword(String password) {
        requestParams.put("password", password);
    }

    public void setMobile(String mobile) {
        requestParams.put("mobile", mobile);
    }

    public void setVerifyCode(String code) {
        requestParams.put("code", code);
    }

    public void setToken(String token) {
        requestParams.put(KeyConstant.KEY_QR_IMAGE_TOKEN, token);
    }

    public HashMap<String, String> getRequestParams() {
        return requestParams;
    }

    public String getToken() {
        return token;
    }

}
