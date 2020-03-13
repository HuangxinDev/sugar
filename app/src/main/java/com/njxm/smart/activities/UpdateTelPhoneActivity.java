package com.njxm.smart.activities;

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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateTelPhoneActivity extends BaseActivity {

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_update_tel_phone;
    }

    private static final int ONE = 763;
    private static final int TWO = 551;
    private static final int THREE = 913;

    private static int currentId = ONE;


    AppCompatImageView mBindPhone;
    AppCompatImageView mVerifyPhone;
    AppCompatImageView mVerifySuccess;
    AppCompatTextView mConfirmBtn;
    AppEditText mBindPhoneEdit;
    AppEditText mNewPhoneNumberCode;
    AppEditText mQRCode;
    View mVerifyPhoneLayout;

    private AppCompatTextView tvPhonePop;

    private int count = 60;

    private AppTextWatcher mAppTextWatcher = new AppTextWatcher() {
        @Override
        public void afterTextChanged(String s) {
            if (StringUtils.isNotEmpty(mQRCode.getText()) && StringUtils.isNotEmpty(mNewPhoneNumberCode.getText())) {
                mConfirmBtn.setEnabled(true);
                mConfirmBtn.setText("确认");
            } else {
                mConfirmBtn.setEnabled(false);
                mConfirmBtn.setText("下一步");
            }
        }
    };

    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        setActionBarTitle("更换手机");
        mBindPhone = findViewById(R.id.bind_phone);
        mVerifyPhone = findViewById(R.id.verify_phone);
        mVerifySuccess = findViewById(R.id.verify_phone_success);
        mBindPhoneEdit = findViewById(R.id.new_phone);
        mNewPhoneNumberCode = findViewById(R.id.login_number_code);
        mQRCode = findViewById(R.id.login_qr_code);
        mConfirmBtn = findViewById(R.id.btn_login);
        mConfirmBtn.setOnClickListener(this);
        mVerifyPhoneLayout = findViewById(R.id.ll_1);
        mVerifyPhone.setEnabled(false);
        mVerifySuccess.setEnabled(false);
        tvPhonePop = findViewById(R.id.phone_pop);
        tvPhonePop.setPadding(0, ResolutionUtil.dp2Px(35), 0, 0);

        mBindPhoneEdit.getEditText().addTextChangedListener(new AppTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                mConfirmBtn.setEnabled(StringUtils.isNotEmpty(s));
            }
        });


        requestQrCode();

        mQRCode.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQrCode();
            }
        });

        mNewPhoneNumberCode.getEditText().addTextChangedListener(mAppTextWatcher);

        mNewPhoneNumberCode.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isEmpty(mQRCode.getText())) {
                    showToast("图形验证码不可为空");
                    return;
                }

                if (count != 60) {
                    return;
                }

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        UpdateTelPhoneActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mNewPhoneNumberCode.setRightText((count--) + " 秒");
                                if (count == 0) {
                                    cancel();
                                }
                            }
                        });

                    }

                    @Override
                    public boolean cancel() {
                        count = 60;
                        mNewPhoneNumberCode.getRightTextView().setEnabled(true);
                        mNewPhoneNumberCode.setRightText("重新获取");
                        return super.cancel();
                    }
                }, 0, 1000);
                if (count == 60) {
                    HttpUtils.getInstance().request(new RequestEvent.Builder().url(UrlPath.PATH_SMS.getUrl())
                            .addBodyJson("kaptchaToken", SPUtils.getStringValue(KeyConstant.KEY_QR_IMAGE_TOKEN))
                            .addBodyJson("mobile", mBindPhoneEdit.getText().trim())
                            .addBodyJson("code", mQRCode.getText().trim())
                            .build());
                }
            }
        });
    }

    /**
     * 获取二维码
     */
    private void requestQrCode() {
        HttpUtils.getInstance().request(new RequestEvent.Builder().url(UrlPath.PATH_PICTURE_VERIFY.getUrl()).build());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mConfirmBtn) {
            switch (currentId) {
                case ONE:
                    if (mBindPhoneEdit.getText().matches("1[0-9]{10}")) {
                        currentId = TWO;
                        mVerifyPhone.setEnabled(true);
                        mBindPhoneEdit.setVisibility(View.GONE);
                        mVerifyPhoneLayout.setVisibility(View.VISIBLE);
                        mConfirmBtn.setEnabled(StringUtils.isNotEmpty(mQRCode.getText()) || StringUtils.isNotEmpty(mNewPhoneNumberCode.getText()));
                        tvPhonePop.setVisibility(View.GONE);
                        requestQrCode();
                    } else {
                        showToast("手机号格式不正确");
                    }
                    break;
                case TWO:
                    updateTelPhone();
                    break;
            }
        }
    }

    @Override
    public void onClickLeftBtn() {

        if (mVerifySuccess.isEnabled()) {
            currentId = ONE;
            finish();
            return;
        }

        switch (currentId) {
            case TWO:
                currentId = ONE;
                mVerifyPhone.setEnabled(false);
                mVerifyPhoneLayout.setVisibility(View.GONE);
                mQRCode.clearText();
                mNewPhoneNumberCode.clearText();

                mConfirmBtn.setEnabled(StringUtils.isNotEmpty(mBindPhoneEdit.getText()));
                mBindPhoneEdit.setVisibility(View.VISIBLE);
                tvPhonePop.setVisibility(View.VISIBLE);
                if (timer != null) {
                    timer.cancel();
                    count = 60;
                    mNewPhoneNumberCode.setRightText("获取验证码");
                    mNewPhoneNumberCode.getRightTextView().setEnabled(true);
                }
                break;
            case ONE:
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        onClickLeftBtn();
    }

    private void updateTelPhone() {

        RequestEvent requestEvent = new RequestEvent.Builder()
                .url(UrlPath.PATH_USER_PHONE_REPLACE.getUrl())
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .addBodyJson("mobile", mBindPhoneEdit.getText().trim())
                .addBodyJson("code", mNewPhoneNumberCode.getText().trim())
                .build();
        HttpUtils.getInstance().request(requestEvent);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponse(ResponseEvent event) {
        if (event.getUrl().equals(UrlPath.PATH_USER_PHONE_REPLACE.getUrl()) && event.isSuccess()) {
            SPUtils.putValue(KeyConstant.KEY_USER_TEL_PHONE, mBindPhoneEdit.getText());
            mVerifySuccess.setEnabled(true);
            mConfirmBtn.setVisibility(View.GONE);
            tvPhonePop.setPadding(0, ResolutionUtil.dp2Px(126), 0, 0);
            tvPhonePop.setVisibility(View.VISIBLE);
            tvPhonePop.setText(getNewPhone());
            mVerifyPhoneLayout.setVisibility(View.GONE);
        } else {
            super.onResponse(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void uploadQrCode(QRCodeBean bean) {
        mQRCode.getRightTextView().setBackgroundDrawable(new BitmapDrawable(getResources(),
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
        spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.color_252525)), len1, len2,
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
        spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.color_252525)), newPhone.indexOf("\n"),
                newPhone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void updateUI(UserBean bean) {
        tvPhonePop.setText(getPhonePop(bean.getPhone()));
    }
}
