package com.njxm.smart.tools.network;

import com.njxm.smart.eventbus.LogoutEvent;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.http.HeaderInterceptor;
import com.njxm.smart.utils.JsonUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.greenrobot.eventbus.EventBus;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.internal.Util;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class HttpUtils {

    private static final HttpUtils sInstance = null;
    private static final Object sLock = new Object();
    private static OkHttpClient sOkHttpClient;
    private String baseUrl;

    ///////////////////////////////////////////////////////////////////////////
    // 单例模式，避免创建多个HttpClient
    ///////////////////////////////////////////////////////////////////////////
    private HttpUtils() {
        //no instance
        com.njxm.smart.tools.network.HttpUtils.sOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .protocols(Util.immutableList(Protocol.HTTP_2, Protocol.HTTP_1_1))
                .addInterceptor(new HeaderInterceptor())
                .build();
    }


    private HttpUtils(Builder builder) {
        this.baseUrl = builder.baseUrl;
        com.njxm.smart.tools.network.HttpUtils.getInstance();
    }

    public static HttpUtils getInstance() {
        return HttpInstance.instance;
    }

    /**
     * 获取OKHttpClient实例
     *
     * @return OKHttpClient实例
     */
    public static OkHttpClient getOkHttpClient() {
        return com.njxm.smart.tools.network.HttpUtils.sOkHttpClient;
    }

    /**
     * 网络请求成功回调
     *
     * @param response
     * @param requestEvent
     */
    private static void onSuccess(Response response, RequestEvent requestEvent) {
        try {
            ResponseEvent responseEvent = JsonUtils.getJsonObject(response.body().string(),
                    ResponseEvent.class);
            responseEvent.setUrl(requestEvent.url);

            if (!responseEvent.isSuccess()) {
                EventBus.getDefault().post(new ToastEvent(responseEvent.getMessage()));
                return;
            }

            int code = responseEvent.getCode();
            if (code == 401 || code == 999) {
                // 身份信息过期,需要重新登录
                EventBus.getDefault().post(new LogoutEvent());
                EventBus.getDefault().post(new ToastEvent("身份信息过期,需要重新登录"));
                return;
            }
            EventBus.getDefault().post(responseEvent);
        } catch (IOException e) {
//            LogTool.printE(Log.getStackTraceString(e));
        }
    }

    /**
     * 获取Retrofit的指定Api
     *
     * @param tClass 网络请求的Api
     * @param <T>    泛型
     * @return 对应类型的网络请求Api
     */
    public static <T> T getApi(Class<T> tClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.136.127:7776")
                .client(com.njxm.smart.tools.network.HttpUtils.getOkHttpClient())
                .client(com.njxm.smart.tools.network.HttpUtils.sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(tClass);
    }

    /**
     * 普通网络请求-请求数据
     *
     * @param requestEvent
     */
    public void request(RequestEvent requestEvent) {


//        RequestBody body;
//        StringBuilder url = new StringBuilder(requestEvent.url);
//        if (requestEvent.params != null && requestEvent.params.size() > 0) {
//            url.append("?");
//            for (Map.Entry<String, String> entry : requestEvent.params.entrySet()) {
//                url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
//            }
//            url.deleteCharAt(url.length() - 1);
//        }
//
//
//        if (StringUtils.isNotEmpty(requestEvent.bodyJson)) {
//            body = FormBody.create(MediaType.parse("application/json"),
//                    requestEvent.bodyJson);
//        } else {
//            body = new FormBody.Builder().build();
//        }
//
//        Request request = new Request.Builder()
//                .url(url.toString())
//                .build();
//
//
//        if (requestEvent.httpMethod == HttpMethod.POST) {
//            request = request.newBuilder().post(body).build();
//        }
//
//        sOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new ToastEvent("网络异常，请稍后再试"));
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                onSuccess(response, requestEvent);
//            }
//        });
    }

    /**
     * 上传资源文件
     *
     * @param requestEvent
     */
    public void doPostFile(RequestEvent requestEvent) {
//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);
//
//        if (requestEvent.parts != null && requestEvent.parts.size() > 0) {
//            for (MultipartBody.Part part : requestEvent.parts) {
//                builder.addPart(part);
//            }
//        }
//
//        Request request = new Request.Builder().url(requestEvent.url)
//                .post(builder.build())
//                .build();
//        sOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new ToastEvent("网络异常，请稍后再试"));
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                onSuccess(response, requestEvent);
//            }
//        });
    }

    /**
     * 获取Retrofit的指定Api
     *
     * @param tClass 网络请求的Api
     * @param <T>    泛型
     * @return 对应类型的网络请求Api
     */
    public <T> T getServerApi(Class<T> tClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.baseUrl)
                .client(com.njxm.smart.tools.network.HttpUtils.getOkHttpClient())
                .client(com.njxm.smart.tools.network.HttpUtils.sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(tClass);
    }

    private static class HttpInstance {
        static HttpUtils instance = new HttpUtils();
    }

    public static class Builder {

        private String baseUrl;

        public Builder() {

        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public HttpUtils build() {
            return new HttpUtils(this);
        }
    }
}
