package com.njxm.smart.activities.login;

import com.njxm.smart.api.LoginApi;
import com.njxm.smart.bean.LoginBean;
import com.njxm.smart.bean.ServerResponseBean;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Response;

/**
 * 登陆仓
 *
 * @author huangxin
 * @date 2021/7/20
 */
public class LoginRepository {
    private LoginApi api;

    public LoginRepository(LoginApi api) {
        this.api = api;
    }

    public Response<ServerResponseBean<LoginBean>> login() {
        try {
            return api.login("", new HashMap<>()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
