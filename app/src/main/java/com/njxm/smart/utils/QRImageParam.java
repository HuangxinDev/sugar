package com.njxm.smart.utils;

import android.text.TextUtils;

public class QRImageParam {
    private final String content;
    private final int width;
    private final int height;
    private final String encoding;
    private final String error_correction_level;
    private final String blankMargin;
    private final int blackBlock;
    private final int whiteBlock;

    /**
     * @param content                字符串内容
     * @param width                  二维码宽度
     * @param height                 二维码高度
     * @param encoding               编码方式（一般使用UTF-8）
     * @param error_correction_level 容错率 L：7% M：15% Q：25% H：35%
     * @param margin                 空白边距（二维码与边框的空白区域）
     * @param color_black            黑色色块
     * @param color_white            白色色块
     */
    public QRImageParam(String content, int width, int height, String encoding, String error_correction_level, String margin, int color_black, int color_white) {
        this.content = content;
        this.width = width;
        this.height = height;
        this.encoding = encoding;
        this.error_correction_level = error_correction_level;
        this.blankMargin = margin;
        this.blackBlock = color_black;
        this.whiteBlock = color_white;
    }

    public String getContent() {
        return content;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getError_correction_level() {
        return error_correction_level;
    }

    public String getBlankMargin() {
        return blankMargin;
    }

    public int getBlackBlock() {
        return blackBlock;
    }

    public int getWhiteBlock() {
        return whiteBlock;
    }

    public boolean isValid() {
        return getWidth() > 0 && getHeight() > 0 && !TextUtils.isEmpty(content);
    }

    
    public void angleRange(int start, int end) {
    }
}
