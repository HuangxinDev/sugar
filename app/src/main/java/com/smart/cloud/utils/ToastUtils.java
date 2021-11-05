package com.smart.cloud.utils;

import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

/**
 * @author huangxin
 * @date 2021/8/6
 */
public class ToastUtils {
    /**
     * 显示弹窗错误信息
     *
     * @param format
     * @param objects
     */
    public static void showToast(String format, Object... objects) {
        if (StringUtils.isEmpty(format)) {
            return;
        }

        String toastMsg = (objects != null && objects.length != 0) ?
                String.format(Locale.US, format, objects) : format;
        EventBus.getDefault().post(new ToastEvent(toastMsg));
    }
}
