/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities.fragments;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Hxin on 2020/3/28
 * Function: Fragment移动画面
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) {
            page.setAlpha(0f);
        } else if (position <= 1) {
            float scaleFactor = Math.max(com.njxm.smart.activities.fragments.ZoomOutPageTransformer.MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                page.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                page.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            page.setAlpha(com.njxm.smart.activities.fragments.ZoomOutPageTransformer.MIN_ALPHA +
                    (scaleFactor - com.njxm.smart.activities.fragments.ZoomOutPageTransformer.MIN_SCALE) /
                            (1 - com.njxm.smart.activities.fragments.ZoomOutPageTransformer.MIN_SCALE) * (1 - com.njxm.smart.activities.fragments.ZoomOutPageTransformer.MIN_ALPHA));
        } else {
            page.setAlpha(0f);
        }
    }
}
