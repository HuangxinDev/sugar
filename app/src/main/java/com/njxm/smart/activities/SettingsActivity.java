package com.njxm.smart.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.njxm.smart.utils.AlertDialogUtils;
import com.ns.demo.R;

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
                    Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                    startActivity(intent);
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

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        finish();
    }
}
