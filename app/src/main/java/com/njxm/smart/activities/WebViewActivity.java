package com.njxm.smart.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.ns.demo.R;

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
        mActionBarBackBtn.setImageResource(R.mipmap.arrow_back);

        mActionBarBackBtn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mActionBarRightBtn.setVisibility(View.GONE);
        if (bundle != null) {
            mWebView.loadUrl("file:///android_asset/html/" + bundle.getString("asset_name"));
            mActionBarTitle.setText(bundle.getString("title_name"));
        }


//        mWebView.loadUrl("file:///android_asset/html/privacy.html");
//        mWebView.loadUrl("file:///android_asset/html/service.html");
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
