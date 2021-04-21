/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IntDef;

import com.ntxm.smart.R;

public class StatusBarUtil {

    public final static int TYPE_MIUI = 0;
    public final static int TYPE_FLYME = 1;
    public final static int TYPE_M = 3; //6.0

    /**
     * 修改状态栏颜色，支持4.4以上版本
     *
     * @param colorId 颜色
     */
    public static void setStatusBarColor(Activity activity, int colorId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            StatusBarUtil.setTranslucentStatus(activity);
            window.setStatusBarColor(colorId);
        } /*else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTintManager,需要先将状态栏设置为透明
            setTranslucentStatus(activity);
            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(activity);
            systemBarTintManager.setStatusBarTintEnabled(true);//显示状态栏
            systemBarTintManager.setStatusBarTintColor(colorId);//设置状态栏颜色
        }*/
    }

    /**
     * 设置状态栏透明
     */
    @TargetApi(19)
    public static void setTranslucentStatus(Activity activity) {

        // 5.0以上系统状态栏透明
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        Window window = activity.getWindow();
        //清除透明状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //设置状态栏颜色必须添加
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);//设置透明
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //19
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    public static void setImmersiveStatusBar(Activity activity, boolean fontIconDark) {
        StatusBarUtil.setStatusBarColor(activity, Color.WHITE);
        StatusBarUtil.setTranslucentStatus(activity);
        if (fontIconDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StatusBarUtil.setStatusBarFontIconDark(activity, StatusBarUtil.TYPE_M);
            }/* else if (OSUtils.isMiui()) {
                setStatusBarFontIconDark(activity, TYPE_MIUI);
            } else if (OSUtils.isFlyme()) {
                setStatusBarFontIconDark(activity, TYPE_FLYME);
            } */ else {//其他情况下我们将状态栏设置为灰色，就不会看不见字体
                StatusBarUtil.setStatusBarColor(activity, Color.LTGRAY);//灰色
            }
        }
    }

    /**
     * 设置文字颜色
     */
    public static void setStatusBarFontIconDark(Activity activity, @ViewType int type) {
        switch (type) {
            case StatusBarUtil.TYPE_MIUI:
                StatusBarUtil.setMiuiUI(activity, true);
                break;
            case StatusBarUtil.TYPE_M:
                StatusBarUtil.setCommonUI(activity);
                break;
            case StatusBarUtil.TYPE_FLYME:
                StatusBarUtil.setFlymeUI(activity, true);
                break;
        }
    }

    //设置6.0的字体
    public static void setCommonUI(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    //设置Flyme的字体
    public static void setFlymeUI(Activity activity, boolean dark) {
        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置MIUI字体
    public static void setMiuiUI(Activity activity, boolean dark) {
        try {
            Window window = activity.getWindow();
            Class clazz = activity.getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStatusBar(Activity activity, boolean useThemestatusBarColor, boolean useStatusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            if (useThemestatusBarColor) {
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.background_white));
            } else {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
            layoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | layoutParams.flags);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !useStatusBarColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @IntDef({StatusBarUtil.TYPE_MIUI,
            StatusBarUtil.TYPE_FLYME,
            StatusBarUtil.TYPE_M})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {
    }
}

