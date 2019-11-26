package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ns.demo.R;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    // 登录按钮
    private TextView mLoginBtn;

    // 快捷登录 Tab
    private TextView mQuickLoginBtn;

    // 密码登录 Tab
    private TextView mPasswordLoginBtn;

    // 快捷登录和密码登录下方的下划线
    private View mQuickLoginDivider, mPasswordLoginDivider;

    // 用户名编辑框
    private EditText mUserAccountEditText;

    // 密码编辑框
    private EditText mUserPasswordEditText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginBtn = findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(this);

        mQuickLoginBtn = findViewById(R.id.quick_login_btn);
        mQuickLoginBtn.setOnClickListener(this);
        mPasswordLoginBtn = findViewById(R.id.password_login_btn);
        mPasswordLoginBtn.setOnClickListener(this);

        mQuickLoginDivider = findViewById(R.id.quick_login_divider);
        mPasswordLoginDivider = findViewById(R.id.password_login_divider);

        mUserPasswordEditText = findViewById(R.id.login_user_password_et);

    }

    @Override
    public void onClick(View v) {
        if (v == mLoginBtn) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (v == mQuickLoginBtn) {
            mQuickLoginBtn.setEnabled(false);
            mPasswordLoginBtn.setEnabled(true);
            mQuickLoginDivider.setVisibility(mQuickLoginBtn.isEnabled() ? View.INVISIBLE : View.VISIBLE);
            mPasswordLoginDivider.setVisibility(mPasswordLoginBtn.isEnabled() ? View.INVISIBLE : View.VISIBLE);
        } else if (v == mPasswordLoginBtn) {
            mQuickLoginBtn.setEnabled(true);
            mPasswordLoginBtn.setEnabled(false);
            mQuickLoginDivider.setVisibility(mQuickLoginBtn.isEnabled() ? View.INVISIBLE : View.VISIBLE);
            mPasswordLoginDivider.setVisibility(mPasswordLoginBtn.isEnabled() ? View.INVISIBLE : View.VISIBLE);
        }
    }
}