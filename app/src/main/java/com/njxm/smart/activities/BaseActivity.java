package com.njxm.smart.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.njxm.smart.utils.LogTool;
import com.ns.demo.R;

/**
 * 基类，提供共用方法和回调
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG;

    protected AppCompatImageButton mActionBarBackBtn;
    protected AppCompatTextView mActionBarTitle;
    protected AppCompatImageButton mActionBarRightBtn;

    protected BaseActivity() {
        TAG = this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogTool.printI("[%s] %s", TAG, ">>> onCreate <<<");
//        StatusBarUtil.setStatusBarColor(this, R.color.text_color_gray);

        setContentView(setContentLayoutId());
//        getWindow().setStatusBarColor(setStatusBarColor());
//        getWindow().setStatusBarColor(getColor(R.color.color_blue_1));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        View ll = findViewById(R.id.ll_root);
        if (ll != null) {
            ll.setPadding(0, getStatusBarHeight(this), 0, 0);
        }

        mActionBarBackBtn = findViewById(R.id.action_bar_left);
        mActionBarRightBtn = findViewById(R.id.action_bar_right);
        mActionBarTitle = findViewById(R.id.action_bar_title);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        LogTool.printD("status bar height: %s", statusBarHeight);
        return statusBarHeight;
    }

    /**
     * 获取 布局ID
     *
     * @return
     */
    protected abstract int setContentLayoutId();

    protected int setStatusBarColor() {
        return R.color.text_color_white;
    }
}