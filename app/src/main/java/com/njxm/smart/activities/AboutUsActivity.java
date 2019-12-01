package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.ns.demo.R;

public class AboutUsActivity extends BaseActivity implements OnClickListener {


    // 服务协议
    private AppCompatTextView mAppServiceProtolBtn;
    // 版权信息
    private AppCompatTextView mAppCopyRightBtn;
    // 隐私政策
    private AppCompatTextView mAppPrivacyBtn;
    // 新功能介绍
    private AppCompatTextView mAppNewFeaturesBtn;

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBarTitle.setText("关于我们");

        mActionBarBackBtn.setImageResource(R.mipmap.arrow_back);
        mActionBarBackBtn.setOnClickListener(this);
        mActionBarRightBtn.setVisibility(View.GONE);

        mAppServiceProtolBtn = findViewById(R.id.app_service_protocol);
        mAppCopyRightBtn = findViewById(R.id.app_copyright);
        mAppPrivacyBtn = findViewById(R.id.app_privacy);
        mAppNewFeaturesBtn = findViewById(R.id.app_new_features);
        mAppServiceProtolBtn.setOnClickListener(this);
        mAppCopyRightBtn.setOnClickListener(this);
        mAppPrivacyBtn.setOnClickListener(this);
        mAppNewFeaturesBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mActionBarBackBtn) {
            finish();
        } else if (v == mAppServiceProtolBtn) {
            startWebView("service.html", "服务协议");
        } else if (v == mAppCopyRightBtn) {
            startWebView("service.html", "版权信息");
        } else if (v == mAppNewFeaturesBtn) {
            startWebView("features.html", "新功能介绍");
        } else if (v == mAppPrivacyBtn) {
            startWebView("privacy.html", "隐私协议");
        }
    }

    private void startWebView(String assetResource, String title) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("title_name", title);
        intent.putExtra("asset_name", assetResource);
        startActivity(intent);
    }
}
