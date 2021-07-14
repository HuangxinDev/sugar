/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.model.jsonbean.UserBean;
import com.ntxm.smart.R;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = GlobalRouter.USER_PHONE)
public class UserPhoneActivity extends BaseActivity {

    @BindView(R.id.news_user_phone)
    protected TextView tvUserPhone;

    @NotNull
    private static News getUserSecretPhone(String phone) {
        return new PhoneNews(phone);
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.user_phone_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle("手机");
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
    }

    @OnClick(R.id.settings_update_phone)
    public void onViewClicked(View v) {
        this.startActivity(new Intent(this, UpdateTelPhoneActivity.class));
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshUI(UserBean bean) {
        this.tvUserPhone.setText(UserPhoneActivity.getUserSecretPhone(bean.getPhone()).getSecretNews());
    }
}
