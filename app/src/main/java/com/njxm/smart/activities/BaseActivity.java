package com.njxm.smart.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;

import com.njxm.smart.base.BaseRunnable;
import com.njxm.smart.eventbus.LogoutEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.PermissionManager;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.utils.AppUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StatusBarUtil;
import com.njxm.smart.view.callbacks.OnActionBarChange;
import com.ns.demo.BuildConfig;
import com.ns.demo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * 基类，提供共用方法和回调
 */
public abstract class BaseActivity extends AppCompatActivity implements OnActionBarChange,
        OnClickListener, HttpCallBack, BaseRunnable {

    protected final String TAG;

    protected static Handler mHandler = new Handler();

    @Nullable
    @BindView(R.id.action_bar_left)
    protected AppCompatImageButton mActionBarBackBtn;

    @Nullable
    @BindView(R.id.action_bar_title)
    protected AppCompatTextView mActionBarTitle;

    @Nullable
    @BindView(R.id.action_bar_right)
    protected AppCompatImageButton mActionBarRightBtn;

    @Nullable
    @BindView(R.id.action_bar_right_text)
    protected AppCompatTextView tvActionBarRightText;

    protected BaseActivity() {
        TAG = this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setStatusBarColor(this, R.color.text_color_gray);

        PermissionManager.requestPermission(this, 100, PermissionManager.sRequestPermissions);
        setContentView(setContentLayoutId());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
//        getWindow().setStatusBarColor(setStatusBarColor());
//        getWindow().setStatusBarColor(getColor(R.color.color_blue_1));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        View ll = findViewById(R.id.ll_root);
        if (ll != null) {
            ll.setPadding(0, getStatusBarHeight(this), 0, 0);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View contentView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            if (contentView != null) {
                contentView.setFitsSystemWindows(true);
            }
        }
        StatusBarUtil.setImmersiveStatusBar(this, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
    }

    /**
     * ActionBar 左侧点击事件 - 默认返回键-点击返回
     */
    @Optional
    @OnClick(R.id.action_bar_left)
    public void onClickLeftBtn() {
        onBackPressed();
    }

    /**
     * ActionBar 右侧按钮点击事件
     */
    @Optional
    @OnClick({R.id.action_bar_right, R.id.action_bar_right_text})
    public void onClickRightBtn() {

    }

    /**
     * 设置ActionBar标题
     *
     * @param title 标题
     */
    @Optional
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
            mActionBarRightBtn.setVisibility(show ? View.VISIBLE : View.GONE);
            mActionBarRightBtn.setImageResource(resourcesId);
        }

        if (show && tvActionBarRightText != null) {
            tvActionBarRightText.setVisibility(View.GONE);
        }
    }

    public void showRightBtn(boolean show, String text) {
        if (tvActionBarRightText != null) {
            tvActionBarRightText.setVisibility(show ? View.VISIBLE : View.GONE);
            tvActionBarRightText.setText(text);
        }

        if (show && mActionBarRightBtn != null) {
            mActionBarRightBtn.setVisibility(View.GONE);
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

    /**
     * 显示弹窗错误信息
     *
     * @param format
     * @param objects
     */
    protected void showToast(String format, Object... objects) {
        final String toastMsg = (format == null) ? "null" :
                ((objects != null && objects.length != 0) ?
                        String.format(Locale.US, format, objects) : format);
        invoke(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(BaseActivity.this, toastMsg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void setVisible(View view, int visible) {
        if (view == null) {
            return;
        }
        view.setVisibility(visible);
    }

    @Override
    public void invoke(Runnable runnable) {
        if (AppUtils.isMainThread()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    protected File photoFile;

    public void takePhoto(int requestId) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        UUID uuid = UUID.randomUUID();
        photoFile = new File(getFilesDir(), uuid + ".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID +
                    ".fileProvider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            Uri uri = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        startActivityForResult(intent, requestId);
    }

    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
    }

    @Override
    public void onFailed(final String errMsg) {
        invoke(new Runnable() {
            @Override
            public void run() {
                showToast(errMsg);
            }
        });
    }

    /**
     * 登出服务
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logOut(LogoutEvent event) {
        if (this instanceof LoginActivity) {
            return;
        }
        SPUtils.putValue(KeyConstant.KEY_USER_TOKEN, "");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}