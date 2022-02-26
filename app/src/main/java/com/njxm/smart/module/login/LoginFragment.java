/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.module.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.njxm.smart.activities.login.LoginData;
import com.njxm.smart.base.BaseFragment;
import com.njxm.smart.contract.LoginContract;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.presenter.LoginPresenter;
import com.njxm.smart.ui.activities.ResetPasswordActivity;
import com.njxm.smart.ui.activities.main.MainActivity;
import com.njxm.smart.utils.RegexUtil;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.view.AppEditText;
import com.ntxm.smart.R;
import com.ntxm.smart.databinding.ActivityLoginBinding;
import com.smart.cloud.utils.ToastUtils;
import com.sugar.android.common.utils.Logger;
import com.sugar.android.common.utils.StringUtils;
import com.sugar.android.common.utils.TextViewUtils;
import com.sugar.android.common.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okio.AsyncTimeout;

/**
 * 登录页面
 */
public class LoginFragment extends BaseFragment implements LoginContract.View, View.OnClickListener {
    private static final String TAG = "LoginFragment";
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

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = ActivityLoginBinding.inflate(inflater).getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (quickStartIfToken()) {
            return;
        }
        super.onCreate(savedInstanceState);
        initPresenter();
        findView();
        this.mLoginNumberEditText.getRightTextView().setOnClickListener(this);
        this.mLoginQrEditText.setOnRightClickListener(v -> this.mLoginPresenter.requestQrCode());
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.i(TAG, "onResume");
        this.switchLoginWay(true);
    }

    private void initPresenter() {
        this.mLoginPresenter = new LoginPresenter();
        this.mLoginPresenter.attachView(this); // Presenter内部持有一个Activity对象
    }

    private void findView() {
        initView();
    }

    private View lineIndicator;

    void initView() {
        this.mLoginBtn = ViewUtils.findViewById(rootView, R.id.btn_login);
        ViewUtils.setOnClickListener(mLoginBtn, this);
        this.mQuickLoginBtn = ViewUtils.findViewById(rootView, R.id.quick_login_btn);
        ViewUtils.setOnClickListener(mQuickLoginBtn, this);
        this.mPasswordLoginBtn = ViewUtils.findViewById(rootView, R.id.password_login_btn);
        ViewUtils.setOnClickListener(mPasswordLoginBtn, this);
        this.mForgetPwdBtn = ViewUtils.findViewById(rootView, R.id.forget_password);
        ViewUtils.setOnClickListener(mForgetPwdBtn, this);
        this.mLoginAccountEditText = ViewUtils.findViewById(rootView, R.id.login_account);
        this.mLoginPwdEditText = ViewUtils.findViewById(rootView, R.id.login_pwd);
        this.mLoginQrEditText = ViewUtils.findViewById(rootView, R.id.login_qr_code);
        this.mLoginNumberEditText = ViewUtils.findViewById(rootView, R.id.login_number_code);
        lineIndicator = ViewUtils.findViewById(rootView, R.id.tab_indicator);
    }

    private boolean quickStartIfToken() {
        if (StringUtils.isNotEmpty(SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            this.startActivity(intent);
            finishActivity();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        }
    }

    private void doForget() {
        Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
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
                mLoginNumberEditText.post(() -> {
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
//        boolean isQuickLogin = !this.mQuickLoginBtn.isEnabled();
//        if (isQuickLogin) {
//            quickLogin();
//        } else {
//            passwordLogin();
//        }


        AnimatorSet set = new AnimatorSet();
        Animator translateX = ObjectAnimator.ofFloat(mLoginBtn, "translationX", -mLoginBtn.getWidth(), 0);
        translateX.setInterpolator(new DecelerateInterpolator());
        translateX.setDuration(5000);


        final View viewById = ViewUtils.findViewById(rootView, R.id.num_tv);
        ViewUtils.setVisibility(viewById, false);


        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(320);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewUtils.setVisibility(viewById, true);
                viewById.setPivotX(viewById.getX());
                viewById.setPivotY(viewById.getY());
                AnimatorSet scaleSet = new AnimatorSet();
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(viewById, "scaleX", 0f, 1f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(viewById, "scaleY", 0f, 1f);
                int scaleDuration = 500;
                scaleX.setDuration(scaleDuration);
                scaleY.setDuration(scaleDuration);
                scaleX.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }
                });
                scaleSet.playTogether(scaleX, scaleY);
                scaleSet.start();
            }
        });
        set.playTogether(translateX, valueAnimator);
        set.start();
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
        if (pwdLogin) {
            refreshUIWithPwdLogin();
        } else {
            refreshUIWithQuickLogin();
        }


        TextViewUtils.setText(mLoginAccountEditText.getEditText(), pwdLogin ? R.string.input_username :
                R.string.input_phone_or_idcard);
        this.mLoginAccountEditText.getEditText().setInputType(pwdLogin ?
                InputType.TYPE_CLASS_TEXT : InputType.TYPE_CLASS_PHONE);
        this.mLoginAccountEditText.clearText();
        float toX;
        float width;
        int viewId;


        if (pwdLogin) {
            viewId = R.id.password_login_btn;
            toX = mPasswordLoginBtn.getX();
            width = mPasswordLoginBtn.getWidth();
            this.mLoginNumberEditText.clearText();
        } else {
            viewId = R.id.quick_login_btn;
            toX = mQuickLoginBtn.getX();
            width = mQuickLoginBtn.getWidth();
            this.mLoginPwdEditText.clearText();
        }

        int lineIndicatorWidth = lineIndicator.getWidth();
        if (lineIndicatorWidth == 0) {
            ConstraintSet set = new ConstraintSet();
            set.setForceId(false);
            set.clone((ConstraintLayout) rootView);
            set.connect(R.id.tab_indicator, ConstraintSet.START, viewId, ConstraintSet.START);
            set.connect(R.id.tab_indicator, ConstraintSet.END, viewId, ConstraintSet.END);
            set.applyTo((ConstraintLayout) rootView);
        } else {
            AnimatorSet set1 = new AnimatorSet();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(lineIndicator, "scaleX", 1f,
                    width / lineIndicatorWidth);
            scaleX.setDuration(1);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(lineIndicator, "translationX", lineIndicator.getX(),
                    toX + (width - lineIndicatorWidth) / 2);
            objectAnimator.setDuration(300);
            objectAnimator.start();
            set1.play(scaleX).before(objectAnimator);
//        set.play(objectAnimator);
            set1.start();
        }


//        refreshQrCode();
        this.mLoginQrEditText.clearText();
    }

    private void refreshUIWithPwdLogin() {
        ViewUtils.setEnable(mPasswordLoginBtn, false);
        ViewUtils.setEnable(mPasswordLoginBtn, true);
        ViewUtils.setVisibility(mLoginPwdEditText, true);
        ViewUtils.setVisibility(mLoginNumberEditText, false);
        ViewUtils.setVisibility(mForgetPwdBtn, true);
    }

    private void refreshUIWithQuickLogin() {
        ViewUtils.setEnable(mPasswordLoginBtn, true);
        ViewUtils.setEnable(mQuickLoginBtn, false);
        ViewUtils.setVisibility(mLoginPwdEditText, false);
        ViewUtils.setVisibility(mLoginNumberEditText, true);
        ViewUtils.setVisibility(mForgetPwdBtn, false);
    }

    /**
     * 请求图片验证码
     */
    private void requestPictureCode() {
        this.mLoginPresenter.requestQrCode();
    }

    //    @Override
//    public void onResponse(ResponseEvent event) {
//        String url = event.getUrl();
//        if (url.equals(UrlPath.PATH_PICTURE_VERIFY.getUrl())) {
//            JSONObject dataObject = parseObject(event.getData());
//            String bitmapStr = dataObject.getString("kaptcha");
//            this.invoke(() -> this.mLoginQrEditText.getRightTextView().setBackgroundDrawable(new BitmapDrawable(this.getResources(), BitmapUtils.stringToBitmap(bitmapStr))));
//        } else {
//            super.onResponse(event);
//        }
//    }

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
    }

    @Override
    public void onError(@NotNull String err) {
        EventBus.getDefault().post(new ToastEvent(err));
    }

    @Override
    public void onLoginState(int state) {
        if (state == 1) {
            this.startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }

    @Override
    @UiThread
    public void onQrCode(Bitmap bitmap) {
        mLoginAccountEditText.post(() -> {
            this.mLoginQrEditText.getRightTextView().setBackgroundDrawable(new BitmapDrawable(this.getResources(),
                    bitmap));
        });
    }

    @Override
    public void showQrCode(Bitmap bitmap) {

    }
}