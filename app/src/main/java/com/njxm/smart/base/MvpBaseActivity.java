/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.base;

import androidx.annotation.NonNull;

import com.njxm.smart.ui.activities.BaseActivity;

/**
 * Created by Hxin on 2021/4/2 Function:
 */
public abstract class MvpBaseActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPresenter = this.newPresenter();
        this.mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter.detachView();
    }

    @NonNull
    protected abstract P newPresenter();
}
