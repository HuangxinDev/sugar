package com.njxm.smart.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * Views工具类
 *
 * @author huangxin
 * @date 2021/8/10
 */
public final class ViewUtils {
    public static View findViewById(ViewGroup viewGroup, int resId) {
        if (viewGroup != null) {
            return viewGroup.findViewById(resId);
        }
        return null;
    }

    public static void setImageResource(ImageView view, @DrawableRes int resId) {
        if (view != null) {
            view.setImageResource(resId);
        }
    }

    public static void setText(TextView view, String text) {
        if (view != null) {
            view.setText(text);
        }
    }

    public static void setText(TextView view, @StringRes int stringRes) {
        if (view != null) {
            view.setText(stringRes);
        }
    }

    public static void setHint(TextView view, @StringRes int stringRes) {
        if (view != null) {
            view.setHint(stringRes);
        }
    }

    public static void setVisibility(View view, int visible) {
        if (view != null) {
            view.setVisibility(visible);
        }
    }

    public static void setVisibility(View view, boolean isVisible) {
        if (view != null) {
            view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    public static boolean isVisibility(View view) {
        return view != null && view.getVisibility() == View.VISIBLE;
    }
}
