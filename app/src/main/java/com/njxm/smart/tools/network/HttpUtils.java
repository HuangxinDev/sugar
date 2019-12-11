package com.njxm.smart.tools.network;

import android.util.Log;

import com.njxm.smart.utils.LogTool;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
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

    ///////////////////////////////////////////////////////////////////////////
    // 单例模式，避免创建多个HttpClient
    ///////////////////////////////////////////////////////////////////////////
    private HttpUtils() {
        //no instance
        sOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .protocols(Util.immutableList(Protocol.HTTP_1_1))
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

    public void postData(String url, Map<String, String> paramMap,
                         String contentType, final CallBack callBack, boolean isBody) {
        StringBuilder urlParams = new StringBuilder(url.length());
        urlParams.append(url);

        FormBody.Builder builder = new FormBody.Builder(Charset.forName("UTF-8"));

        if (isBody && paramMap != null && paramMap.size() > 0) {
            Set<Map.Entry<String, String>> aa = paramMap.entrySet();
            for (Map.Entry<String, String> entry : aa) {
                builder.add(entry.getKey(), entry.getValue());
            }
        } else {
            try {
                if (paramMap.size() <= 0) {
                    return;
                }
                urlParams.append("?");

                Set<Map.Entry<String, String>> aa = paramMap.entrySet();
                for (Map.Entry<String, String> entry : aa) {
                    urlParams.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                urlParams.deleteCharAt(urlParams.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder().url(urlParams.toString())
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", contentType)
//                .header("Platform", "App")
//                .addHeader("Platform", "PC")
//                .addHeader("Content-Type", "application/json")
                .post(requestBody)
//                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();


        LogTool.printD("host: %s, headers: %s", request.url(), request.headers().toString());

        sOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                LogTool.printW("request Fail, %s, exception: %s", call.request().url(),
                        Log.getStackTraceString(e));
                if (callBack != null) {
                    callBack.onFailed();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                LogTool.printD("host: %s/%s, result: %s", call.request().url().host(),
                        call.request().url().url().getPath(),
                        result);
                if (callBack != null) {
                    callBack.onSuccess(result);
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
