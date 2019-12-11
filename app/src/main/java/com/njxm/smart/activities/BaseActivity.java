package com.njxm.smart.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.njxm.smart.utils.LogTool;
import com.njxm.smart.view.callbacks.OnActionBarChange;
import com.ns.demo.R;

import java.util.Locale;

/**
 * 基类，提供共用方法和回调
 */
public abstract class BaseActivity extends AppCompatActivity implements OnActionBarChange,
        OnClickListener {

    protected final String TAG;

    protected AppCompatImageView mActionBarBackBtn;
    protected AppCompatTextView mActionBarTitle;
    protected AppCompatImageView mActionBarRightBtn;

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
        if (mActionBarBackBtn != null) {
            mActionBarBackBtn.setOnClickListener(this);
        }

        if (mActionBarRightBtn != null) {
            mActionBarRightBtn.setOnClickListener(this);
        }
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

    @Override
    public void onClick(View v) {
        if (v == mActionBarBackBtn) {
            onClickLeftBtn();
        } else if (v == mActionBarRightBtn) {
            onClickRightBtn();
        }
    }

    @Override
    public void onClickLeftBtn() {

    }

    @Override
    public void onClickRightBtn() {

    }

    @Override
    public void setActionBarTitle(String title) {
        if (mActionBarTitle != null) {
            mActionBarTitle.setText(title);
            mActionBarTitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showView(View view, boolean show) {
        if (view != null) {
            view.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setImageResource(AppCompatImageButton view, int resourceId) {
        if (view != null) {
            view.setImageResource(resourceId);
        }
    }

    @Override
    public void showLeftBtn(boolean show, int resourcesId) {
        if (mActionBarBackBtn != null) {
            if (show) {
                mActionBarBackBtn.setImageResource(resourcesId);
                mActionBarBackBtn.setVisibility(View.VISIBLE);
            } else {
                mActionBarBackBtn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showRightBtn(boolean show, int resourcesId) {
        if (mActionBarRightBtn != null) {
            if (show) {
                mActionBarRightBtn.setImageResource(resourcesId);
                mActionBarRightBtn.setVisibility(View.VISIBLE);
            } else {
                mActionBarRightBtn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showTitle(boolean show, String title) {
        if (mActionBarTitle != null) {
            if (show) {
                mActionBarTitle.setText(title);
                mActionBarTitle.setVisibility(View.VISIBLE);
            } else {
                mActionBarTitle.setVisibility(View.GONE);
            }
        }
    }

    protected void showToast(String format, Object... objects) {
        format = (format == null) ? "null" : ((objects != null && objects.length != 0) ?
                String.format(Locale.US, format, objects) : format);
        Toast.makeText(this, format, Toast.LENGTH_SHORT).show();
    }

    protected void setVisiable(View view, int visiable) {
        if (view == null) {
            return;
        }
        view.setVisibility(visiable);
    }
}