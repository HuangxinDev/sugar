package com.sugar.android.common.utils;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组工具类
 *
 * @author huangxin
 * @date 2022/2/27
 */
public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    public static boolean isNotEmpty(List<?> list) {
        return list != null && list.size() > 0;
    }

    @Nullable
    public static <T> List<T> getSubList(List<T> data, int start, int end) {
        return isEmpty(data) || start < 0 || end >= data.size() || start > end ? null : data.subList(start, end);
    }

    public static <T> T getListElement(List<T> data, int index) {
        return isEmpty(data) || index < 0 || index >= data.size() ? null : data.get(index);
    }

    public static <T> List<T> getNonNullList(List<T> data) {
        if (data == null || data.isEmpty()) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        for (T item : data) {
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }
}
