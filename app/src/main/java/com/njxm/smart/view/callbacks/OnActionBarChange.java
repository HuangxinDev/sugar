/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.view.callbacks;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageButton;

public interface OnActionBarChange {

    /**
     * 设置ActionBar标题
     *
     * @param title 标题
     */
    void setActionBarTitle(String title);


    /**
     * 是否显示该view
     *
     * @param view ActionBar view
     * @param show true 显示 false 隐藏
     */
    void showView(View view, boolean show);

    /**
     * 设置图片资源
     *
     * @param view
     * @param resourceId
     */
    void setImageResource(AppCompatImageButton view, int resourceId);

    /**
     * 点击左侧按钮:默认效果为退出页面
     */
    void onClickLeftBtn();

    /**
     * 点击右侧按钮
     */
    void onClickRightBtn();

    /**
     * 是否显示左侧按钮
     */
    void showLeftBtn(boolean show, int resourcesId);

    /**
     * 是否显示右侧按钮
     */
    void showRightBtn(boolean show, int resourcesId);

    /**
     * 是否显示标题
     */
    void showTitle(boolean show, String title);
}
