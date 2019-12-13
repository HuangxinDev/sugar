package com.njxm.smart.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.njxm.smart.utils.LogTool;
import com.ns.demo.R;

/**
 * WebView 加载 webView 页面
 */
public class WebViewActivity extends BaseActivity {

    private WebView mWebView;

    @Override

    protected int setContentLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebView = findViewById(R.id.webview);
        setWebViewSettings(mWebView);

        showLeftBtn(true, R.mipmap.arrow_back_blue);

        Intent intent = getIntent();
        if (intent != null) {
            showTitle(true, intent.getStringExtra("title_name"));
            if (intent.getBooleanExtra("loadUrl", false)) {
                LogTool.printD("url: %s", intent.getStringExtra("resUrl"));
                mWebView.loadUrl(intent.getStringExtra("resUrl"));
            } else {
                mWebView.loadUrl("file:///android_asset/html/" + intent.getStringExtra("resUrl"));
            }
        }
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        finish();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebViewSettings(WebView paramWebView) {
        if (paramWebView == null) {
            return;
        }
        WebSettings webSettings = paramWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
