package com.njxm.smart.activities;

import android.app.AlertDialog;
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
import com.njxm.smart.tools.AppTextWatcher;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.AlertDialogUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.RegexUtil;
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

    private boolean isForgetPwd;

    private AppTextWatcher watcher = new AppTextWatcher() {
        @Override
        public void afterTextChanged(String s) {
            mAccountNumber.getRightTextView().setEnabled(StringUtils.isNotEmpty(mAccountEdit.getText()) && StringUtils.isNotEmpty(mAccountQR.getText()));
            mConfirmBtn.setEnabled(StringUtils.isNotEmpty(mNewPwd1.getText()) && StringUtils.isNotEmpty(mNewPwd2.getText()));
        }

        @Override
        public void beforeTextChanged(CharSequence s) {
            super.beforeTextChanged(s);
            mAccountNumber.getRightTextView().setEnabled(StringUtils.isNotEmpty(mAccountEdit.getText()) && StringUtils.isNotEmpty(mAccountQR.getText()));
            mConfirmBtn.setEnabled(StringUtils.isNotEmpty(mNewPwd1.getText()) && StringUtils.isNotEmpty(mNewPwd2.getText()));
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getIntent().getStringExtra("action");

        View divider = findViewById(R.id.divider1);
        View title = findViewById(R.id.title);

        isForgetPwd = action.equals("1");

        if (isForgetPwd) {
            setVisible(mActionBarTitle, View.GONE);
            setVisible(divider, View.GONE);
        } else {
            setActionBarTitle("重置密码");
            setVisible(mActionBarRightBtn, View.GONE);
            setVisible(title, View.GONE);
        }

        showLeftBtn(true, R.mipmap.arrow_back_blue);


        root_one = findViewById(R.id.ll_1);
        root_two = findViewById(R.id.ll_2);

        mAccountEdit = findViewById(R.id.login_account);
        mAccountQR = findViewById(R.id.login_qr_code);
        mAccountQR.getRightTextView().setOnClickListener(this);
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

        if (isForgetPwd) {
            showView(root_one, true);
            showView(root_two, false);
            mAccountEdit.getEditText().addTextChangedListener(watcher);
            mAccountQR.getEditText().addTextChangedListener(watcher);
            mAccountNumber.getRightTextView().setEnabled(StringUtils.isNotEmpty(mAccountEdit.getText()) && StringUtils.isNotEmpty(mAccountQR.getText()));
        } else {
            showView(root_one, false);
            showView(root_two, true);
            mConfirmBtn.setEnabled(StringUtils.isNotEmpty(mNewPwd1.getText()) && StringUtils.isNotEmpty(mNewPwd2.getText()));
        }

        mNewPwd1.getEditText().addTextChangedListener(watcher);
        mNewPwd2.getEditText().addTextChangedListener(watcher);


        HttpUtils.getInstance().postDataWithParams(HttpUtils.REQUEST_QR, HttpUrlGlobal.HTTP_QR_URL, null,
                this);
    }

    private int count = 60;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mNextStepBtn) {

            if (StringUtils.isEmpty(mAccountEdit.getText()) || !mAccountEdit.getText().matches(RegexUtil.REGEX_PHONE)) {
                showToast("请输入正确的手机号");
                return;
            }

            if (StringUtils.isEmpty(mAccountQR.getText())) {
                showToast("验证码不可为空");
                return;
            }

            if (StringUtils.isEmpty(mAccountNumber.getText())) {
                showToast("请输入账户密码");
                return;
            }

            setVisible(root_one, View.GONE);
            setVisible(root_two, View.VISIBLE);
        } else if (v == mAccountNumber.getRightTextView()) {
            mAccountNumber.setEnabled(false);
            if (count != 60) {
                return;
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mAccountNumber.setRightText((count--) + "秒");
                    if (count < 0) {
                        mAccountNumber.setRightText("获取验证码");
                        count = 60;
                        mAccountNumber.setEnabled(true);
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

            if (!TextUtils.equals(mNewPwd1.getText(), mNewPwd2.getText())) {
                showDialog();
                return;
            }
            HashMap<String, String> params = new HashMap<>();
            String url;
            if (isForgetPwd) {
                params.put("mobile", mAccountEdit.getText().trim());
                params.put("code", mAccountNumber.getText().trim());
                url = HttpUrlGlobal.HTTP_RESET_PWD_URL;
            } else {
                url = HttpUrlGlobal.URL_SETTINGS_RESET_PWD;
                params.put("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID));
            }
            params.put("password", mNewPwd2.getText().trim());

            HttpUtils.getInstance().postData(-1, HttpUtils.getJsonRequest(url, params), this);
        } else if (v == mAccountQR.getRightTextView()) {
            HttpUtils.getInstance().postDataWithParams(HttpUtils.REQUEST_QR, HttpUrlGlobal.HTTP_QR_URL, null,
                    this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClickLeftBtn() {
        clickBack();
    }

    private void clickBack() {
        if (root_two.getVisibility() == View.VISIBLE && isForgetPwd) {
            mNewPwd1.clearText();
            mNewPwd2.clearText();
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
                showToast("密码修改成功");
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                finish();
            }
        }

    }

    /**
     * 使用错误Dialog
     */
    private void showDialog() {
        invoke(new Runnable() {
            @Override
            public void run() {
                AlertDialogUtils.getInstance().showConfirmDialog(ResetPasswordActivity.this,
                        "两次密码不一致", "", "重新输入", new AlertDialogUtils.OnButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(AlertDialog dialog) {

                            }

                            @Override
                            public void onNegativeButtonClick(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
            }

        });
    }
}
