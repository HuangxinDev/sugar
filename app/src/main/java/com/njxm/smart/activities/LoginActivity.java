package com.njxm.smart.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.AlertDialogUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.AppEditText;
import com.ns.demo.R;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, HttpCallBack {

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

        if (StringUtils.isNotEmpty(SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

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

        mLoginQrEditText.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtils.getInstance().postDataWithParams(HttpUtils.REQUEST_QR, HttpUrlGlobal.HTTP_QR_URL, null,
                        LoginActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mLoginBtn) {
            boolean isQuickLogin = !mQuickLoginBtn.isEnabled();
            String username = mLoginAccountEditText.getText().trim();
            String password;
            String qrCode = mLoginQrEditText.getText().trim();
            String url;
            Map<String, String> urlParams = new HashMap<>();
            if (!isQuickLogin) {
                url = HttpUrlGlobal.HTTP_USER_LOGIN_URL;
                password = mLoginPwdEditText.getText().trim();
                urlParams.put("username", username);
                urlParams.put("password", password);
                urlParams.put("code", qrCode);
                urlParams.put(KeyConstant.KEY_QR_IMAGE_TOKEN, SPUtils.getStringValue(KeyConstant.KEY_QR_IMAGE_TOKEN));
            } else {
                url = HttpUrlGlobal.HTTP_MOBILE_LOGIN_URL;
                password = mLoginNumberEditText.getText().trim();
                urlParams.put("mobile", username);
                urlParams.put("code", password);
            }

            if (StringUtils.isEmpty(username) || (isQuickLogin && !username.matches("1[0-9]{10}"))) {
                EventBus.getDefault().post(new ToastEvent("账户不符和条件"));
                return;
            }

            if (!isQuickLogin && StringUtils.isEmpty(mLoginQrEditText.getText())) {
                showToast("图形验证为空");
                return;
            }

            if (StringUtils.isEmpty(password)) {
                showToast(isQuickLogin ? "验证码为空" : "密码为空");
                return;
            }

            HttpUtils.getInstance().postDataWithParams(HttpUtils.REQUEST_LOGIN, url, urlParams, this);
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

            HashMap<String, String> params = new HashMap<>();
            params.put(KeyConstant.KEY_QR_IMAGE_TOKEN, SPUtils.getStringValue(KeyConstant.KEY_QR_IMAGE_TOKEN));
            params.put("code", mLoginQrEditText.getText().trim());
            params.put("mobile", mLoginAccountEditText.getText().trim());
            HttpUtils.getInstance().postDataWithBody(HttpUtils.REQUEST_SMS,
                    HttpUrlGlobal.HTTP_SMS_URL, params, null);

            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    invoke(new Runnable() {
                        @Override
                        public void run() {
                            mLoginNumberEditText.setRightText((count--) + " 秒");
                            if (count == 0) {
                                mLoginNumberEditText.setRightText("重新获取");
                                count = 60;
                                cancel();
                            }
                        }
                    });
                }
            }, 0, 1000);


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
        HttpUtils.getInstance().postDataWithParams(HttpUtils.REQUEST_QR, HttpUrlGlobal.HTTP_QR_URL, null, this);
        mLoginQrEditText.clearText();
    }

    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        super.onSuccess(requestId, success, code, data);
        if (StringUtils.isEmpty(data)) {
            return;
        }

        JSONObject dataObject = JSONObject.parseObject(data);
        if (requestId == HttpUtils.REQUEST_QR) {
            if (success) {
                final String bitmapStr = dataObject.getString("kaptcha");
                SPUtils.putValue(KeyConstant.KEY_QR_IMAGE_TOKEN, dataObject.getString("kaptchaToken"));
                invoke(new Runnable() {
                    @Override
                    public void run() {
                        mLoginQrEditText.getRightTextView().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapUtils.stringToBitmap(bitmapStr)));
                    }
                });
            }
        } else if (requestId == HttpUtils.REQUEST_LOGIN) {

            SPUtils.putValue("login_message", data);
            SPUtils.putValue(KeyConstant.KEY_USER_ID, dataObject.getString(KeyConstant.KEY_USER_ID));
            SPUtils.putValue(KeyConstant.KEY_USER_TOKEN, dataObject.getString(KeyConstant.KEY_USER_TOKEN));
            SPUtils.putValue(KeyConstant.KEY_USER_ACCOUNT, dataObject.getString(KeyConstant.KEY_USER_ACCOUNT));
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onFailed(String errMsg) {
        showDialog();
    }

    /**
     * 使用错误Dialog
     */
    private void showDialog() {
        invoke(new Runnable() {
            @Override
            public void run() {
                AlertDialogUtils.getInstance().showConfirmDialog(LoginActivity.this,
                        "账号或密码输入错误", "忘记密码", "重新输入", new AlertDialogUtils.OnButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(AlertDialog dialog) {
                                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                                intent.putExtra("action", "1");
                                startActivity(intent);
                                dialog.dismiss();
                            }

                            @Override
                            public void onNegativeButtonClick(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        }
                );
            }
        });
    }
}