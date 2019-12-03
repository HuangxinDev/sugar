package com.njxm.smart.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.AppEditText;
import com.ns.demo.R;

public class ResetPasswordActivity extends BaseActivity {
    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_reset_password;
    }

    private AppEditText mAccountEdit;
    private AppEditText mAccountQR;
    private AppEditText mAccountNumber;
    private AppCompatTextView mNextStepBtn;

    private View root_one;
    private View root_two;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getIntent().getStringExtra("action");

        View divider = findViewById(R.id.divider1);
        View title = findViewById(R.id.title);

        if (action.equals("1")) {
            setVisiable(mActionBarTitle, View.GONE);
            setVisiable(divider, View.GONE);
        } else {
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


        mAccountEdit.getEditText().addTextChangedListener(textWatcher);
        mAccountQR.getEditText().addTextChangedListener(textWatcher);
        mAccountNumber.getEditText().addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mNextStepBtn) {
            setVisiable(root_one, View.GONE);
            setVisiable(root_two, View.VISIBLE);
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
