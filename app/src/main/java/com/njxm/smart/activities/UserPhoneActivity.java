package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.R;

@Route(path = GlobalRouter.USER_PHONE)
public class UserPhoneActivity extends BaseActivity {

    private TextView tvUserPhone;
    private TextView tvUpdatePhoneBtn;

    @Override
    protected int setContentLayoutId() {
        return R.layout.user_phone_activity;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("手机");
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        tvUserPhone = findViewById(R.id.news_user_phone);
        tvUpdatePhoneBtn = findViewById(R.id.settings_update_phone);


        StringBuilder builder = new StringBuilder(11);
        builder.append(SPUtils.getStringValue(KeyConstant.KEY_USER_TEL_PHONE));
        tvUserPhone.setText(builder.replace(3, 7, "****").toString());
        tvUpdatePhoneBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == tvUpdatePhoneBtn) {
            startActivity(new Intent(this, UpdateTelPhoneActivity.class));
        }
    }
}
