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
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;

import com.njxm.smart.base.BaseRunnable;
import com.njxm.smart.tools.PermissionManager;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.utils.AppUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.view.callbacks.OnActionBarChange;
import com.ns.demo.BuildConfig;
import com.ns.demo.R;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

/**
 * 基类，提供共用方法和回调
 */
public abstract class BaseActivity extends AppCompatActivity implements OnActionBarChange,
        OnClickListener, HttpCallBack, BaseRunnable {

    protected final String TAG;

    protected static Handler mHandler = new Handler();

    protected AppCompatImageView mActionBarBackBtn;
    protected AppCompatTextView mActionBarTitle;
    protected AppCompatTextView mActionBarRightBtn;

    protected BaseActivity() {
        TAG = this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setStatusBarColor(this, R.color.text_color_gray);

        PermissionManager.requestPermission(this, 100, PermissionManager.sRequestPermissions);
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
            mActionBarBackBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickLeftBtn();
                }
            });
        }

        if (mActionBarRightBtn != null) {
            mActionBarRightBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRightBtn();
                }
            });
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

    /**
     * 默认返回键-点击返回
     */
    @Override
    public void onClickLeftBtn() {
        finish();
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
            mActionBarRightBtn.setVisibility(show ? View.VISIBLE : View.GONE);
            mActionBarRightBtn.setBackgroundResource(resourcesId);
        }
    }

    public void showRightBtn(boolean show, String text) {
        if (mActionBarRightBtn != null) {
            mActionBarRightBtn.setVisibility(show ? View.VISIBLE : View.GONE);
            mActionBarRightBtn.setText(text);
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
        if (code == 401 || code == 999) {
            // TODO 验证过期,需要退回登录页
            invoke(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
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
}