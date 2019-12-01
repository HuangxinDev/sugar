package com.njxm.smart.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

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

        Bundle bundle = getIntent().getExtras();
        showLeftBtn(true, R.mipmap.arrow_back);
        if (bundle != null) {
            mWebView.loadUrl("file:///android_asset/html/" + bundle.getString("asset_name"));
            showTitle(true, bundle.getString("title_name"));
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
