package com.njxm.smart.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.contract.LoginContract;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.presenter.LoginPresenter;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.AppEditText;
import com.ntxm.smart.R;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {

    // 登录按钮
    private TextView mLoginBtn;

    // 快捷登录 Tab
    private TextView mQuickLoginBtn;

    // 密码登录 Tab
    private TextView mPasswordLoginBtn;

    // 快捷登录和密码登录下方的下划线
    private View mQuickLoginDivider;
    private View mPasswordLoginDivider;

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

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (StringUtils.isNotEmpty(SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(this); // Presenter内部持有一个Activity对象

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

        mLoginNumberEditText.getRightTextView().setOnClickListener(this);

        mLoginQrEditText.setOnRightClickListener(v -> mLoginPresenter.requestQrCode());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginBtn) {
            boolean isQuickLogin = !mQuickLoginBtn.isEnabled();
            String username = mLoginAccountEditText.getText().trim();

            if (StringUtils.isEmpty(username) || (isQuickLogin && !username.matches("1[0-9]{10}"))) {
                EventBus.getDefault().post(new ToastEvent("账户不符和条件"));
                return;
            }

            if (!isQuickLogin && StringUtils.isEmpty(mLoginQrEditText.getText())) {
                showToast("图形验证为空");
                return;
            }

            String password = isQuickLogin ? mLoginNumberEditText.getText().trim() : mLoginPwdEditText.getText().trim();

            if (StringUtils.isEmpty(password)) {
                showToast(isQuickLogin ? "验证码为空" : "密码为空");
                return;
            }

            String qrCode = mLoginQrEditText.getText().trim();
            Map<String, String> params = new HashMap<>();
            params.put(isQuickLogin ? "mobile" : "username", username);
            params.put(isQuickLogin ? "code" : "password", password);
            if (!isQuickLogin) {
                params.put("code", qrCode);
                params.put(KeyConstant.KEY_QR_IMAGE_TOKEN, SPUtils.getStringValue(KeyConstant.KEY_QR_IMAGE_TOKEN));
            }
            mLoginPresenter.loginAccount(params);
        } else if (v == mQuickLoginBtn) {
            switchLoginWay(false);
        } else if (v == mPasswordLoginBtn) {
            switchLoginWay(true);
        } else if (v == mForgetPwdBtn) {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("action", "1");
            startActivity(intent);
        } else if (v == mLoginNumberEditText.getRightTextView()) {
            if (count != 60) {
                return;
            }

            String mobile = mLoginAccountEditText.getText().trim();
            if (StringUtils.isEmpty(mobile) || !mobile.matches("1[0-9]{10}")) {
                showToast("手机号格式不正确");
                return;
            }

            if (StringUtils.isEmpty(mLoginQrEditText.getText())) {
                showToast("图形验证码为空");
                return;
            }
            mLoginPresenter.requestSms(mLoginAccountEditText.getText().trim(), mLoginQrEditText.getText().trim());

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    invoke(() -> {
                        mLoginNumberEditText.setRightText((count--) + " 秒");
                        if (count == 0) {
                            mLoginNumberEditText.setRightText("重新获取");
                            count = 60;
                            cancel();
                        }
                    });
                }
            }, 0, 1000);
        } else {
            super.onClick(v);
        }
    }

    private int count = 60;

    /**
     * 切换登录方式
     *
     * @param pwdLogin 密码登录
     */
    private void switchLoginWay(boolean pwdLogin) {
        mPasswordLoginBtn.setEnabled(!pwdLogin);
        mQuickLoginBtn.setEnabled(pwdLogin);
        mPasswordLoginDivider.setVisibility(pwdLogin ? View.VISIBLE : View.INVISIBLE);
        mQuickLoginDivider.setVisibility(pwdLogin ? View.INVISIBLE : View.VISIBLE);
        mLoginPwdEditText.setVisibility(pwdLogin ? View.VISIBLE : View.GONE);
        mLoginNumberEditText.setVisibility(pwdLogin ? View.GONE : View.VISIBLE);
        mForgetPwdBtn.setVisibility(pwdLogin ? View.VISIBLE : View.GONE);
        mLoginAccountEditText.getEditText().setHint(pwdLogin ? "输入用户名" : "请输入手机号或者身份证号");
        mLoginAccountEditText.getEditText().setInputType(pwdLogin ?
                InputType.TYPE_CLASS_TEXT : InputType.TYPE_CLASS_PHONE);
        mLoginAccountEditText.clearText();
        if (pwdLogin) {
            mLoginNumberEditText.clearText();
        } else {
            mLoginPwdEditText.clearText();
        }

        mLoginPresenter.requestQrCode();
        mLoginQrEditText.clearText();
    }

    @Override
    public void onResponse(ResponseEvent event) {

        final String url = event.getUrl();

        if (url.equals(UrlPath.PATH_PICTURE_VERIFY.getUrl())) {
            JSONObject dataObject = parseObject(event.getData());
            final String bitmapStr = dataObject.getString("kaptcha");

            invoke(() -> mLoginQrEditText.getRightTextView().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapUtils.stringToBitmap(bitmapStr))));
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
        LogTool.printE(TAG, "onError: MyFirstMvpLog: %s", Log.getStackTraceString(throwable));
    }

    @Override
    public void onError(@NotNull String err) {
        EventBus.getDefault().post(new ToastEvent(err));
    }

    @Override
    public void onLoginState(int state) {
        if (state == 1) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    @UiThread
    public void onQrCode(Bitmap bitmap) {
        runOnUiThread(() -> mLoginQrEditText.getRightTextView().setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap)));
    }
}