/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.AlertDialogUtils;
import com.ntxm.smart.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置页面 主页我的-设置
 */
public class SettingsActivity extends BaseActivity {
    @BindView(R.id.login_exit)
    protected AppCompatTextView mExitLoginBtn;
    @BindView(R.id.settings_reset_pwd)
    protected View mResetPwdBtn;
    @BindView(R.id.settings_update_phone)
    protected View mUpdateTelBtn;
    @BindView(R.id.settings_check_update)
    protected View mCheckUpdateBtn;
    @BindView(R.id.settings_clean_cache)
    protected View mCleanCacheBtn;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle("设置");
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
        this.findViewById(R.id.settings_check_update).setOnClickListener(v -> {
            com.njxm.smart.activities.BaseActivity.showToast("清除缓存完成");
        });
    }

    /**
     * 重置密码
     */
    @OnClick(R.id.settings_reset_pwd)
    protected void resetPwd() {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra("action", "2");
        this.startActivity(intent);
    }

    /**
     * 更换手机
     */
    @OnClick(R.id.settings_update_phone)
    protected void updateTelPhone() {
        Intent intent = new Intent(this, UpdateTelPhoneActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.login_exit)
    protected void clickLogoutItem() {
        AlertDialogUtils.getInstance().showConfirmDialog(this, "确定退出？", "取消", "确定", new AlertDialogUtils.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(AlertDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onNegativeButtonClick(AlertDialog dialog) {
                HttpUtils.getInstance().request(RequestEvent.newBuilder()
                        .url(UrlPath.PATH_SYS_LOGOUT.getUrl())
                        .build());
                dialog.dismiss();
            }
        });
    }

    /**
     * 登出操作
     */
    public void logOut() {

    }
}
