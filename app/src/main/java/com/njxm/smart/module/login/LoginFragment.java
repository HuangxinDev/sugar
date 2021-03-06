/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.module.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
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
import com.njxm.smart.view.AppEditText;
import com.ntxm.smart.R;
import com.ntxm.smart.databinding.FragmentLoginBinding;
import com.smart.cloud.utils.ToastUtils;
import com.sugar.android.common.utils.ActivityUtils;
import com.sugar.android.common.utils.HandlerUtils;
import com.sugar.android.common.utils.Logger;
import com.sugar.android.common.utils.SPUtils;
import com.sugar.android.common.utils.StringUtils;
import com.sugar.android.common.utils.TextViewUtils;
import com.sugar.android.common.utils.ThreadPoolUtils;
import com.sugar.android.common.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

/**
 * 登录页面
 */
public class LoginFragment extends BaseFragment implements LoginContract.View, View.OnClickListener {
    private static final String TAG = "LoginFragment";

    /**
     * 倒计时 60s
     */
    private static final int COUNT_TIME = 60;

    // 账号验证布局
    private AppEditText mLoginAccountEditText;

    // 密码验证布局
    private AppEditText mLoginPwdEditText;

    // 图形验证布局
    private AppEditText mLoginQrEditText;

    // 验证码登录
    private AppEditText mLoginNumberEditText;

    private LoginPresenter mLoginPresenter;

    private int count = COUNT_TIME;

    private final LoginData loginData = new LoginData();

    private FragmentLoginBinding layoutBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = FragmentLoginBinding.inflate(inflater);
        return layoutBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        initPresenter();
        initView();
    }

    private void initPresenter() {
        this.mLoginPresenter = new LoginPresenter();
        this.mLoginPresenter.attachView(this); // Presenter内部持有一个Activity对象
    }

    void initView() {
        ViewUtils.setOnClickListener(layoutBinding.btnLogin, this);
        ViewUtils.setOnClickListener(layoutBinding.quickLoginTabBtn, this);
        ViewUtils.setOnClickListener(layoutBinding.passwordLoginTabBtn, this);
        ViewUtils.setOnClickListener(layoutBinding.forgetPassword, this);
        this.mLoginAccountEditText = ViewUtils.findViewById(layoutBinding.getRoot(), R.id.login_account);
        this.mLoginPwdEditText = ViewUtils.findViewById(layoutBinding.getRoot(), R.id.login_pwd);
        this.mLoginQrEditText = ViewUtils.findViewById(layoutBinding.getRoot(), R.id.login_qr_code);
        this.mLoginNumberEditText = ViewUtils.findViewById(layoutBinding.getRoot(), R.id.login_number_code);
        ViewUtils.setOnClickListener(mLoginQrEditText.getRightTextView(), v -> mLoginPresenter.requestQrCode());
        ViewUtils.setOnClickListener(mLoginNumberEditText.getRightTextView(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.i(TAG, "onResume");
        switchLoginWay(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mLoginPresenter != null) {
            mLoginPresenter.detachView();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.quick_login_tab_btn:
                switchLoginWay(false);
                break;
            case R.id.password_login_tab_btn:
                switchLoginWay(true);
                break;
            case R.id.forget_password:
                gotoResetPasswordActivity();
                break;
            default:
                break;
        }
        if (v == mLoginAccountEditText.getRightTextView()) {
            requestVerifyCode();
        }
    }

    private void gotoResetPasswordActivity() {
        Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
        intent.putExtra("action", "1");
        ActivityUtils.startActivity(getActivity(), intent);
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
        ThreadPoolUtils.schedulePeriodTask(() -> {
            count--;
            HandlerUtils.postToMain(() -> TextViewUtils.setText(mLoginAccountEditText.getRightTextView(), count == 0 ? "重新获取" : count + " 秒"));
        }, 1000, 1000);
    }

    private void doLogin() {
        boolean isQuickLogin = !layoutBinding.quickLoginTabBtn.isEnabled();
        if (isQuickLogin) {
            quickLogin();
        } else {
            passwordLogin();
        }
        getActivity().getOnBackPressedDispatcher().onBackPressed();
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
            viewId = R.id.password_login_tab_btn;
            toX = layoutBinding.passwordLoginTabBtn.getX();
            width = layoutBinding.passwordLoginTabBtn.getWidth();
            this.mLoginNumberEditText.clearText();
        } else {
            viewId = R.id.quick_login_tab_btn;
            toX = layoutBinding.quickLoginTabBtn.getX();
            width = layoutBinding.quickLoginTabBtn.getWidth();
            this.mLoginPwdEditText.clearText();
        }
        int lineIndicatorWidth = layoutBinding.btnTabIndicator.getWidth();
        if (lineIndicatorWidth == 0) {
            ConstraintSet set = new ConstraintSet();
            set.setForceId(false);
            set.clone(layoutBinding.getRoot());
            set.connect(R.id.btn_tab_indicator, ConstraintSet.START, viewId, ConstraintSet.START);
            set.connect(R.id.btn_tab_indicator, ConstraintSet.END, viewId, ConstraintSet.END);
            set.applyTo(layoutBinding.getRoot());
        } else {
            AnimatorSet set1 = new AnimatorSet();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(layoutBinding.btnTabIndicator, "scaleX", 1f,
                    width / lineIndicatorWidth);
            scaleX.setDuration(1);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layoutBinding.btnTabIndicator, "translationX",
                    layoutBinding.btnTabIndicator.getX(),
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
        ViewUtils.setEnable(layoutBinding.passwordLoginTabBtn, false);
        ViewUtils.setEnable(layoutBinding.quickLoginTabBtn, true);
        ViewUtils.setVisibility(mLoginPwdEditText, true);
        ViewUtils.setVisibility(mLoginNumberEditText, false);
        ViewUtils.setVisibility(layoutBinding.forgetPassword, true);
    }

    private void refreshUIWithQuickLogin() {
        ViewUtils.setEnable(layoutBinding.passwordLoginTabBtn, true);
        ViewUtils.setEnable(layoutBinding.quickLoginTabBtn, false);
        ViewUtils.setVisibility(mLoginPwdEditText, false);
        ViewUtils.setVisibility(mLoginNumberEditText, true);
        ViewUtils.setVisibility(layoutBinding.forgetPassword, false);
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
        HandlerUtils.postToMain(() -> {
            //            ViewUtils.setImageDrawable(mLoginQrEditText.getRightTextView(), new BitmapDrawable(this.getResources(),
            //                    bitmap));
        });
    }

    @Override
    public void showQrCode(Bitmap bitmap) {
    }
}