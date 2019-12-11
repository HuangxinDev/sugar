package com.njxm.smart.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class BitmapUtils {

    public static Bitmap stringToBitmap(String imageStr) {
        byte[] encodeByte = android.util.Base64.decode(imageStr, android.util.Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }
}
