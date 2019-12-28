package com.njxm.smart.tools;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

public class PermissionManager {

    public static String[] sRequestPermissions = {
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static void requestPermission(Activity context, int requestPermissionId,
                                         String... permissions) {

        if (permissions == null || permissions.length == 0) {
            return;
        }
        for (String permission : permissions) {
            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                continue;
            }
            context.requestPermissions(new String[]{permission}, requestPermissionId);
        }
    }
}
