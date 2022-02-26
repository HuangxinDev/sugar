/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.utils;

import com.sugar.android.common.utils.StringUtils;

public class RegexUtil {
    /**
     * 正则手机号匹配
     */
    private static final String TEL_PHONE_REGEX = "1\\[\\d\\]{10}";

    public static boolean isMobilePhone(String mobile) {
        return StringUtils.isNotEmpty(mobile) && mobile.matches(TEL_PHONE_REGEX);
    }
}
