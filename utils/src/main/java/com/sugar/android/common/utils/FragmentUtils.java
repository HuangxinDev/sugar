package com.sugar.android.common.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 碎片工具类
 *
 * @author huangxin
 * @date 2022/2/26
 */
public final class FragmentUtils {
    private static final String TAG = "FragmentUtils";

    private FragmentUtils() {
    }

    public static void showFragment(FragmentManager fragmentManager, int containerId, Fragment fragment) {
        showFragment(fragmentManager, containerId, null, fragment);
    }

    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragment1 : ArrayUtils.getNonNullList(fragmentManager.getFragments())) {
            transaction.hide(fragment1);
        }
        Logger.i(TAG, "show fragment: " + fragment.getClass().getSimpleName());
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(containerId, fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    public static void showFragment(FragmentManager manager, int containerId, Fragment fromFragment, Fragment toFragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (fromFragment != null) {
            transaction.hide(fromFragment);
        }
        if (toFragment == null) {
            Logger.i(TAG, "toFragment is null.");
        } else if (toFragment.isAdded()) {
            transaction.show(toFragment);
        } else {
            transaction.add(containerId, toFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }

    public static void hideFragment() {
    }
}
