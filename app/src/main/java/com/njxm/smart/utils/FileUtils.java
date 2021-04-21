/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.utils;

import java.io.File;

import com.njxm.smart.SmartCloudApplication;

public class FileUtils {

    public static String getCameraDir() {

        File dirFile = new File(SmartCloudApplication.getApplication().getCacheDir(), "Android" +
                "/data/com.njxm.smart/camera");

        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile.getAbsolutePath();
    }
}
