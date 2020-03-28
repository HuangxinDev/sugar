package com.njxm.smart.intefaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Hxin on 2020/3/26
 * Function: 登录接口
 */
public interface LoginApi {

    @POST("/auth/user/login")
    Call<ResponseBody> loginByPassword(@Query(value = "username") String userName, @Query(value = "password") String password,
                                       @Query(value = "code") String code, @Query(value = "kaptchaToken") String token);

    @POST("/auth/mobile/login")
    Call<ResponseBody> loginByMessage(@Field(value = "mobile") String mobile, @Field(value = "code") String code);
}
