package com.njxm.smart.activities;

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

public class ResetPasswordActivity extends BaseActivity implements HttpCallBack {
    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_reset_password;
    }

    private AppEditText mAccountEdit;
    private AppEditText mAccountQR;
    private AppEditText mAccountNumber;
    private AppEditText mNewPwd1;
    private AppEditText mNewPwd2;
    private AppCompatTextView mNextStepBtn;
    private AppCompatTextView mConfirmBtn;

    private View root_one;
    private View root_two;

    private boolean isResetPwd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getIntent().getStringExtra("action");

        View divider = findViewById(R.id.divider1);
        View title = findViewById(R.id.title);

        if (action.equals("1")) {
            setVisible(mActionBarTitle, View.GONE);
            setVisible(divider, View.GONE);
            isResetPwd = true;
        } else {
            isResetPwd = false;
            setActionBarTitle("重置密码");
            setVisible(mActionBarRightBtn, View.GONE);
            setVisible(title, View.GONE);
        }
        showLeftBtn(true, R.mipmap.arrow_back_blue);


        root_one = findViewById(R.id.ll_1);
        root_two = findViewById(R.id.ll_2);

        mAccountEdit = findViewById(R.id.login_account);
        mAccountQR = findViewById(R.id.login_qr_code);
        mAccountNumber = findViewById(R.id.login_number_code);
        mNextStepBtn = findViewById(R.id.next_step);
        mNextStepBtn.setOnClickListener(this);
        setVisible(root_two, View.VISIBLE);
        mConfirmBtn = findViewById(R.id.login_confirm);
        mConfirmBtn.setOnClickListener(this);
        setVisible(root_two, View.GONE);

        mNewPwd1 = findViewById(R.id.new_pwd1);
        mNewPwd2 = findViewById(R.id.new_pwd2);

        mAccountEdit.getEditText().addTextChangedListener(textWatcher);
        mAccountQR.getEditText().addTextChangedListener(textWatcher);
        mAccountNumber.getEditText().addTextChangedListener(textWatcher);
        mAccountNumber.getRightTextView().setOnClickListener(this);
    }

    private int count = 60;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mNextStepBtn) {
            setVisible(root_one, View.GONE);
            setVisible(root_two, View.VISIBLE);
        } else if (v == mAccountNumber.getRightTextView()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mAccountNumber.setRightText((count--) + "秒");
                    if (count < 0) {
                        mAccountNumber.setRightText("获取验证码");
                        count = 60;
                        cancel();
                    }
                }
            }, 0, 1000);

            HashMap<String, String> params = new HashMap<>();
            params.put("kaptchaToken", SPUtils.getStringValue(KeyConstant.KEY_QR_IMAGE_TOKEN));
            params.put("code", mAccountQR.getText().trim());
            params.put("mobile", mAccountEdit.getText().trim());

            HttpUtils.getInstance().postDataWithBody(HttpUtils.REQUEST_SMS,
                    HttpUrlGlobal.HTTP_SMS_URL, params, null);
        } else if (v == mConfirmBtn) {

            if (TextUtils.isEmpty(mNewPwd1.getText()) || TextUtils.isEmpty(mNewPwd2.getText()) || !TextUtils.equals(mNewPwd1.getText(),
                    mNewPwd2.getText())) {
                showToast("两次密码输入不一致");
                return;
            }
            HashMap<String, String> params = new HashMap<>();
            params.put("mobile", mAccountEdit.getText().trim());
            params.put("code", mAccountNumber.getText().trim());
            params.put("password", mNewPwd2.getText().trim());

            HttpUtils.getInstance().postDataWithBody(-1, HttpUrlGlobal.HTTP_RESET_PWD_URL, params
                    , this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpUtils.getInstance().postDataWithParams(HttpUtils.REQUEST_QR, HttpUrlGlobal.HTTP_QR_URL, null,
                this);
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        clickBack();
    }

    private void clickBack() {
        if (root_two.getVisibility() == View.VISIBLE) {
            setVisible(root_one, View.VISIBLE);
            setVisible(root_two, View.GONE);
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        clickBack();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (StringUtils.isNotEmpty(mAccountEdit.getText()) &&
                    StringUtils.isNotEmpty(mAccountQR.getText()) &&
                    StringUtils.isNotEmpty(mAccountNumber.getText())) {
                mNextStepBtn.setEnabled(true);
            } else {
                mNextStepBtn.setEnabled(false);
            }
        }
    };


    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        super.onSuccess(requestId, success, code, data);
        if (requestId == HttpUtils.REQUEST_QR) {
            if (success) {
                JSONObject dataObject = JSONObject.parseObject(data);
                mAccountQR.getRightTextView().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapUtils.stringToBitmap(dataObject.getString("kaptcha"))));
                SPUtils.putValue(KeyConstant.KEY_QR_IMAGE_TOKEN, dataObject.getString("kaptchaToken"));
            }
        } else if (requestId == -1) {
            if (success && code == 200) {
                // 密码修改成功
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                finish();
            }
        }

    }

    @Override
    public void onFailed(String errMsg) {

    }
}
