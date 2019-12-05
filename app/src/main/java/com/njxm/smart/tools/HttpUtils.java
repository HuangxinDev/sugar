package com.njxm.smart.tools;

import com.njxm.smart.utils.LogTool;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

public class HttpUtils {
    private static OkHttpClient sOkHttpClient;


    // 登录接口
    public static Request loginRequest = new Request.Builder()
            .url("http://119.3.136.127:7777/auth/mobile/login?mobile=13951320937&code=807427")
            .addHeader("Platform", "App")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build();

    // 图形验证接口
    public static Request qrRequest = new Request.Builder()
            .url("http://119.3.136.127:7777/auth/kaptcha/get")
            .build();


    public static Request loginRequest2 = new Request.Builder()
            .url("http://119.3.136.127:7777/auth/user/login?username=admin&password=123456&code=cb72&kaptchaToken=ba23986a3f204155bf1314bb28f01df2")
            .build();

    private static OkHttpClient getsOkHttpClient() {
        if (sOkHttpClient == null) {
            sOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(3, TimeUnit.SECONDS)
                    .protocols(Util.immutableList(Protocol.HTTP_1_1))
                    .build();
        }
        return sOkHttpClient;
    }

    public static void execute(Request request) {
        Call call = getsOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogTool.printE("%s 请求失败，原因: %s", call.request().url(), e.getCause());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogTool.printD("%s 请求成功，数据: %s", call.request().url(), response.toString());
            }
        });
    }
}
