package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.njxm.smart.view.AppEditText;
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

    // 账号验证布局
    private AppEditText mLoginAccountEditText;

    // 密码验证布局
    private AppEditText mLoginPwdEditText;

    // 图形验证布局
    private AppEditText mLoginQrEditText;

    // 验证码登录
    private AppEditText mLoginNumberEditText;

    // 忘记密码
    private AppCompatTextView mForgetPwdBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginBtn = findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(this);

        mQuickLoginBtn = findViewById(R.id.quick_login_btn);
        mQuickLoginBtn.setOnClickListener(this);
        mPasswordLoginBtn = findViewById(R.id.password_login_btn);
        mPasswordLoginBtn.setOnClickListener(this);

        mQuickLoginDivider = findViewById(R.id.quick_login_divider);
        mPasswordLoginDivider = findViewById(R.id.password_login_divider);

        mForgetPwdBtn = findViewById(R.id.forget_password);
        mForgetPwdBtn.setOnClickListener(this);

        mLoginAccountEditText = findViewById(R.id.login_account);
        mLoginPwdEditText = findViewById(R.id.login_pwd);
        mLoginQrEditText = findViewById(R.id.login_qr_code);
        mLoginNumberEditText = findViewById(R.id.login_number_code);

        switchLoginWay(true);
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginBtn) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (v == mQuickLoginBtn) {
            switchLoginWay(false);
        } else if (v == mPasswordLoginBtn) {
            switchLoginWay(true);
        } else if (v == mForgetPwdBtn) {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("action", "1");
            startActivity(intent);
        }
    }

    /**
     * 切换登录方式
     *
     * @param pwdLogin 密码登录
     */
    private void switchLoginWay(boolean pwdLogin) {
        if (pwdLogin) {
            mPasswordLoginBtn.setEnabled(false);
            mQuickLoginBtn.setEnabled(true);
            mPasswordLoginDivider.setVisibility(View.VISIBLE);
            mQuickLoginDivider.setVisibility(View.INVISIBLE);

            mLoginPwdEditText.setVisibility(View.VISIBLE);
            mLoginQrEditText.setVisibility(View.GONE);
            mLoginNumberEditText.setVisibility(View.GONE);

        } else {
            mPasswordLoginBtn.setEnabled(true);
            mQuickLoginBtn.setEnabled(false);
            mQuickLoginDivider.setVisibility(View.VISIBLE);
            mPasswordLoginDivider.setVisibility(View.INVISIBLE);


            mLoginPwdEditText.setVisibility(View.GONE);
            mLoginQrEditText.setVisibility(View.VISIBLE);
            mLoginNumberEditText.setVisibility(View.VISIBLE);
        }
    }
}