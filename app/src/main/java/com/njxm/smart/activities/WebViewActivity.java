package com.njxm.smart.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.js.JsApi;
import com.ns.demo.R;

import butterknife.BindView;
import wendu.dsbridge.DWebView;

/**
 * WebView 加载 webView 页面
 */
@Route(path = "/app/about_us")
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webview)
    protected DWebView mWebView;

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setWebViewSettings(mWebView);
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        Intent intent = getIntent();
        if (intent != null) {
            showTitle(true, intent.getStringExtra("title_name"));
            mWebView.loadUrl(intent.getStringExtra("resUrl"));
        }
        mWebView.addJavascriptObject(new JsApi(this, mWebView), null);
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
