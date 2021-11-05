/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities.login;

import static com.alibaba.fastjson.JSON.parseObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
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
import com.njxm.smart.ui.activities.ActionBarItem;
import com.njxm.smart.ui.activities.BaseActivity;
import com.njxm.smart.ui.activities.ResetPasswordActivity;
import com.njxm.smart.ui.activities.main.MainActivity;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.RegexUtil;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.utils.ViewUtils;
import com.njxm.smart.view.AppEditText;
import com.ntxm.smart.R;
import com.smart.cloud.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okio.AsyncTimeout;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {
    /**
     * 倒计时 60s
     */
    private static final int COUNT_TIME = 60;
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
    private int count = COUNT_TIME;

    private LoginData loginData = new LoginData();

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected ActionBarItem getActionBarItem() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (quickStartIfToken()) {
            return;
        }
        super.onCreate(savedInstanceState);
        initPresenter();
        findView();
        this.switchLoginWay(true);
        this.mLoginNumberEditText.getRightTextView().setOnClickListener(this);
        this.mLoginQrEditText.setOnRightClickListener(v -> this.mLoginPresenter.requestQrCode());
    }

    private void initPresenter() {
        this.mLoginPresenter = new LoginPresenter();
        this.mLoginPresenter.attachView(this); // Presenter内部持有一个Activity对象
    }

    private void findView() {
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
    }

    private boolean quickStartIfToken() {
        if (StringUtils.isNotEmpty(SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mLoginPresenter.detachView();
    }

    @Override
    public void onClick(View v) {
        if (v == this.mLoginBtn) {
            doLogin();
        } else if (v == this.mQuickLoginBtn) {
            this.switchLoginWay(false);
        } else if (v == this.mPasswordLoginBtn) {
            this.switchLoginWay(true);
        } else if (v == this.mForgetPwdBtn) {
            doForget();
        } else if (v == this.mLoginNumberEditText.getRightTextView()) {
            requestVerifyCode();
        } else {
            super.onClick(v);
        }
    }

    private void doForget() {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra("action", "1");
        this.startActivity(intent);
    }

    private void requestVerifyCode() {
        if (this.count != COUNT_TIME) {
            return;
        }

        String mobile = this.mLoginAccountEditText.getText().trim();
        if (!RegexUtil.isMobilePhone(mobile)) {
            return;
        }

        if (StringUtils.isEmpty(this.mLoginQrEditText.getText())) {
            return;
        }
        this.mLoginPresenter.requestSms(this.mLoginAccountEditText.getText().trim(), this.mLoginQrEditText.getText().trim());

        reset();
    }

    private void reset() {
        Timer timer = new Timer();
        this.timeout.timeout(3, TimeUnit.SECONDS);
        this.timeout.enter();
        timer.schedule(new TimerTask() {
            int count = COUNT_TIME;

            @Override
            public void run() {
                LoginActivity.this.invoke(() -> {
                    count -= 1;
                    mLoginNumberEditText.setRightText(count + " 秒");
                    if (count == 0) {
                        mLoginNumberEditText.setRightText("重新获取");
                        this.cancel();
                    }
                });
            }
        }, 0, 1000);
    }

    public int add(int a, int b) {
        return a + b;
    }

    private void doLogin() {
        boolean isQuickLogin = !this.mQuickLoginBtn.isEnabled();
        if (isQuickLogin) {
            quickLogin();
        } else {
            passwordLogin();
        }
    }

    private void quickLogin() {
        String phoneNumber = this.mLoginAccountEditText.getText().trim();
        String verifyCode = this.mLoginNumberEditText.getText().trim();

        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(verifyCode)) {
            ToastUtils.showToast(getString(R.string.null_mobile_or_verify_code));
            return;
        }
        loginData.setMobile(phoneNumber);
        loginData.setVerifyCode(verifyCode);
        this.mLoginPresenter.loginAccount(loginData.getRequestParams());
    }

    private void passwordLogin() {
        String username = this.mLoginAccountEditText.getText().trim();
        String password = this.mLoginPwdEditText.getText().trim();
        String qrCode = this.mLoginQrEditText.getText().trim();

        if (checkParam(username, password, qrCode)) {
            ToastUtils.showToast("用户名、密码、校验码不能为空");
            return;
        }

        loginData.setUsername(username);
        loginData.setPassword(password);
        loginData.setVerifyCode(qrCode);
        loginData.setToken(SPUtils.getStringValue(KeyConstant.KEY_QR_IMAGE_TOKEN));
        this.mLoginPresenter.loginAccount(loginData.getRequestParams());
    }

    private boolean checkParam(String... param) {
        for (String s : param) {
            if (TextUtils.isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    private String checkPassword(boolean isQuickLogin) {
        String password = isQuickLogin ? this.mLoginNumberEditText.getText().trim() : this.mLoginPwdEditText.getText().trim();
        if (StringUtils.isEmpty(password)) {
            return null;
        }
        return password;
    }

    private boolean checkUserName(boolean isQuickLogin, String mobile) {
        if (isQuickLogin && !RegexUtil.isMobilePhone(mobile)) {
            EventBus.getDefault().post(new ToastEvent("账户不符和条件"));
            return true;
        }
        return false;
    }

    /**
     * 切换登录方式
     *
     * @param pwdLogin 密码登录
     */
    private void switchLoginWay(boolean pwdLogin) {
        this.mPasswordLoginBtn.setEnabled(!pwdLogin);
        this.mQuickLoginBtn.setEnabled(pwdLogin);
        ViewUtils.setVisibility(mLoginPwdEditText, pwdLogin);
        ViewUtils.setVisibility(mLoginNumberEditText, !pwdLogin);
        ViewUtils.setVisibility(mForgetPwdBtn, pwdLogin);
        ViewUtils.setHint(mLoginAccountEditText.getEditText(), pwdLogin ? R.string.input_username :
                R.string.input_phone_or_idcard);
        this.mLoginAccountEditText.getEditText().setInputType(pwdLogin ?
                InputType.TYPE_CLASS_TEXT : InputType.TYPE_CLASS_PHONE);
        this.mLoginAccountEditText.clearText();
        this.mPasswordLoginBtn.setCompoundDrawables(null, null, null, pwdLogin ? getLineDrawable() : null);
        this.mQuickLoginBtn.setCompoundDrawables(null, null, null, pwdLogin ? null : getLineDrawable());
        if (pwdLogin) {
            this.mLoginNumberEditText.clearText();
        } else {
            this.mLoginPwdEditText.clearText();
        }

        refreshQrCode();
        this.mLoginQrEditText.clearText();
    }

    private void refreshQrCode() {
        this.mLoginPresenter.requestQrCode();
    }

    private Drawable getLineDrawable() {
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.line_blue);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        return drawable;
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