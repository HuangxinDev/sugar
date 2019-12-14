package com.njxm.smart.tools;

import android.Manifest;
import android.app.Activity;

public class PermissionManager {

    public static String[] sRequestPermissions = {
            Manifest.permission.CAMERA
    };

    public static void requestPermission(Activity context, int requestPermissionId, String... permission) {
        context.requestPermissions(permission, requestPermissionId);
    }
}
