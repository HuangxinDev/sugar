package com.njxm.smart.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ns.demo.R;

public class RealNameAuthenticationActivity extends BaseActivity {
    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_realname_authentication;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBarTitle(getIntent().getStringExtra("title"));
        showLeftBtn(true, R.mipmap.arrow_back_blue);
    }

    @Override
    public void onClickLeftBtn() {
        super.onBackPressed();
    }
}
