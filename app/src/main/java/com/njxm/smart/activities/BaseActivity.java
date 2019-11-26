package com.njxm.smart.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.njxm.smart.utils.LogTool;

/**
 * 基类，提供共用方法和回调
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG;

    protected BaseActivity() {
        TAG = this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogTool.printI("[%s] %s", TAG, ">>> onCreate <<<");
//        StatusBarUtil.setStatusBarColor(this, R.color.text_color_gray);

    }


}