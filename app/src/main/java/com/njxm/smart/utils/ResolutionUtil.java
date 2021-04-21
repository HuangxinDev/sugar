/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.utils;

import com.njxm.smart.SmartCloudApplication;

/**
 * 分辨率工具类
 */
public class ResolutionUtil {

    /**
     * dp转px
     *
     * @param dp dp值
     * @return px值
     */
    public static int dp2Px(int dp) {
        final float scale = SmartCloudApplication.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


}
