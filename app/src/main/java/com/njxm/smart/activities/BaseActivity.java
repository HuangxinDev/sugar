/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;

import com.hxin.common.perrmission.PermissionRequestActivity;
import com.njxm.smart.base.BaseRunnable;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.LogoutEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.EduTypeBean;
import com.njxm.smart.model.jsonbean.QRCodeBean;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.utils.AppUtils;
import com.njxm.smart.utils.JsonUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StatusBarUtil;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.callbacks.OnActionBarChange;
import com.ntxm.smart.BuildConfig;
import com.ntxm.smart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * 基类，提供共用方法和回调
 */
public abstract class BaseActivity extends AppCompatActivity implements OnActionBarChange,
        OnClickListener, BaseRunnable {

    protected static Handler sHandler = new Handler();
    protected final String mTag;
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
    protected File photoFile;
    private long lastClickTime = 0;

    protected BaseActivity() {
        this.mTag = this.getClass().getSimpleName();
    }

    public static Handler getMainHandler() {
        return BaseActivity.sHandler;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        LogTool.printD(BaseActivity.class, "status bar height: %s", statusBarHeight);
        return statusBarHeight;
    }

    protected static int setStatusBarColor() {
        return R.color.text_color_white;
    }

    protected static void setVisible(View view, int visible) {
        if (view == null) {
            return;
        }
        view.setVisibility(visible);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public static void doOtherThing(Runnable runnable) {
        runnable.run();
    }

    /**
     * 显示弹窗错误信息
     *
     * @param format
     * @param objects
     */
    protected static void showToast(String format, Object... objects) {


        if (StringUtils.isEmpty(format)) {
            return;
        }
        String toastMsg = (objects != null && objects.length != 0) ?
                String.format(Locale.US, format, objects) : format;


        EventBus.getDefault().post(new ToastEvent(toastMsg));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
         禁止屏幕旋转,固定屏幕方向
        */
        this.setContentView(this.setContentLayoutId());
        ButterKnife.bind(this);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        StatusBarUtil.setStatusBar(this, false, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showToast(ToastEvent event) {
        Toast toast = Toast.makeText(BaseActivity.this, event.toastMsg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 获取 布局ID
     *
     * @return
     */
    protected abstract int setContentLayoutId();

    @Override
    public void onClick(View v) {
    }

    /**
     * ActionBar 左侧点击事件 - 默认返回键-点击返回
     */
    @Override
    @Optional
    @OnClick(R.id.action_bar_left)
    public void onClickLeftBtn() {
        this.onBackPressed();
    }

    /**
     * ActionBar 右侧按钮点击事件
     */
    @Override
    @Optional
    @OnClick({R.id.action_bar_right, R.id.action_bar_right_text})
    public void onClickRightBtn() {

    }

    /**
     * 设置ActionBar标题
     *
     * @param title 标题
     */
    @Override
    @Optional
    public void setActionBarTitle(String title) {
        if (this.mActionBarTitle != null) {
            this.mActionBarTitle.setText(title);
            this.mActionBarTitle.setVisibility(View.VISIBLE);
        }
    }

    @Optional
    public void setActionBarTitle(int resId) {
        if (this.mActionBarTitle != null) {
            this.mActionBarTitle.setText(resId);
            this.mActionBarTitle.setVisibility(View.VISIBLE);
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
        if (this.mActionBarBackBtn != null) {
            if (show) {
                this.mActionBarBackBtn.setImageResource(resourcesId);
                this.mActionBarBackBtn.setVisibility(View.VISIBLE);
            } else {
                this.mActionBarBackBtn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showRightBtn(boolean show, int resourcesId) {
        if (this.mActionBarRightBtn != null) {
            this.mActionBarRightBtn.setVisibility(show ? View.VISIBLE : View.GONE);
            this.mActionBarRightBtn.setImageResource(resourcesId);
        }

        if (show && this.tvActionBarRightText != null) {
            this.tvActionBarRightText.setVisibility(View.GONE);
        }
    }

    public void showRightBtn(boolean show, String text) {
        if (this.tvActionBarRightText != null) {
            this.tvActionBarRightText.setVisibility(show ? View.VISIBLE : View.GONE);
            this.tvActionBarRightText.setText(text);
        }

        if (show && this.mActionBarRightBtn != null) {
            this.mActionBarRightBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void showTitle(boolean show, String title) {
        if (this.mActionBarTitle != null) {
            if (show) {
                this.mActionBarTitle.setText(title);
                this.mActionBarTitle.setVisibility(View.VISIBLE);
            } else {
                this.mActionBarTitle.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void invoke(Runnable runnable) {
        if (AppUtils.isMainThread()) {
            runnable.run();
        } else {
            BaseActivity.sHandler.post(runnable);
        }
    }

    public void takePhoto(int requestId) {
        PermissionRequestActivity.startPermissionRequest(this, new String[]{Manifest.permission.CAMERA}, 100, null);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        UUID uuid = UUID.randomUUID();
        this.photoFile = new File(this.getFilesDir(), uuid + ".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID +
                    ".fileProvider", this.photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            Uri uri = Uri.fromFile(this.photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        this.startActivityForResult(intent, requestId);

    }

    @Subscribe(sticky = true)
    public void onResponse(ResponseEvent event) {
        String url = event.getUrl();
        if (url.equals(UrlPath.PATH_USER_DETAILS_NEWS.getUrl())) {
            EventBus.getDefault().postSticky(JsonUtils.getJsonObject(event.getData(), UserBean.class));
        } else if (url.equals(UrlPath.PATH_USER_EDU_PULL.getUrl())) {
            EventBus.getDefault().post(JsonUtils.getJsonArray(event.getData(), EduTypeBean.class));
        } else if (url.equals(UrlPath.PATH_SYS_LOGOUT.getUrl())) {
            BaseActivity.showToast("登出成功");
            EventBus.getDefault().post(new LogoutEvent());
        } else if (url.equals(UrlPath.PATH_PROVINCE_CITY_AREA.getUrl())) {
            if (event.isSuccess()) {
                SPUtils.putValue(KeyConstant.KEY_COMMON_ADDRESS_LIST, event.getData());
            }
        } else if (url.equals(UrlPath.PATH_PICTURE_VERIFY.getUrl())) {
            EventBus.getDefault().post(JsonUtils.getJsonObject(event.getData(), QRCodeBean.class));
        }
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        this.finish();
    }

    /**
     * 快速点击两次屏蔽第二次点击事件，避免出现同一页面.
     *
     * @param ev
     * @return true:结束 false:继续传递
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (!this.isFastDoubleClick()) {
                return super.dispatchTouchEvent(ev);
            }
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 300ms内是否重复点击
     *
     * @return true:是
     */
    private boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeDiff = time - this.lastClickTime;
        this.lastClickTime = time;
        return timeDiff <= 300;
    }
}