package com.njxm.smart.activities;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ntxm.smart.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.Nullable;

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

        com.njxm.smart.activities.WebViewActivity.setWebViewSettings(this.mWebView);
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);

        Intent intent = this.getIntent();
        if (intent != null) {
            this.showTitle(true, intent.getStringExtra("title_name"));
            this.mWebView.loadUrl(intent.getStringExtra("resUrl"));
        }
    }
}
