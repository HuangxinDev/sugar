package com.njxm.smart.tools.network;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.utils.LogTool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

public final class HttpUtils {

    private static HttpUtils sInstance = null;

    private static final Object sLock = new Object();

    private static OkHttpClient sOkHttpClient;

    // 图形验证码
    public static final int REQUEST_QR = 0;

    // 登录接口
    public static final int REQUEST_LOGIN = 1;

    // SMS接口
    public static final int REQUEST_SMS = 2;

    ///////////////////////////////////////////////////////////////////////////
    // 单例模式，避免创建多个HttpClient
    ///////////////////////////////////////////////////////////////////////////
    private HttpUtils() {
        //no instance
        sOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .protocols(Util.immutableList(Protocol.HTTP_2, Protocol.HTTP_1_1))
                .build();
    }

    public static HttpUtils getInstance() {

        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new HttpUtils();
                }
            }
        }
        return sInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return sOkHttpClient;
    }


    public void postData(final int requestId, final Request request, final HttpCallBack callBack) {
        sOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogTool.printE("request url: %s, error: %s , %s", call.request().url(),
                        e.getMessage(), Log.getStackTraceString(e.getCause()));
                if (callBack != null) {
                    callBack.onFailed(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject object = JSONObject.parseObject(response.body().string());
                boolean success = object.getBoolean("success");
                int code = object.getInteger("code");
                LogTool.printD("url: %s, success: %s, code: %s, message: %s",
                        call.request().url(), success, code, object.getString("message"));
                if (callBack != null) {
                    if (success && code == 200) {
                        callBack.onSuccess(requestId, true, code, object.getString("data"));
                    } else {
                        callBack.onSuccess(requestId, false, code, object.getString("message"));
                    }
                }
            }
        });
    }


    public void postDataWithParams(final int requestId, String url, Map<String, String> paramMap,
                                   String contentType, final HttpCallBack callBack) {
        StringBuilder urlParams = new StringBuilder(url.length());
        urlParams.append(url);

        FormBody.Builder builder = new FormBody.Builder();
        try {
            if (paramMap != null && paramMap.size() <= 0) {
                urlParams.append("?");
                Set<Map.Entry<String, String>> aa = paramMap.entrySet();
                for (Map.Entry<String, String> entry : aa) {
                    urlParams.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                urlParams.deleteCharAt(urlParams.length() - 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder().url(urlParams.toString())
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", contentType)
                .post(requestBody)
                .build();
        sOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                LogTool.printW("request Fail, %s, exception: %s", call.request().url(),
                        Log.getStackTraceString(e));
                if (callBack != null) {
                    callBack.onFailed(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                JSONObject jsonObject = JSONObject.parseObject(result);
                int code = jsonObject.getInteger("code");

                if (code == 401) {
                    // TODO 登录过期，退回登录页面
                    return;
                }

                if (callBack == null) {
                    return;
                }

                LogTool.printD("response result: %s", result);
                boolean success = jsonObject.getBoolean("success");
                if (code == 200) {
                    String data = jsonObject.getString("data");
                    callBack.onSuccess(requestId, success, code, data);
                } else {
                    callBack.onFailed(jsonObject.getString("message"));
                }
            }
        });

    }

    public void postDataWithBody(final int requestId, String url,
                                 HashMap<String, String> paramMaps, final HttpCallBack callBack) {

        JSONObject body = new JSONObject();
        if (paramMaps != null && paramMaps.size() > 0) {
            for (Map.Entry<String, String> entry : paramMaps.entrySet()) {
                body.put(entry.getKey(), entry.getValue());
            }
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE),
                body.toString());

        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .build();

        sOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject jsonObject = JSONObject.parseObject(response.body().string());
                if (callBack != null) {
                    callBack.onSuccess(requestId, jsonObject.getBoolean("success"),
                            jsonObject.getInteger("code"), jsonObject.getString("data"));
                }
            }
        });

    }

    public void execute(Request request, Map<String, String> paramMap) {

        Call call = sOkHttpClient.newCall(request);
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


//    File file =
//            new File(Environment.getExternalStoragePublicDirectory("") + "/Android/data/" + getPackageName() + "/xx.png");
//
//    http://119.3.136.127:7776/api/sys/userMedical/insert
//
//    RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
//
//    RequestBody requestBody1 = new MultipartBody.Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart("file", "testImage.png", requestBody)
//            .addFormDataPart("sumrUserId", "1")
//            .addFormDataPart("userName", "user_android").build();
//
//    Request request = new Request.Builder().url("http://119.3.136.127:7776/api/sys/userMedical/insert")
//            .header("Platform", "App")
//            .header("Content-Type", "application/x-www-form-urlencoded")
//            .header("Account", "admin")
//            .header("Authorization", "Bearer-eyJhbGciOiJIUzI1NiJ9" +
//                    ".eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNTc4NDgyNDYxLCJ1c2VySWQiOiIzZWQwZTU2YzM5Yzc0ZWE4ZjJhNjU5Yzk3MmIyMzBiZSIsImlhdCI6MTU3NTg5MDQ2MSwianRpIjoiNGQwYjJkZjctNWE5NS00ZDVjLTg5MjctYzJmNmNkYzM4NGM5In0.1ky3wwlp-RwSCgVxga1AxPuT4Umq6y4IwH4f-ESUBao").post(requestBody1)
//            .build();
//
//        client.newCall(request).enqueue(new Callback() {
//        @Override
//        public void onFailure(Call call, IOException e) {
//            LogTool.printD("post failed & response: ");
//        }
//
//        @Override
//        public void onResponse(Call call, Response response) throws IOException {
//            LogTool.printD("post %s success & response: %s ", call.request().url(),
//                    response.code());
//        }
//    });

    public static final class MimeType {
        public static final String JSON = "application/json";
        public static final String TEXT = "application/x-www-form-urlencoded";
    }

}
