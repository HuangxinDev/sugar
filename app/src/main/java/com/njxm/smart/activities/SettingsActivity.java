package com.njxm.smart.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.AlertDialogUtils;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.R;

import okhttp3.Request;

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

    private AppCompatTextView mExitLoginBtn;

    private View mResetPwdBtn;
    private View mUpdateTelBtn;
    private View mCheckUpdateBtn;
    private View mCleanCacheBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExitLoginBtn = findViewById(R.id.login_exit);
        mExitLoginBtn.setOnClickListener(this);
        setActionBarTitle("设置");
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        mResetPwdBtn = findViewById(R.id.settings_reset_pwd);
        mUpdateTelBtn = findViewById(R.id.settings_update_phone);
        mCheckUpdateBtn = findViewById(R.id.settings_check_update);
        mCleanCacheBtn = findViewById(R.id.settings_clean_cache);

        mResetPwdBtn.setOnClickListener(this);
        mUpdateTelBtn.setOnClickListener(this);
        mCleanCacheBtn.setOnClickListener(this);
        mCheckUpdateBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        if (mExitLoginBtn == v) {
            AlertDialogUtils.getInstance().showConfirmDialog(this, "确定退出？", "取消", "确定", new AlertDialogUtils.OnButtonClickListener() {
                @Override
                public void onPositiveButtonClick(AlertDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void onNegativeButtonClick(AlertDialog dialog) {
                    dialog.dismiss();
                    logOut();
                }
            });
        } else if (v == mResetPwdBtn) {
            intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("action", "2");
        } else if (v == mUpdateTelBtn) {
            intent = new Intent(this, UpdateTelPhoneActivity.class);
        } else if (v == mCheckUpdateBtn) {
            showToast("当前已是最新版本");
        } else if (v == mCleanCacheBtn) {
            showToast("清除缓存完成");
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void logOut() {
        Request request = new Request.Builder().url(HttpUrlGlobal.HTTP_MY_SETTING_LOGOUT)
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                .build();

        HttpUtils.getInstance().postData(0, request, new HttpCallBack() {
            @Override
            public void onSuccess(int requestId, boolean success, int code, String data) {
                if (success) {
                    invoke(new Runnable() {
                        @Override
                        public void run() {
                            showToast("登出成功");
                            SPUtils.putValue(KeyConstant.KEY_USER_TOKEN, "");
                            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailed(String errMsg) {

            }
        });
    }
}
