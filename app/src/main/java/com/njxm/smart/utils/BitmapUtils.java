/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sugar.android.common.utils.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public final class BitmapUtils {
    private static final String TAG = "BitmapUtils";

    private BitmapUtils() {
        // 禁止构造
    }

    public static Bitmap transform(@NonNull String bitmapStr) {
        byte[] bytes = android.util.Base64.decode(bitmapStr, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] transform(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 压缩保存图片
     *
     * @param bitmap    待压缩图片
     * @param imageFile 文件
     */
    public static File saveBitmap(Bitmap bitmap, File imageFile) {
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    public static File compressFile(File imageFile) {
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        return BitmapUtils.saveBitmap(bitmap, imageFile);
    }


    /**
     * 生成简单二维码
     *
     * @param param@return BitMap
     */
    public static Bitmap createQRCodeBitmap(QRImageParam param) {
        // 字符串内容判空
        if (TextUtils.isEmpty(param.getContent())) {
            return null;
        }
        // 宽和高>=0
        if (param.getWidth() < 0 || param.getHeight() < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(param.getEncoding())) {
                hints.put(EncodeHintType.CHARACTER_SET, param.getEncoding());
            }
            // 容错率设置
            if (!TextUtils.isEmpty(param.getError_correction_level())) {
                hints.put(EncodeHintType.ERROR_CORRECTION, param.getError_correction_level());
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(param.getBlankMargin())) {
                hints.put(EncodeHintType.MARGIN, param.getBlankMargin());
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(param.getContent(), BarcodeFormat.QR_CODE, param.getWidth(), param.getHeight(), hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[param.getWidth() * param.getHeight()];
            for (int y = 0; y < param.getHeight(); y++) {
                for (int x = 0; x < param.getWidth(); x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * param.getWidth() + x] = param.getBlackBlock();//黑色色块像素设置
                    } else {
                        pixels[y * param.getWidth() + x] = param.getWhiteBlock();// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(param.getWidth(), param.getHeight(), Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, param.getWidth(), 0, 0, param.getWidth(), param.getHeight());
            return bitmap;
        } catch (WriterException e) {
            Logger.e(TAG, "create qr bitmap exception: " + e.getMessage());
        }
        return null;
    }
}
