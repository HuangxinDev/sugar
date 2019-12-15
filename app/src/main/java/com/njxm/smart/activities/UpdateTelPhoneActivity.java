package com.njxm.smart.activities;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.AppEditText;
import com.ns.demo.R;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UpdateTelPhoneActivity extends BaseActivity implements HttpCallBack {
    private static final int REQUEST_UPDATE_PHONE = 100;
    private static final int REQUEST_UPDATE_QRCODE = 570;
    private static final int REQUEST_SMS = 507;

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

    private int count = 60;

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

        mNewPhoneNumberCode.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Timer timer = new Timer();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        UpdateTelPhoneActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mNewPhoneNumberCode.setRightText((count--) + " 秒");
                                if (count == 0) {
                                    mNewPhoneNumberCode.setRightText("获取验证码");
                                    count = 60;
                                    cancel();
                                }
                            }
                        });

                    }
                }, 0, 1000);
                if (count == 60) {
                    getSMS();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mConfirmBtn) {
            switch (currentId) {
                case ONE:
                    currentId = TWO;
                    mVerifyPhone.setEnabled(true);
                    mBindPhoneEdit.setVisibility(View.GONE);
                    mVerifyPhoneLayout.setVisibility(View.VISIBLE);
                    break;
                case TWO:
                    currentId = THREE;
                    mConfirmBtn.setText("确认");
                    break;
                case THREE:
                    updateTelPhone();
                    break;
            }
        }
    }

    @Override
    public void onClickLeftBtn() {

        if (mVerifySuccess.isEnabled()) {
            finish();
            return;
        }

        switch (currentId) {
            case THREE:
                mConfirmBtn.setText("下一步");
                mVerifySuccess.setEnabled(false);
                currentId = TWO;
                break;
            case TWO:
                currentId = ONE;
                mVerifyPhone.setEnabled(false);
                mBindPhoneEdit.setVisibility(View.VISIBLE);
                mVerifyPhoneLayout.setVisibility(View.GONE);
                break;
            case ONE:
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        onClickLeftBtn();
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void updateTelPhone() {

        if (StringUtils.isEmpty(mBindPhoneEdit.getText()) || StringUtils.isEmpty(mQRCode.getText()) ||
                StringUtils.isEmpty(mNewPhoneNumberCode.getText())) {
            showToast("数据存在为空");
            return;
        }

        JSONObject object = new JSONObject();
        object.put("id", SPUtils.getStringValue(KeyConstant.KEY_USE_ID));
        object.put("mobile", mBindPhoneEdit.getText().trim());
        object.put("code", mNewPhoneNumberCode.getText().trim());
        RequestBody requestBody =
                FormBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE), object.toString());
        Request request = new Request.Builder().url("http://119.3.136.127:7776/api/sys/user/updateMobile")
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                .post(requestBody)
                .build();

        HttpUtils.getInstance().postData(REQUEST_UPDATE_PHONE, request, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpUtils.getInstance().postDataWithParams(HttpUtils.REQUEST_QR, HttpUrlGlobal.HTTP_QR_URL, null,
                HttpUtils.MimeType.JSON,
                this);
    }

    public void getSMS() {
        HashMap<String, String> params = new HashMap<>();
        params.put("kaptchaToken", SPUtils.getStringValue(KeyConstant.KEY_QR_IMAGE));
        params.put("code", mQRCode.getText().trim());
        params.put("mobile", mBindPhoneEdit.getText().trim());
        HttpUtils.getInstance().postDataWithBody(HttpUtils.REQUEST_SMS,
                HttpUrlGlobal.HTTP_SMS_URL, params, null);
    }

    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        if (requestId == HttpUtils.REQUEST_QR) {
            JSONObject jsonObject = JSON.parseObject(data);

            final String bitmapStr = jsonObject.getString("kaptcha");
            SPUtils.putValue(KeyConstant.KEY_QR_IMAGE, jsonObject.getString("kaptchaToken"));
            invoke(new Runnable() {
                @Override
                public void run() {
                    mQRCode.getRightTextView().setBackgroundDrawable(new BitmapDrawable(getResources(),
                            BitmapUtils.stringToBitmap(bitmapStr)));
                }
            });
        } else if (REQUEST_UPDATE_PHONE == requestId) {
            invoke(new Runnable() {
                @Override
                public void run() {
                    showToast("修改密码成功");
                    mVerifySuccess.setEnabled(true);
                }
            });
        }

    }

    @Override
    public void onFailed(String errMsg) {

    }
}
