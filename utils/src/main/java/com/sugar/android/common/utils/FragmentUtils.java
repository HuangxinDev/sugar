package com.sugar.android.common.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
/**
 * 碎片工具类
 *
 * @author huangxin
 * @date 2022/2/26
 */
public final class FragmentUtils {
    private FragmentUtils() {
    }

    public static void showFragment(FragmentManager fragmentManager, int containerId, Fragment fragment) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().add(containerId, fragment).commitAllowingStateLoss();
        }
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }

    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }

    public static void hideFragment() {
    }
}
