package com.hxin.common.perrmission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.WeakHashMap;

/**
 * Created by Hxin on 2020/3/27
 * Function: 权限工具类
 */
public final class PermissionUtil {

    private static WeakHashMap<String, Integer> MIN_SDK_PERMISSIONS = new WeakHashMap<>();

    static {
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", 23);
    }

    /**
     * @param context     上下文
     * @param permissions 待申请权限组
     * @return return true if all permission granted
     */
    public static boolean hasAllPermission(Context context, @NonNull String[] permissions) {
        for (String permission : permissions) {
            if (permissionExist(permission) && !hasSelfPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断单个权限是否申请
     *
     * @param permission 权限
     * @return true 已经申请
     */
    public static boolean hasSelfPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 验证权限结果
     *
     * @param grantResults 权限结果
     * @return 权限全部通过: true
     */
    public static boolean verifyPermissions(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean shouldShowRequestPermissionPop(@NonNull Context context, String[] permissions) {
        for (String permission : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限是否存在
     *
     * @param permssion 权限
     * @return retutn true if permission exist in SDK version
     */
    private static boolean permissionExist(@NonNull String permssion) {
        Integer minVersion = MIN_SDK_PERMISSIONS.get(permssion);
        return minVersion == null || Build.VERSION.SDK_INT >= minVersion;
    }
}
