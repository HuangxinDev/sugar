package com.njxm.smart.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.AlertDialogUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.AppEditText;
import com.ns.demo.R;

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

        if (StringUtils.isNotEmpty(SPUtils.getStringValue("token"))) {
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


    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpUtils.getInstance().postDataWithParams(HttpUtils.REQUEST_QR, HttpUrlGlobal.HTTP_QR_URL, null,
                HttpUtils.MimeType.JSON,
                this);
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginBtn) {
            boolean isQuickLogin = !mQuickLoginBtn.isEnabled();
            String username = mLoginAccountEditText.getText().trim();
            String qrCode = mLoginQrEditText.getText().trim();
            String msgCode;
            String password;
            Map<String, String> urlParams = new HashMap<>();
            String url;
            if (!isQuickLogin) {
                url = HttpUrlGlobal.HTTP_USER_LOGIN_URL;
                msgCode = mLoginPwdEditText.getText().trim();
                urlParams.put("username", username);
                urlParams.put("password", msgCode);
                urlParams.put("code", qrCode);
                urlParams.put("kaptchaToken", SPUtils.getValue("kaptchaToken", ""));
            } else {
                url = HttpUrlGlobal.HTTP_MOBILE_LOGIN_URL;
                password = mLoginNumberEditText.getText().trim();
                urlParams.put("mobile", username);
                urlParams.put("code", password);
            }

            HttpUtils.getInstance().postDataWithParams(HttpUtils.REQUEST_LOGIN, url, urlParams, HttpUtils.MimeType.TEXT, this);
        } else if (v == mQuickLoginBtn) {
            switchLoginWay(false);
        } else if (v == mPasswordLoginBtn) {
            switchLoginWay(true);
        } else if (v == mForgetPwdBtn) {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("action", "1");
            startActivity(intent);
        } else if (v == mLoginNumberEditText.getRightTextView()) {
            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLoginNumberEditText.setRightText((count--) + " 秒");
                            if (count == 0) {
                                mLoginNumberEditText.setRightText("获取验证码");
                                count = 60;
                                cancel();
                            }
                        }
                    });

                }
            }, 0, 1000);

            HashMap<String, String> params = new HashMap<>();
            params.put("kaptchaToken", SPUtils.getStringValue("kaptchaToken"));
            params.put("code", mLoginQrEditText.getText().trim());
            params.put("mobile", mLoginAccountEditText.getText().trim());
            HttpUtils.getInstance().postDataWithBody(HttpUtils.REQUEST_SMS,
                    HttpUrlGlobal.HTTP_SMS_URL, params, null);
        }
    }

    private int count = 60;

    public void showDialog() {

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
            mLoginNumberEditText.setVisibility(View.GONE);
        } else {
            mPasswordLoginBtn.setEnabled(true);
            mQuickLoginBtn.setEnabled(false);
            mQuickLoginDivider.setVisibility(View.VISIBLE);
            mPasswordLoginDivider.setVisibility(View.INVISIBLE);
            mLoginPwdEditText.setVisibility(View.GONE);
            mLoginNumberEditText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        JSONObject dataObject = JSONObject.parseObject(data);
        if (requestId == HttpUtils.REQUEST_QR) {
            if (success) {
                final String bitmapStr = dataObject.getString("kaptcha");
                SPUtils.putValue("kaptchaToken", dataObject.getString("kaptchaToken"));
                invoke(new Runnable() {
                    @Override
                    public void run() {
                        mLoginQrEditText.getRightTextView().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapUtils.stringToBitmap(bitmapStr)));
                    }
                });
            }
        } else if (requestId == HttpUtils.REQUEST_LOGIN) {
            if (success) {
                if (code == 200) {
                    SPUtils.putValue("id", dataObject.getString("id"));
                    SPUtils.putValue("token", dataObject.getString("token"));
                    SPUtils.putValue("userAccount", dataObject.getString("suAccount"));
                    LogTool.printD("id: %s , token: %s", SPUtils.getStringValue("id"),
                            SPUtils.getStringValue("token"));
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (code == 401) {
                    // TODO 登出
                }
            } else {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showDialog();
                    }
                });
            }
        }
    }

    @Override
    public void onFailed(String errMsg) {

    }
}