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
