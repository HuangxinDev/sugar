/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
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
import com.njxm.smart.view.callbacks.OnActionBarChange;
import com.ntxm.smart.BuildConfig;
import com.ntxm.smart.R;
import com.smart.cloud.utils.ToastUtils;
import com.sugar.android.common.utils.HandlerUtils;
import com.sugar.android.common.utils.Logger;
import com.sugar.android.common.utils.SPUtils;
import com.sugar.android.common.utils.TextViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * 基类，提供共用方法和回调
 */
public abstract class BaseActivity extends AppCompatActivity implements OnActionBarChange, BaseRunnable {
    private final String mTag;

    @Nullable
    @BindView(R.id.action_bar_left)
    AppCompatImageButton mActionBarBackBtn;

    @Nullable
    @BindView(R.id.action_bar_title)
    AppCompatTextView mActionBarTitle;

    @Nullable
    @BindView(R.id.action_bar_right)
    AppCompatImageButton mActionBarRightBtn;

    @Nullable
    @BindView(R.id.action_bar_right_text)
    AppCompatTextView actionBarRightTextView;

    File photoFile;

    private View container;

    protected BaseActivity() {
        mTag = getClass().getSimpleName();
    }

    protected ActionBarItem getActionBarItem() {
        return new ActionBarItem(R.mipmap.arrow_back, 0, "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        ButterKnife.bind(this);
        container = findViewById(R.id.ll_root);
        Logger.d(mTag, "activity lifecycle onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Logger.d(mTag, "activity lifecycle onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(mTag, "activity lifecycle onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d(mTag, "activity lifecycle onPause");
    }

    /**
     * 全屏 this.container.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE |
     * View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
     * View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
     * View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
     */
    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
        Logger.d(mTag, "activity lifecycle onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(mTag, "activity lifecycle onDestroy");
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
    protected int getLayoutId() {
        return -1;
    }

    /**
     * ActionBar 左侧点击事件 - 默认返回键-点击返回
     */
    @Override
    @Optional
    @OnClick(R.id.action_bar_left)
    public void onClickLeftBtn() {
        onBackPressed();
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
        if (mActionBarTitle != null) {
            mActionBarTitle.setText(title);
            mActionBarTitle.setVisibility(View.VISIBLE);
        }
    }

    @Optional
    public void setActionBarTitle(int resId) {
        if (mActionBarTitle != null) {
            mActionBarTitle.setText(resId);
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
            setRightResource(resourceId);
        }
    }

    @Override
    public void showLeftBtn(boolean show, int resourcesId) {
        if (mActionBarBackBtn != null) {
            if (show) {
                setRightResource(resourcesId);
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
            setRightResource(resourcesId);
        }
        if (show && actionBarRightTextView != null) {
            actionBarRightTextView.setVisibility(View.GONE);
        }
    }

    private void setRightResource(int resourcesId) {
        com.sugar.android.common.utils.ViewUtils.setImageResource(mActionBarRightBtn, resourcesId);
    }

    public void showRightBtn(boolean show, String text) {
        TextViewUtils.setText(actionBarRightTextView, text);
        if (actionBarRightTextView != null) {
            actionBarRightTextView.setVisibility(show ? View.VISIBLE : View.GONE);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void invoke(Runnable runnable) {
        if (AppUtils.isMainThread()) {
            runnable.run();
        } else {
            HandlerUtils.postToMain(runnable);
        }
    }

    public void takePhoto(int requestId) {
        PermissionRequestActivity.startPermissionRequest(this, new String[]{Manifest.permission.CAMERA}, 100, null);
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

    @Subscribe(sticky = true)
    public void onResponse(ResponseEvent event) {
        String url = event.getUrl();
        if (url.equals(UrlPath.PATH_USER_DETAILS_NEWS.getUrl())) {
            EventBus.getDefault().postSticky(JsonUtils.getJsonObject(event.getData(), UserBean.class));
        } else if (url.equals(UrlPath.PATH_USER_EDU_PULL.getUrl())) {
            EventBus.getDefault().post(JsonUtils.getJsonArray(event.getData(), EduTypeBean.class));
        } else if (url.equals(UrlPath.PATH_SYS_LOGOUT.getUrl())) {
            ToastUtils.showToast("登出成功");
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
    public void signUp(LogoutEvent event) {
        SPUtils.putValue(KeyConstant.KEY_USER_TOKEN, "");
        finish();
    }

    public static class MedicalBean {
        protected String id;

        private int delFlag;

        private String createTime;

        private String createUser;

        private String modifyTime;

        private String modifyUser;

        private String sumrStatus;

        private String sumrUserId;

        private String files;

        private List<String> pathList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getModifyUser() {
            return modifyUser;
        }

        public void setModifyUser(String modifyUser) {
            this.modifyUser = modifyUser;
        }

        public String getSumrStatus() {
            return sumrStatus;
        }

        public void setSumrStatus(String sumrStatus) {
            this.sumrStatus = sumrStatus;
        }

        public String getSumrUserId() {
            return sumrUserId;
        }

        public void setSumrUserId(String sumrUserId) {
            this.sumrUserId = sumrUserId;
        }

        public String getFiles() {
            return files;
        }

        public void setFiles(String files) {
            this.files = files;
        }

        List<String> getPathList() {
            return pathList;
        }

        public void setPathList(List<String> pathList) {
            this.pathList = pathList;
        }
    }
}