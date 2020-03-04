package com.njxm.smart.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.AlertDialogUtils;
import com.ntxm.smart.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置页面 主页我的-设置
 */
///////////////////////////////////////////////////////////////////////////
// 设置页面
///////////////////////////////////////////////////////////////////////////
public class SettingsActivity extends BaseActivity {
    @Override
    protected int setContentLayoutId() {
        return R.layout.my_setting;
    }

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("设置");
        showLeftBtn(true, R.mipmap.arrow_back_blue);
    }

    /**
     * 清除缓存文件
     */
    @OnClick(R.id.settings_clean_cache)
    protected void cleanCache() {
        showToast("清除缓存完成");
    }

    /**
     * 检查版本更新
     */
    @OnClick(R.id.settings_check_update)
    protected void checkUpdate() {
        showToast("当前已是最新版本");
    }

    /**
     * 重置密码
     */
    @OnClick(R.id.settings_reset_pwd)
    protected void resetPwd() {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra("action", "2");
        startActivity(intent);
    }

    /**
     * 更换手机
     */
    @OnClick(R.id.settings_update_phone)
    protected void updateTelPhone() {
        Intent intent = new Intent(this, UpdateTelPhoneActivity.class);
        startActivity(intent);
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
                        .url(HttpUrlGlobal.HTTP_MY_SETTING_LOGOUT)
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
