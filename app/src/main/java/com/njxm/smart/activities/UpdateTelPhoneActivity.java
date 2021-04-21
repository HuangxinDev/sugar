/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import java.util.Timer;
import java.util.TimerTask;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.QRCodeBean;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.tools.AppTextWatcher;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.ResolutionUtil;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.AppEditText;
import com.ntxm.smart.R;

public class UpdateTelPhoneActivity extends BaseActivity {

    private static final int ONE = 763;
    private static final int TWO = 551;
    private static final int THREE = 913;
    private static int currentId = com.njxm.smart.activities.UpdateTelPhoneActivity.ONE;
    AppCompatImageView mBindPhone;
    AppCompatImageView mVerifyPhone;
    AppCompatImageView mVerifySuccess;
    AppCompatTextView mConfirmBtn;
    AppEditText mBindPhoneEdit;
    AppEditText mNewPhoneNumberCode;
    AppEditText mQRCode;
    private final AppTextWatcher mAppTextWatcher = new AppTextWatcher() {
        @Override
        public void afterTextChanged(String s) {
            if (StringUtils.isNotEmpty(com.njxm.smart.activities.UpdateTelPhoneActivity.this.mQRCode.getText()) && StringUtils.isNotEmpty(com.njxm.smart.activities.UpdateTelPhoneActivity.this.mNewPhoneNumberCode.getText())) {
                com.njxm.smart.activities.UpdateTelPhoneActivity.this.mConfirmBtn.setEnabled(true);
                com.njxm.smart.activities.UpdateTelPhoneActivity.this.mConfirmBtn.setText("确认");
            } else {
                com.njxm.smart.activities.UpdateTelPhoneActivity.this.mConfirmBtn.setEnabled(false);
                com.njxm.smart.activities.UpdateTelPhoneActivity.this.mConfirmBtn.setText("下一步");
            }
        }
    };
    View mVerifyPhoneLayout;
    private AppCompatTextView tvPhonePop;
    private int count = 60;
    private Timer timer;

    /**
     * 获取二维码
     */
    private static void requestQrCode() {
        HttpUtils.getInstance().request(new RequestEvent.Builder().url(UrlPath.PATH_PICTURE_VERIFY.getUrl()).build());
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_update_tel_phone;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
        this.setActionBarTitle("更换手机");
        this.mBindPhone = this.findViewById(R.id.bind_phone);
        this.mVerifyPhone = this.findViewById(R.id.verify_phone);
        this.mVerifySuccess = this.findViewById(R.id.verify_phone_success);
        this.mBindPhoneEdit = this.findViewById(R.id.new_phone);
        this.mNewPhoneNumberCode = this.findViewById(R.id.login_number_code);
        this.mQRCode = this.findViewById(R.id.login_qr_code);
        this.mConfirmBtn = this.findViewById(R.id.btn_login);
        this.mConfirmBtn.setOnClickListener(this);
        this.mVerifyPhoneLayout = this.findViewById(R.id.ll_1);
        this.mVerifyPhone.setEnabled(false);
        this.mVerifySuccess.setEnabled(false);
        this.tvPhonePop = this.findViewById(R.id.phone_pop);
        this.tvPhonePop.setPadding(0, ResolutionUtil.dp2Px(35), 0, 0);

        this.mBindPhoneEdit.getEditText().addTextChangedListener(new AppTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                com.njxm.smart.activities.UpdateTelPhoneActivity.this.mConfirmBtn.setEnabled(StringUtils.isNotEmpty(s));
            }
        });


        com.njxm.smart.activities.UpdateTelPhoneActivity.requestQrCode();

        this.mQRCode.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.njxm.smart.activities.UpdateTelPhoneActivity.requestQrCode();
            }
        });

        this.mNewPhoneNumberCode.getEditText().addTextChangedListener(this.mAppTextWatcher);

        this.mNewPhoneNumberCode.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isEmpty(com.njxm.smart.activities.UpdateTelPhoneActivity.this.mQRCode.getText())) {
                    com.njxm.smart.activities.BaseActivity.showToast("图形验证码不可为空");
                    return;
                }

                if (com.njxm.smart.activities.UpdateTelPhoneActivity.this.count != 60) {
                    return;
                }

                com.njxm.smart.activities.UpdateTelPhoneActivity.this.timer = new Timer();
                com.njxm.smart.activities.UpdateTelPhoneActivity.this.timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        UpdateTelPhoneActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                com.njxm.smart.activities.UpdateTelPhoneActivity.this.mNewPhoneNumberCode.setRightText((com.njxm.smart.activities.UpdateTelPhoneActivity.this.count--) + " 秒");
                                if (com.njxm.smart.activities.UpdateTelPhoneActivity.this.count == 0) {
                                    cancel();
                                }
                            }
                        });

                    }

                    @Override
                    public boolean cancel() {
                        com.njxm.smart.activities.UpdateTelPhoneActivity.this.count = 60;
                        com.njxm.smart.activities.UpdateTelPhoneActivity.this.mNewPhoneNumberCode.getRightTextView().setEnabled(true);
                        com.njxm.smart.activities.UpdateTelPhoneActivity.this.mNewPhoneNumberCode.setRightText("重新获取");
                        return super.cancel();
                    }
                }, 0, 1000);
                if (com.njxm.smart.activities.UpdateTelPhoneActivity.this.count == 60) {
                    HttpUtils.getInstance().request(new RequestEvent.Builder().url(UrlPath.PATH_SMS.getUrl())
                            .addBodyJson("kaptchaToken", SPUtils.getStringValue(KeyConstant.KEY_QR_IMAGE_TOKEN))
                            .addBodyJson("mobile", com.njxm.smart.activities.UpdateTelPhoneActivity.this.mBindPhoneEdit.getText().trim())
                            .addBodyJson("code", com.njxm.smart.activities.UpdateTelPhoneActivity.this.mQRCode.getText().trim())
                            .build());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == this.mConfirmBtn) {
            switch (com.njxm.smart.activities.UpdateTelPhoneActivity.currentId) {
                case com.njxm.smart.activities.UpdateTelPhoneActivity.ONE:
                    if (this.mBindPhoneEdit.getText().matches("1[0-9]{10}")) {
                        com.njxm.smart.activities.UpdateTelPhoneActivity.currentId = com.njxm.smart.activities.UpdateTelPhoneActivity.TWO;
                        this.mVerifyPhone.setEnabled(true);
                        this.mBindPhoneEdit.setVisibility(View.GONE);
                        this.mVerifyPhoneLayout.setVisibility(View.VISIBLE);
                        this.mConfirmBtn.setEnabled(StringUtils.isNotEmpty(this.mQRCode.getText()) || StringUtils.isNotEmpty(this.mNewPhoneNumberCode.getText()));
                        this.tvPhonePop.setVisibility(View.GONE);
                        com.njxm.smart.activities.UpdateTelPhoneActivity.requestQrCode();
                    } else {
                        com.njxm.smart.activities.BaseActivity.showToast("手机号格式不正确");
                    }
                    break;
                case com.njxm.smart.activities.UpdateTelPhoneActivity.TWO:
                    this.updateTelPhone();
                    break;
            }
        }
    }

    @Override
    public void onClickLeftBtn() {

        if (this.mVerifySuccess.isEnabled()) {
            com.njxm.smart.activities.UpdateTelPhoneActivity.currentId = com.njxm.smart.activities.UpdateTelPhoneActivity.ONE;
            this.finish();
            return;
        }

        switch (com.njxm.smart.activities.UpdateTelPhoneActivity.currentId) {
            case com.njxm.smart.activities.UpdateTelPhoneActivity.TWO:
                com.njxm.smart.activities.UpdateTelPhoneActivity.currentId = com.njxm.smart.activities.UpdateTelPhoneActivity.ONE;
                this.mVerifyPhone.setEnabled(false);
                this.mVerifyPhoneLayout.setVisibility(View.GONE);
                this.mQRCode.clearText();
                this.mNewPhoneNumberCode.clearText();

                this.mConfirmBtn.setEnabled(StringUtils.isNotEmpty(this.mBindPhoneEdit.getText()));
                this.mBindPhoneEdit.setVisibility(View.VISIBLE);
                this.tvPhonePop.setVisibility(View.VISIBLE);
                if (this.timer != null) {
                    this.timer.cancel();
                    this.count = 60;
                    this.mNewPhoneNumberCode.setRightText("获取验证码");
                    this.mNewPhoneNumberCode.getRightTextView().setEnabled(true);
                }
                break;
            case com.njxm.smart.activities.UpdateTelPhoneActivity.ONE:
                this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        this.onClickLeftBtn();
    }

    private void updateTelPhone() {

        RequestEvent requestEvent = new RequestEvent.Builder()
                .url(UrlPath.PATH_USER_PHONE_REPLACE.getUrl())
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .addBodyJson("mobile", this.mBindPhoneEdit.getText().trim())
                .addBodyJson("code", this.mNewPhoneNumberCode.getText().trim())
                .build();
        HttpUtils.getInstance().request(requestEvent);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponse(ResponseEvent event) {
        if (event.getUrl().equals(UrlPath.PATH_USER_PHONE_REPLACE.getUrl()) && event.isSuccess()) {
            SPUtils.putValue(KeyConstant.KEY_USER_TEL_PHONE, this.mBindPhoneEdit.getText());
            this.mVerifySuccess.setEnabled(true);
            this.mConfirmBtn.setVisibility(View.GONE);
            this.tvPhonePop.setPadding(0, ResolutionUtil.dp2Px(126), 0, 0);
            this.tvPhonePop.setVisibility(View.VISIBLE);
            this.tvPhonePop.setText(this.getNewPhone());
            this.mVerifyPhoneLayout.setVisibility(View.GONE);
        } else {
            super.onResponse(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void uploadQrCode(QRCodeBean bean) {
        this.mQRCode.getRightTextView().setBackgroundDrawable(new BitmapDrawable(this.getResources(),
                BitmapUtils.stringToBitmap(bean.getImage())));
        SPUtils.putValue(KeyConstant.KEY_QR_IMAGE_TOKEN, bean.getImageToken());
    }

    public SpannableString getPhonePop(String telPhone) {
        String string = "* 更换手机号后，下次登录可使用新手机号登录\n当前手机号";
        if (StringUtils.isEmpty(telPhone) || !telPhone.matches("1[0-9]{10}")) {
            return new SpannableString("手机号不存在");
        }

        String phone = new StringBuffer(telPhone).replace(3, 7, "****").toString();

        int len1 = string.length();
        int len2 = len1 + phone.length();
        SpannableString spannableString = new SpannableString(string + phone);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ffff6600")), 0, 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(17, true), string.indexOf("\n"),
                len2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(this.getColor(R.color.color_252525)), len1, len2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public SpannableString getNewPhone() {
        String newPhone =
                "新手机号为\n" + new StringBuilder(SPUtils.getStringValue(KeyConstant.KEY_USER_TEL_PHONE)).replace(3, 7, "****").toString();
        SpannableString spannableString = new SpannableString(newPhone);
        spannableString.setSpan(new AbsoluteSizeSpan(14, true), 0, newPhone.indexOf("\n"),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(19, true), newPhone.indexOf("\n"),
                newPhone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(this.getColor(R.color.color_252525)), newPhone.indexOf("\n"),
                newPhone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void updateUI(UserBean bean) {
        this.tvPhonePop.setText(this.getPhonePop(bean.getPhone()));
    }
}
