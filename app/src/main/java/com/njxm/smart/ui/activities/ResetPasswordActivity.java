/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.module.login.LoginFragment;
import com.njxm.smart.tools.AppTextWatcher;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.AlertDialogUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.RegexUtil;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.view.AppEditText;
import com.ntxm.smart.R;
import com.smart.cloud.utils.ToastUtils;
import com.smart.cloud.utils.VerifyCodeUtils;
import com.sugar.android.common.safe.SafeIntent;
import com.sugar.android.common.utils.StringUtils;
import com.sugar.android.common.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private AppEditText mAccountEdit;
    private AppEditText mAccountQR;
    private AppEditText mAccountNumber;
    private AppEditText mNewPwd1;
    private AppEditText mNewPwd2;
    private AppCompatTextView mNextStepBtn;
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            ResetPasswordActivity.this.mNextStepBtn.setEnabled(StringUtils.isNotEmpty(com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountEdit.getText()) &&
                    StringUtils.isNotEmpty(com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountQR.getText()) &&
                    StringUtils.isNotEmpty(com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountNumber.getText()));
        }
    };
    private AppCompatTextView mConfirmBtn;
    private final AppTextWatcher watcher = new AppTextWatcher() {
        @Override
        public void afterTextChanged(String s) {
            com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountNumber.getRightTextView().setEnabled(StringUtils.isNotEmpty(com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountEdit.getText()) && StringUtils.isNotEmpty(com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountQR.getText()));
            com.njxm.smart.ui.activities.ResetPasswordActivity.this.mConfirmBtn.setEnabled(StringUtils.isNotEmpty(com.njxm.smart.ui.activities.ResetPasswordActivity.this.mNewPwd1.getText()) && StringUtils.isNotEmpty(com.njxm.smart.ui.activities.ResetPasswordActivity.this.mNewPwd2.getText()));
        }

        @Override
        public void beforeTextChanged(CharSequence s) {
            super.beforeTextChanged(s);
            com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountNumber.getRightTextView().setEnabled(StringUtils.isNotEmpty(com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountEdit.getText()) && StringUtils.isNotEmpty(com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountQR.getText()));
            com.njxm.smart.ui.activities.ResetPasswordActivity.this.mConfirmBtn.setEnabled(StringUtils.isNotEmpty(com.njxm.smart.ui.activities.ResetPasswordActivity.this.mNewPwd1.getText()) && StringUtils.isNotEmpty(com.njxm.smart.ui.activities.ResetPasswordActivity.this.mNewPwd2.getText()));
        }
    };
    private View root_one;
    private View root_two;
    private boolean isForgetPwd;
    private int count = 60;

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String action = new SafeIntent(getIntent()).getStringExtra("action");

        View divider = ViewUtils.findViewById(this, R.id.divider1);
        View title = this.findViewById(R.id.title);
        this.isForgetPwd = StringUtils.isEqual(action, "1");

        if (this.isForgetPwd) {
            ViewUtils.setVisibility(this.mActionBarTitle, View.GONE);
            ViewUtils.setVisibility(divider, View.GONE);
        } else {
            this.setActionBarTitle("重置密码");
            ViewUtils.setVisibility(this.mActionBarRightBtn, View.GONE);
            ViewUtils.setVisibility(title, View.GONE);
        }

        this.showLeftBtn(true, R.mipmap.arrow_back_blue);


        this.root_one = this.findViewById(R.id.ll_1);
        this.root_two = this.findViewById(R.id.ll_2);

        this.mAccountEdit = this.findViewById(R.id.login_account);
        this.mAccountQR = this.findViewById(R.id.login_qr_code);
        this.mAccountQR.getRightTextView().setOnClickListener(this);
        this.mAccountNumber = this.findViewById(R.id.login_number_code);
        this.mNextStepBtn = this.findViewById(R.id.next_step);
        this.mNextStepBtn.setOnClickListener(this);
        ViewUtils.setVisibility(this.root_two, View.VISIBLE);
        this.mConfirmBtn = this.findViewById(R.id.login_confirm);
        this.mConfirmBtn.setOnClickListener(this);
        ViewUtils.setVisibility(this.root_two, View.GONE);

        this.mNewPwd1 = this.findViewById(R.id.new_pwd1);
        this.mNewPwd2 = this.findViewById(R.id.new_pwd2);

        this.mAccountEdit.getEditText().addTextChangedListener(this.textWatcher);
        this.mAccountQR.getEditText().addTextChangedListener(this.textWatcher);
        this.mAccountNumber.getEditText().addTextChangedListener(this.textWatcher);
        this.mAccountNumber.getRightTextView().setOnClickListener(this);

        if (this.isForgetPwd) {
            this.showView(this.root_one, true);
            this.showView(this.root_two, false);
            this.mAccountEdit.getEditText().addTextChangedListener(this.watcher);
            this.mAccountQR.getEditText().addTextChangedListener(this.watcher);
            this.mAccountNumber.getRightTextView().setEnabled(StringUtils.isNotEmpty(this.mAccountEdit.getText()) && StringUtils.isNotEmpty(this.mAccountQR.getText()));
        } else {
            this.showView(this.root_one, false);
            this.showView(this.root_two, true);
            this.mConfirmBtn.setEnabled(StringUtils.isNotEmpty(this.mNewPwd1.getText()) && StringUtils.isNotEmpty(this.mNewPwd2.getText()));
        }

        this.mNewPwd1.getEditText().addTextChangedListener(this.watcher);
        this.mNewPwd2.getEditText().addTextChangedListener(this.watcher);

        VerifyCodeUtils.getQRCode();
    }

    @Override
    public void onResponse(ResponseEvent event) {
        String url = event.getUrl();
        if (url.equals(UrlPath.PATH_PICTURE_VERIFY.getUrl())) {
            JSONManager manager = new JSONManager(JSONObject.parseObject(event.getData()));
            if (manager.hasProperties("kaptcha")) {
                setCode(manager.getProperties("kaptcha"));
            }

            if (manager.hasProperties("kaptchaToken")) {
                updateToken(manager.getProperties("kaptchaToken"));
            }
        } else if (url.equals(UrlPath.PATH_MODIFY_PWD.getUrl()) || url.equals(UrlPath.PATH_RESET_PWD.getUrl())) {
            // 密码修改成功
            EventBus.getDefault().post(new ToastEvent("密码修改成功"));
            this.startActivity(new Intent(ResetPasswordActivity.this, LoginFragment.class));
            this.finish();
        } else {
            super.onResponse(event);
        }
    }

    private void updateToken(String token) {
        SPUtils.putValue(KeyConstant.KEY_QR_IMAGE_TOKEN, token);
    }

    private void setCode(String bitmapStr) {
        this.mAccountQR.getRightTextView().setBackgroundDrawable(new BitmapDrawable(this.getResources(), BitmapUtils.transform(bitmapStr)));
    }

    private static class JSONManager {
        private final JSONObject jsonObject;

        public JSONManager(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        private boolean hasProperties(String name) {
            return jsonObject.containsKey(name);
        }

        public String getProperties(String name) {
            return jsonObject.getString(name);
        }
    }


    @Override
    public void onClick(View v) {
        if (v == this.mNextStepBtn) {
            if (RegexUtil.isMobilePhone(mAccountEdit.getText())) {
                ToastUtils.showToast("请输入正确的手机号");
                return;
            }

            if (StringUtils.isEmpty(this.mAccountQR.getText())) {
                ToastUtils.showToast("验证码不可为空");
                return;
            }

            if (StringUtils.isEmpty(this.mAccountNumber.getText())) {
                ToastUtils.showToast("请输入账户密码");
                return;
            }

            ViewUtils.setVisibility(this.root_one, View.GONE);
            ViewUtils.setVisibility(this.root_two, View.VISIBLE);
        } else if (v == this.mAccountNumber.getRightTextView()) {
            this.mAccountNumber.setEnabled(false);
            if (this.count != 60) {
                return;
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountNumber.setRightText((com.njxm.smart.ui.activities.ResetPasswordActivity.this.count--) + "秒");
                    if (com.njxm.smart.ui.activities.ResetPasswordActivity.this.count < 0) {
                        com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountNumber.setRightText("获取验证码");
                        com.njxm.smart.ui.activities.ResetPasswordActivity.this.count = 60;
                        com.njxm.smart.ui.activities.ResetPasswordActivity.this.mAccountNumber.setEnabled(true);
                        this.cancel();
                    }
                }
            }, 0, 1000);

            sendSms();
        } else if (v == this.mConfirmBtn) {
            if (!TextUtils.equals(this.mNewPwd1.getText(), this.mNewPwd2.getText())) {
                this.showDialog(new DialogMessage("", null, ""));
                return;
            }

            RequestEvent.Builder requestBuilder = new RequestEvent.Builder();
            if (this.isForgetPwd) {
                requestBuilder.url(UrlPath.PATH_MODIFY_PWD.getUrl())
                        .addBodyJson("mobile", this.mAccountEdit.getText().trim())
                        .addBodyJson("code", this.mAccountNumber.getText().trim());
            } else {
                requestBuilder.url(UrlPath.PATH_RESET_PWD.getUrl())
                        .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID));
            }
            requestBuilder.addBodyJson("password", this.mNewPwd2.getText().trim());
            HttpUtils.getInstance().request(requestBuilder.build());
        } else if (v == this.mAccountQR.getRightTextView()) {
            VerifyCodeUtils.getQRCode();
        }
    }

    /**
     * 发送短信
     */
    private void sendSms() {
        HttpUtils.getInstance().request(new RequestEvent.Builder()
                .url(UrlPath.PATH_SMS.getUrl())
                .addBodyJson("kaptchaToken", SPUtils.getStringValue(KeyConstant.KEY_QR_IMAGE_TOKEN))
                .addBodyJson("code", this.mAccountQR.getText().trim())
                .addBodyJson("mobile", this.mAccountEdit.getText().trim()).build());
    }

    @Override
    public void onClickLeftBtn() {
        this.clickBack();
    }

    private void clickBack() {
        if (this.root_two.getVisibility() == View.VISIBLE && this.isForgetPwd) {
            this.mNewPwd1.clearText();
            this.mNewPwd2.clearText();
            ViewUtils.setVisibility(this.root_one, View.VISIBLE);
            ViewUtils.setVisibility(this.root_two, View.GONE);
        } else {
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        this.clickBack();
    }

    /**
     * 使用错误Dialog
     */
    private void showDialog(DialogMessage message) {
        this.invoke(
                () -> AlertDialogUtils.getInstance().showConfirmDialog(this, message.message,
                        message.yesText, message.noText));
//                        getString(R.string.different_password), null, getString(R.string.input_again)));
    }

    private static final class DialogMessage {
        String message;

        String yesText;

        String noText;

        public DialogMessage(String message, String yesText, String noText) {
            this.message = message;
            this.yesText = yesText;
            this.noText = noText;
        }
    }
}
