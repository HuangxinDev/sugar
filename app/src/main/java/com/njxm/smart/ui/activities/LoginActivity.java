/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.contract.LoginContract;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.presenter.LoginPresenter;
import com.njxm.smart.ui.activities.main.MainActivity;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.AppEditText;
import com.ntxm.smart.R;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okio.AsyncTimeout;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {

    private final AsyncTimeout timeout = new AsyncTimeout() {
        @Override
        protected void timedOut() {
            // 取消定时
        }
    };
    // 登录按钮
    private TextView mLoginBtn;
    // 快捷登录 Tab
    private TextView mQuickLoginBtn;
    // 密码登录 Tab
    private TextView mPasswordLoginBtn;
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
    private LoginPresenter mLoginPresenter;
    private int count = 60;

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (StringUtils.isNotEmpty(SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
            return;
        }
        super.onCreate(savedInstanceState);
        this.mLoginPresenter = new LoginPresenter();
        this.mLoginPresenter.attachView(this); // Presenter内部持有一个Activity对象
        this.mLoginBtn = this.findViewById(R.id.btn_login);
        this.mLoginBtn.setOnClickListener(this);
        this.mQuickLoginBtn = this.findViewById(R.id.quick_login_btn);
        this.mQuickLoginBtn.setOnClickListener(this);
        this.mPasswordLoginBtn = this.findViewById(R.id.password_login_btn);
        this.mPasswordLoginBtn.setOnClickListener(this);
        this.mForgetPwdBtn = this.findViewById(R.id.forget_password);
        this.mForgetPwdBtn.setOnClickListener(this);
        this.mLoginAccountEditText = this.findViewById(R.id.login_account);
        this.mLoginPwdEditText = this.findViewById(R.id.login_pwd);
        this.mLoginQrEditText = this.findViewById(R.id.login_qr_code);
        this.mLoginNumberEditText = this.findViewById(R.id.login_number_code);
        this.switchLoginWay(true);
        this.mLoginNumberEditText.getRightTextView().setOnClickListener(this);
        this.mLoginQrEditText.setOnRightClickListener(v -> this.mLoginPresenter.requestQrCode());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mLoginPresenter.detachView();
    }

    @Override
    public void onClick(View v) {
        if (v == this.mLoginBtn) {
//            boolean isQuickLogin = !this.mQuickLoginBtn.isEnabled();
//            String username = this.mLoginAccountEditText.getText().trim();
//
//            if (StringUtils.isEmpty(username) || (isQuickLogin && !username.matches("1[0-9]{10}"))) {
//                EventBus.getDefault().post(new ToastEvent("账户不符和条件"));
//                return;
//            }
//
//            if (!isQuickLogin && StringUtils.isEmpty(this.mLoginQrEditText.getText())) {
//                BaseActivity.showToast("图形验证为空");
//                return;
//            }
//
//            String password = isQuickLogin ? this.mLoginNumberEditText.getText().trim() : this.mLoginPwdEditText.getText().trim();
//
//            if (StringUtils.isEmpty(password)) {
//                BaseActivity.showToast(isQuickLogin ? "验证码为空" : "密码为空");
//                return;
//            }
//
//            String qrCode = this.mLoginQrEditText.getText().trim();
//            Map<String, String> params = new HashMap<>();
//            params.put(isQuickLogin ? "mobile" : "username", username);
//            params.put(isQuickLogin ? "code" : "password", password);
//            if (!isQuickLogin) {
//                params.put("code", qrCode);
//                params.put(KeyConstant.KEY_QR_IMAGE_TOKEN, SPUtils.getStringValue(KeyConstant.KEY_QR_IMAGE_TOKEN));
//            }
//            this.mLoginPresenter.loginAccount(params);
            this.startActivity(new Intent(this, MainActivity.class));
        } else if (v == this.mQuickLoginBtn) {
            this.switchLoginWay(false);
        } else if (v == this.mPasswordLoginBtn) {
            this.switchLoginWay(true);
        } else if (v == this.mForgetPwdBtn) {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("action", "1");
            this.startActivity(intent);
        } else if (v == this.mLoginNumberEditText.getRightTextView()) {
            if (this.count != 60) {
                return;
            }

            String mobile = this.mLoginAccountEditText.getText().trim();
            if (StringUtils.isEmpty(mobile) || !mobile.matches("1[0-9]{10}")) {
                BaseActivity.showToast("手机号格式不正确");
                return;
            }

            if (StringUtils.isEmpty(this.mLoginQrEditText.getText())) {
                BaseActivity.showToast("图形验证码为空");
                return;
            }
            this.mLoginPresenter.requestSms(this.mLoginAccountEditText.getText().trim(), this.mLoginQrEditText.getText().trim());

            Timer timer = new Timer();
            this.timeout.timeout(60, TimeUnit.SECONDS);
            this.timeout.enter();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    LoginActivity.this.invoke(() -> {
                        LoginActivity.this.mLoginNumberEditText.setRightText((LoginActivity.this.count--) + " 秒");
                        if (LoginActivity.this.count == 0) {
                            LoginActivity.this.mLoginNumberEditText.setRightText("重新获取");
                            LoginActivity.this.count = 60;
                            this.cancel();
                        }
                    });
                }
            }, 0, 1000);
        } else {
            super.onClick(v);
        }
    }

    /**
     * 切换登录方式
     *
     * @param pwdLogin 密码登录
     */
    private void switchLoginWay(boolean pwdLogin) {
        this.mPasswordLoginBtn.setEnabled(!pwdLogin);
        this.mQuickLoginBtn.setEnabled(pwdLogin);
        this.mLoginPwdEditText.setVisibility(pwdLogin ? View.VISIBLE : View.GONE);
        this.mLoginNumberEditText.setVisibility(pwdLogin ? View.GONE : View.VISIBLE);
        this.mForgetPwdBtn.setVisibility(pwdLogin ? View.VISIBLE : View.GONE);
        this.mLoginAccountEditText.getEditText().setHint(pwdLogin ? "输入用户名" : "请输入手机号或者身份证号");
        this.mLoginAccountEditText.getEditText().setInputType(pwdLogin ?
                InputType.TYPE_CLASS_TEXT : InputType.TYPE_CLASS_PHONE);
        this.mLoginAccountEditText.clearText();
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.line_blue);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        this.mPasswordLoginBtn.setCompoundDrawables(null, null, null, pwdLogin ? drawable : null);
        this.mQuickLoginBtn.setCompoundDrawables(null, null, null, pwdLogin ? null : drawable);
        if (pwdLogin) {
            this.mLoginNumberEditText.clearText();
        } else {
            this.mLoginPwdEditText.clearText();
        }

        this.mLoginPresenter.requestQrCode();
        this.mLoginQrEditText.clearText();
    }

    @Override
    public void onResponse(ResponseEvent event) {
        String url = event.getUrl();
        if (url.equals(UrlPath.PATH_PICTURE_VERIFY.getUrl())) {
            JSONObject dataObject = parseObject(event.getData());
            String bitmapStr = dataObject.getString("kaptcha");
            this.invoke(() -> this.mLoginQrEditText.getRightTextView().setBackgroundDrawable(new BitmapDrawable(this.getResources(), BitmapUtils.stringToBitmap(bitmapStr))));
        } else {
            super.onResponse(event);
        }
    }

    @Override
    public void showLoading() {
        // 暂时不确定UI这边显示
    }

    @Override
    public void hideLoading() {
        // 此处暂时不需要处理
    }

    @Override
    public void onError(@NotNull Throwable throwable) {
        LogTool.printE(this.mTag, "onError: MyFirstMvpLog: %s", Log.getStackTraceString(throwable));
    }

    @Override
    public void onError(@NotNull String err) {
        EventBus.getDefault().post(new ToastEvent(err));
    }

    @Override
    public void onLoginState(int state) {
        if (state == 1) {
            this.startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    @UiThread
    public void onQrCode(Bitmap bitmap) {
        this.runOnUiThread(() -> this.mLoginQrEditText.getRightTextView().setBackgroundDrawable(new BitmapDrawable(this.getResources(), bitmap)));
    }

    @Override
    public void showQrCode(Bitmap bitmap) {

    }
}