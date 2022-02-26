/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ntxm.smart.R;
import com.sugar.android.common.safe.SafeIntent;

import butterknife.BindView;
import wendu.dsbridge.DWebView;

/**
 * WebView 加载 webView 页面
 */
@Route(path = "/app/about_us")
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webview)
    protected DWebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    private static void setWebViewSettings(WebView paramWebView) {
        if (paramWebView == null) {
            return;
        }
        WebSettings webSettings = paramWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.njxm.smart.ui.activities.WebViewActivity.setWebViewSettings(this.mWebView);
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);

        SafeIntent intent = new SafeIntent(getIntent());
        this.showTitle(true, intent.getStringExtra("title_name"));
        this.mWebView.loadUrl(intent.getStringExtra("resUrl"));
    }
}
