package com.njxm.smart.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class BitmapUtils {

    public static Bitmap stringToBitmap(String imageStr) {
        byte[] encodeByte = android.util.Base64.decode(imageStr, android.util.Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 保存图片
     */
    public static File saveBitmap(Context context, Bitmap bitmap, String filename) {
        File tempFile = new File(context.getFilesDir(), filename);
        if (!tempFile.exists()) {
            tempFile.getParentFile().mkdirs();
        }
        tempFile.delete();
        try {
            tempFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(tempFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
//            bitmap.recycle();
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
