package com.njxm.smart.activities;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ns.demo.R;

import butterknife.BindView;
import wendu.dsbridge.DWebView;

@Route(path = "/app/webview")
public class DWebViewActivity extends BaseActivity {

    @BindView(R.id.webview_kit)
    protected DWebView mDWebView;

    @BindView(R.id.ll_root)
    protected LinearLayout llRoot;


    @Override
    protected int setContentLayoutId() {
        return R.layout.js_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        llRoot.setPadding(0, getStatusBarHeight(this), 0, 0);

        mDWebView.loadUrl(getIntent().getStringExtra("loadUrl"));
    }
}
