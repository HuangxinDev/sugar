/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.http;

import com.njxm.smart.global.KeyConstant;
import com.sugar.android.common.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Hxin on 2020/3/30
 * Function: 请求头拦截器
 */
public class HeaderInterceptor implements Interceptor {
    private static final String ACCOUNT = SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT);

    private static final String AUTH = SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", "application/json")
                .addHeader("Account", ACCOUNT)
                .addHeader("Authorization", "Bearer-" + AUTH)
                .build();
        return chain.proceed(request);
    }
}
