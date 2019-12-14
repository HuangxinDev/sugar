package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity implements HttpCallBack {

    private static final int REQUEST_WEBVIEW_URL = 316;

    private AppCompatImageView mAppIcon;

    private AppCompatTextView mAppName;

    private AppCompatTextView mAppVersion;

    // 服务协议
    private View mAppServiceBtn;
    // 版权信息
    private View mAppCopyRightBtn;
    // 隐私政策
    private View mAppPrivacyBtn;
    // 新功能介绍
    private View mAppFeaturesBtn;

    private boolean loadUrl = false;

    private static final String[] HTMLS = {"service.html", "service.html", "privacy.html", "features.html"};

    private List<UrlBean> mUrls = new ArrayList<>();

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_about_us;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("关于我们");
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        mAppServiceBtn = findViewById(R.id.about_us_service);
        mAppPrivacyBtn = findViewById(R.id.about_us_secret);
        mAppCopyRightBtn = findViewById(R.id.about_us_version);
        mAppFeaturesBtn = findViewById(R.id.about_us_feature);

        mAppServiceBtn.setOnClickListener(this);
        mAppPrivacyBtn.setOnClickListener(this);
        mAppCopyRightBtn.setOnClickListener(this);
        mAppFeaturesBtn.setOnClickListener(this);

        Request request = new Request.Builder().url(HttpUrlGlobal.HTTP_ABOUT_US).build();
        HttpUtils.getInstance().postData(REQUEST_WEBVIEW_URL, request, this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int index = 0;

        if (mUrls.size() < 4) {
            loadUrl = false; // 加载本地资源
        }

        String title = "";
        if (v == mAppServiceBtn) {
            index = 0;
            title = "服务协议";
        } else if (mAppCopyRightBtn == v) {
            index = 1;
            title = "版权信息";
        } else if (mAppPrivacyBtn == v) {
            index = 2;
            title = "隐私政策";
        } else if (v == mAppFeaturesBtn) {
            index = 3;
            title = "新功能介绍";
        }
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("title_name", title);
        if (loadUrl && mUrls.get(index) != null && mUrls.get(index).getUrl() != null) {
            loadUrl = true;
            intent.putExtra("resUrl", mUrls.get(index).url);
        } else {
            loadUrl = false;
            intent.putExtra("resUrl", HTMLS[index]);
        }
        intent.putExtra("loadUrl", loadUrl);
        startActivity(intent);
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        finish();
    }

    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        if (success && code == 200) {
            loadUrl = true;

            List<UrlBean> urlBeans = JSONObject.parseArray(data, UrlBean.class);
            mUrls.addAll(urlBeans);
        }
    }

    @Override
    public void onFailed() {
    }

    private static class UrlBean {
        @JSONField(name = "key")
        public String name;

        public String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }

    public static void main(String[] args) {
        String json = "[{\"key\":\"服务协议\",\"url\":\"https://www.baidu.com/\"},{\"key\":\"版本信息\",\"url\":\"https://www.baidu.com/\"},{\"key\":\"隐私政策\",\"url\":\"https://www.baidu.com/\"},{\"key\":\"新功能介绍\",\"url\":\"https://www.baidu.com/\"}]";

        List<UrlBean> beans = JSONObject.parseArray(json, UrlBean.class);

        for (int i = 0; i < beans.size(); i++) {
            System.out.println(beans.get(i).getUrl());
        }
    }
}
