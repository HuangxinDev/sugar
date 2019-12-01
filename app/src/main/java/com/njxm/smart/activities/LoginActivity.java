package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

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
    private ConstraintLayout mUserAccountLayout;

    // 密码验证布局
    private ConstraintLayout mUserPwdLayout;

    // 图形验证布局
    private ConstraintLayout mUserPicLayout;

    // 验证码登录
    private ConstraintLayout mUserCodeLayout;

    // 用户名编辑框
    private AppCompatEditText mUserAccountEditText;

    // 密码编辑框
    private AppCompatEditText mUserPasswordEditText;

    // 图形验证码编辑框
    private AppCompatEditText mUserPicEditText;

    // 验证码编辑框
    private AppCompatEditText mUserVerifyCodeEditText;

    // 获取验证码按钮
    private AppCompatTextView mGetVerifyCodeBtn;

    // 密码显示/隐藏按钮
    private AppCompatTextView mShowPwdBtn;

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

        mUserAccountLayout = findViewById(R.id.verify_account);
        mUserPicLayout = findViewById(R.id.verify_picture);
        mUserPwdLayout = findViewById(R.id.verify_pwd);
        mUserCodeLayout = findViewById(R.id.verify_code);

        mUserAccountEditText = findViewById(R.id.user_account_edit);

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

            mUserPwdLayout.setVisibility(View.VISIBLE);
            mUserPicLayout.setVisibility(View.GONE);
            mUserCodeLayout.setVisibility(View.GONE);

            mUserAccountEditText.setHint(R.string.hint_user_login_account);
        } else {
            mPasswordLoginBtn.setEnabled(true);
            mQuickLoginBtn.setEnabled(false);
            mQuickLoginDivider.setVisibility(View.VISIBLE);
            mPasswordLoginDivider.setVisibility(View.INVISIBLE);

            mUserPwdLayout.setVisibility(View.GONE);
            mUserPicLayout.setVisibility(View.VISIBLE);
            mUserCodeLayout.setVisibility(View.VISIBLE);

            mUserAccountEditText.setHint(R.string.hint_user_login_account2);
        }
    }
}