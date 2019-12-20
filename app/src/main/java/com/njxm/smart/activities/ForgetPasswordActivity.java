package com.njxm.smart.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ns.demo.R;

/**
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity {
    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showTitle(false, null);
        showLeftBtn(true, R.mipmap.arrow_back_blue);
    }
}
