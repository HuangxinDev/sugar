package com.njxm.smart.utils;

import com.njxm.smart.SmartCloudApplication;

import java.io.File;

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
