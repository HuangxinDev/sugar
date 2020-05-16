package com.njxm.smart.api;

import com.njxm.smart.bean.LoginBean;
import com.njxm.smart.bean.ServerResponseBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Hxin on 2020/3/26
 * Function: 登录接口
 */
public interface LoginApi {

    @POST("/auth/{way}/login")
    @Headers(value = {"Content-Type:application/x-www-form-urlencoded"})
    Call<ServerResponseBean<LoginBean>> login(@Path("way") String name, @QueryMap Map<String, String> params);


    @POST("auth/kaptcha/get")
    void requestQrCode();
}
