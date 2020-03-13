package com.njxm.smart.eventbus;

import androidx.annotation.IntDef;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.tools.network.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 *
 */
public class RequestEvent {

    public static final int UPLOAD_FILE = 425;
    public static final int REQUEST_PARAMS = 642;

    @IntDef({UPLOAD_FILE, REQUEST_PARAMS})
    @interface RequestType {

    }

    /**
     * 上传类型
     */
    private int type;

    public String url;

    public HttpMethod httpMethod; // 默认为POST

    public HashMap<String, String> headers;

    public HashMap<String, String> params;

    public List<MultipartBody.Part> parts;

    public String bodyJson;

    public boolean newBuilder = false;

    public RequestEvent(@RequestType int type, int requestId) {
        this.type = type;
    }

    private RequestEvent(Builder builder) {
        this.url = builder.url;
        this.headers = builder.headers;
        this.params = builder.params;
        this.type = REQUEST_PARAMS;
        this.newBuilder = builder.newBuilder;
        this.parts = builder.parts;
        this.httpMethod = builder.method;
        if (builder.bodyJsonMap.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, String> bodyJson : builder.bodyJsonMap.entrySet()) {
                jsonObject.put(bodyJson.getKey(), bodyJson.getValue());
            }
            bodyJson = jsonObject.toJSONString();
        }
    }

    public int getRequestType() {
        return type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String url;
        private String bodyJson;
        private HashMap<String, String> headers = new HashMap<>();
        private HashMap<String, String> params = new HashMap<>();
        private HashMap<String, String> bodyJsonMap = new HashMap<>();
        private List<MultipartBody.Part> parts = new ArrayList<>();
        protected boolean newBuilder = false;
        private HttpMethod method = HttpMethod.POST;

        public int requestId;

        /**
         * Get/Post
         *
         * @param method
         * @return
         */
        public Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder voidBuilder(boolean newBuilder) {
            this.newBuilder = newBuilder;
            return this;
        }

        public Builder addHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public Builder addParam(String key, String value) {
            params.put(key, value);
            return this;
        }

        public Builder addBodyJson(String key, String value) {
            bodyJsonMap.put(key, value);
            return this;
        }

        public Builder addPart(MultipartBody.Part part) {
            if (part != null) {
                parts.add(part);
            }
            return this;
        }


        public RequestEvent build() {
            return new RequestEvent(this);
        }
    }

}
