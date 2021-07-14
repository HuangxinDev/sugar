/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ntxm.smart.R;

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

        this.showTitle(false, null);
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
    }
}
