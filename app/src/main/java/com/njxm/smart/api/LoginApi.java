/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.api;

import com.njxm.smart.bean.LoginBean;
import com.njxm.smart.bean.ServerResponseBean;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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


    /**
     *
     */
    @FormUrlEncoded
    @POST("/abc/abc/{index}")
    @Headers({"Content-Type:application/json",
            "Platform:APP"})
    @Multipart
    void test(@Path("index") String index, @PartMap(encoding = "utf-8") MultipartBody.Part data, @FieldMap String... values);


    @Multipart
    void postFile(@Part okhttp3.MultipartBody.Part... parts);
}
