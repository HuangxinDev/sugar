package com.njxm.smart.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.njxm.smart.view.AppEditText;
import com.ns.demo.R;

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
    View mVerifyPhoneLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        setActionBarTitle("更换手机");

        mBindPhone = findViewById(R.id.bind_phone);
        mVerifyPhone = findViewById(R.id.verify_phone);
        mVerifySuccess = findViewById(R.id.verify_phone_success);

        mBindPhoneEdit = findViewById(R.id.new_phone);
        mConfirmBtn = findViewById(R.id.btn_login);
        mConfirmBtn.setOnClickListener(this);
        mVerifyPhoneLayout = findViewById(R.id.ll_1);

        mVerifyPhone.setEnabled(false);
        mVerifySuccess.setEnabled(false);
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
                    mVerifySuccess.setEnabled(true);
                    mConfirmBtn.setText("确认");
                    break;
                case THREE:
            }
        }
    }

    @Override
    public void onClickLeftBtn() {
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
}
