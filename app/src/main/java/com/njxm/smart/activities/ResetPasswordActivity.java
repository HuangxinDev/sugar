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
import com.njxm.smart.tools.network.CallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.AppEditText;
import com.ns.demo.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResetPasswordActivity extends BaseActivity {
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
            setVisiable(mActionBarTitle, View.GONE);
            setVisiable(divider, View.GONE);
            isResetPwd = true;
        } else {
            isResetPwd = false;
            setActionBarTitle("重置密码");
            setVisiable(mActionBarRightBtn, View.GONE);
            setVisiable(title, View.GONE);
        }
        showLeftBtn(true, R.mipmap.arrow_back_blue);


        root_one = findViewById(R.id.ll_1);
        root_two = findViewById(R.id.ll_2);

        mAccountEdit = findViewById(R.id.login_account);
        mAccountQR = findViewById(R.id.login_qr_code);
        mAccountNumber = findViewById(R.id.login_number_code);
        mNextStepBtn = findViewById(R.id.next_step);
        mNextStepBtn.setOnClickListener(this);
        setVisiable(root_two, View.VISIBLE);
        mConfirmBtn = findViewById(R.id.login_confirm);
        mConfirmBtn.setOnClickListener(this);
        setVisiable(root_two, View.GONE);

        mNewPwd1 = findViewById(R.id.new_pwd1);
        mNewPwd2 = findViewById(R.id.new_pwd2);

        mAccountEdit.getEditText().addTextChangedListener(textWatcher);
        mAccountQR.getEditText().addTextChangedListener(textWatcher);
        mAccountNumber.getEditText().addTextChangedListener(textWatcher);

//        if (isResetPwd) {
        HttpUtils.getInstance().postData("http://119.3.136.127:7777/auth/kaptcha/get", null,
                HttpUtils.MimeType.JSON,
                new CallBack() {
                    @Override
                    public void onSuccess(String param) {
                        JSONObject object = JSONObject.parseObject(param);
                        boolean isSuccess = object.getBoolean("success");
                        LogTool.printD("qr result: " + param);
                        if (isSuccess) {
                            JSONObject dataObject = object.getJSONObject("data");
                            mAccountQR.getRightTextView().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapUtils.stringToBitmap(dataObject.getString("kaptcha"))));
                            SPUtils.putValue("kaptchaToken", dataObject.getString("kaptchaToken"));
                        }
                    }

                    @Override
                    public void onFailed() {

                    }
                }, false);

        mAccountNumber.getRightTextView().setOnClickListener(this);
//        }


    }

    private static int count = 60;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mNextStepBtn) {
            setVisiable(root_one, View.GONE);
            setVisiable(root_two, View.VISIBLE);
        } else if (v == mAccountNumber.getRightTextView()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mAccountNumber.setRightText((count--) + "秒");
                    if (count < 0) {
                        mAccountNumber.setRightText("获取验证码");
                        cancel();
                    }
                }
            }, 0, 1000);

            OkHttpClient client = HttpUtils.getInstance().getOkHttpClient();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("kaptchaToken", SPUtils.getValue("kaptchaToken", ""));
            jsonObject.put("code", mAccountQR.getText().trim());
            jsonObject.put("mobile", mAccountEdit.getText().trim());

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    jsonObject.toString());

            Request request = new Request.Builder().url("http://119.3.136" +
                    ".127:7777/auth/sms/sendSms")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogTool.printE("failed: " + e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    LogTool.printD("%s success result: %s", call.request().url(),
                            response.body().string());
                }
            });
        } else if (v == mConfirmBtn) {

            if (TextUtils.isEmpty(mNewPwd1.getText()) || TextUtils.isEmpty(mNewPwd2.getText()) || !TextUtils.equals(mNewPwd1.getText(),
                    mNewPwd2.getText())) {
                showToast("两次密码输入不一致");
                return;
            }

            OkHttpClient client = HttpUtils.getInstance().getOkHttpClient();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", mAccountEdit.getText().trim());
            jsonObject.put("code", mAccountNumber.getText().trim());
            jsonObject.put("password", mNewPwd2.getText().trim());

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    jsonObject.toString());

            Request request = new Request.Builder().url("http://119.3.136.127:7776/sys/user/updatePassByMobile")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogTool.printE("failed: " + e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    JSONObject object = JSONObject.parseObject(response.body().string());

                    if (object.getInteger("code") == 200) {
                        // 密码修改成功
                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            });
        }
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        clickBack();
    }

    private void clickBack() {
        if (root_two.getVisibility() == View.VISIBLE) {
            setVisiable(root_one, View.VISIBLE);
            setVisiable(root_two, View.GONE);
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


}
