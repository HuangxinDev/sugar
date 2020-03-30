package com.njxm.smart.http;

import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Hxin on 2020/3/30
 * Function: 请求头拦截器
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request().newBuilder()
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", "application/json")
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                .build();

        return chain.proceed(request);
    }
}
