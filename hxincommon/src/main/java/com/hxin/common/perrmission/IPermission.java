/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.hxin.common.perrmission;

import android.util.Log;

/**
 * Created by Hxin on 2020/3/27
 * Function: 权限操作类
 */
public interface IPermission {
    IPermission NONE = new IPermission() {
        @Override
        public void onPermissionSuccess(int requestCode) {
            Log.d("Hxin", "权限申请成功");
        }

        @Override
        public void onPermissionCanceled(int requestCode) {
            Log.w("Hxin", "权限申请被取消");
        }

        @Override
        public void onPermissionDenied(int requestCode) {
            Log.e("Hxin", "权限申请被拒绝");
        }
    };

    /**
     * 权限申请成功
     * @param requestCode
     */
    void onPermissionSuccess(int requestCode);

    /**
     * 权限取消申请
     * @param requestCode
     */
    void onPermissionCanceled(int requestCode);

    /**
     * 权限申请被拒绝
     * @param requestCode
     */
    void onPermissionDenied(int requestCode);
}
