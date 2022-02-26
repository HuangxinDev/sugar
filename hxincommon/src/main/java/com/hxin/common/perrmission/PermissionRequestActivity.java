/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.hxin.common.perrmission;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.sugar.android.common.safe.SafeIntent;


/**
 * Created by Hxin on 2020/3/27
 * Function: 权限申请Activity
 */
public class PermissionRequestActivity extends AppCompatActivity {

    private static final String PERMISSION = "permission";
    private static final String PERMISSION_CODE = "permission_code";

    private static IPermission sPermissionListener = IPermission.NONE;

    /**
     * 开始申请权限
     *
     * @param context     Activity/Fragment上下文
     * @param permissions 待申请权限
     * @param requestCode 权限申请码
     * @param listener    权限申请状态监听
     */
    public static void startPermissionRequest(Context context, String[] permissions, int requestCode, IPermission listener) {
        if (listener != null) {
            com.hxin.common.perrmission.PermissionRequestActivity.sPermissionListener = listener;
        }
        Intent intent = new Intent(context, PermissionRequestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putStringArray(com.hxin.common.perrmission.PermissionRequestActivity.PERMISSION, permissions);
        bundle.putInt(com.hxin.common.perrmission.PermissionRequestActivity.PERMISSION_CODE, requestCode);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new SafeIntent(getIntent()).getExtras();
        String[] permission = bundle.getStringArray(com.hxin.common.perrmission.PermissionRequestActivity.PERMISSION);
        int code = bundle.getInt(com.hxin.common.perrmission.PermissionRequestActivity.PERMISSION_CODE);
        this.requestPermission(permission, code);
        this.overridePendingTransition(0, 0);
    }

    private void requestPermission(String[] permissions, int code) {
        if (PermissionUtil.hasAllPermission(this, permissions)) {
            com.hxin.common.perrmission.PermissionRequestActivity.sPermissionListener.onPermissionSuccess(code);
            this.finish();
            this.overridePendingTransition(0, 0);
        } else {
            ActivityCompat.requestPermissions(this, permissions, code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionUtil.verifyPermissions(grantResults)) {
            // 权限全部验证成功
            com.hxin.common.perrmission.PermissionRequestActivity.sPermissionListener.onPermissionSuccess(requestCode);
        } else {
            // 有权限没有授权
            if (PermissionUtil.shouldShowRequestPermissionPop(this, permissions)) {
                com.hxin.common.perrmission.PermissionRequestActivity.sPermissionListener.onPermissionCanceled(requestCode);
            } else {
                com.hxin.common.perrmission.PermissionRequestActivity.sPermissionListener.onPermissionDenied(requestCode);
            }
        }
        this.finish();
        this.overridePendingTransition(0, 0);
    }
}
