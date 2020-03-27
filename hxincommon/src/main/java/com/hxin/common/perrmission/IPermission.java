package com.hxin.common.perrmission;

import android.util.Log;

/**
 * Created by Hxin on 2020/3/27
 * Function: 权限操作类
 */
public interface IPermission {
    IPermission NONE = new IPermission() {
        @Override
        public void onPermissionSuccess() {
            Log.d("Hxin", "权限申请成功");
        }

        @Override
        public void onPermissionCanceled() {
            Log.w("Hxin", "权限申请被取消");
        }

        @Override
        public void onPermissionDenied() {
            Log.e("Hxin", "权限申请被拒绝");
        }
    };

    /**
     * 权限申请成功
     */
    void onPermissionSuccess();

    /**
     * 权限取消申请
     */
    void onPermissionCanceled();

    /**
     * 权限申请被拒绝
     */
    void onPermissionDenied();
}
