package com.sugar.android.common.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * 视图工具类
 *
 * @author huangxin
 * @date 2022/2/26
 */
public final class ViewUtils {
    private ViewUtils() {
    }

    public static <V extends View> V findViewById(@NonNull Activity activity, int viewId) {
        return activity.findViewById(viewId);
    }

    /**
     * findViewById
     *
     * @param viewGroup 父视图
     * @param viewId    控件 Id
     * @return view
     */
    public static <V extends View> V findViewById(View viewGroup, int viewId) {
        return viewGroup.findViewById(viewId);
    }

    /**
     * 设置 View 的可见性
     */
    public static void setVisibility(View view, boolean isVisible) {
        if (view != null) {
            view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置 View 的可见性
     */
    public static void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    /**
     * 判断 View 是否可见
     *
     * @param view view
     * @return true：可见 false：不可见
     */
    public static boolean isVisibility(View view) {
        return view != null && (view.getVisibility() == View.VISIBLE);
    }

    /**
     * 设置 View 的监听事件
     *
     * @param view     视图
     * @param listener 监听器
     */
    public static void setOnClickListener(View view, View.OnClickListener listener) {
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 设置 View 使能
     *
     * @param view   view
     * @param enable 使能
     */
    public static void setEnable(View view, boolean enable) {
        if (view != null) {
            view.setEnabled(enable);
        }
    }

    public static void setImageResource(ImageView view, @DrawableRes int resId) {
        if (view != null) {
            view.setImageResource(resId);
        }
    }

    public static void setImageDrawable(ImageView view, Drawable drawable) {
        if (view != null) {
            view.setImageDrawable(drawable);
        }
    }

    public static void setBackgroundColor(View view, @ColorRes int color) {
        if (view != null) {
            view.setBackgroundColor(view.getResources().getColor(color));
        }
    }
}
