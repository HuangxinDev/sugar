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
