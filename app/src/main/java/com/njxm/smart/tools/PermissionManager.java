/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

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
