package com.njxm.smart.utils;

import android.content.Context;
import android.content.res.Resources;

import com.njxm.smart.ui.activities.BaseActivity;

/**
 * @author huangxin
 * @date 2021/8/6
 */
public class ScreenUtils {
    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        LogTool.printD(BaseActivity.class, "status bar height: %s", statusBarHeight);
        return statusBarHeight;
    }
}
