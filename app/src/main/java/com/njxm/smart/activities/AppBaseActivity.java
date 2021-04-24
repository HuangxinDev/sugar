/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.njxm.smart.base.BasePresenter;
import com.njxm.smart.base.BaseView;
import com.njxm.smart.base.MvpBaseActivity;

/**
 * Created by Hxin on 2021/4/2 Function:
 */
public abstract class AppBaseActivity<V extends BaseView, P extends BasePresenter<V>> extends MvpBaseActivity<V, P> implements BaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
