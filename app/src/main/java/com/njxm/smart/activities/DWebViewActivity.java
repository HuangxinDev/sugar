package com.njxm.smart.activities;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ntxm.smart.R;

import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import butterknife.BindView;
import wendu.dsbridge.DWebView;

@Route(path = "/app/webview")
public class DWebViewActivity extends BaseActivity {

    @Autowired
    public String loadUrl;
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
        ARouter.getInstance().inject(this);
        this.llRoot.setPadding(0, com.njxm.smart.activities.BaseActivity.getStatusBarHeight(this), 0, 0);
        this.mDWebView.addJavascriptObject(this, null);
        this.mDWebView.loadUrl(this.getIntent().getStringExtra("loadUrl"));
    }
}
