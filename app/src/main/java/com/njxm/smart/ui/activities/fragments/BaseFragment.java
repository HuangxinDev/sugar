/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.njxm.smart.base.BaseRunnable;
import com.njxm.smart.utils.AppUtils;
import com.sugar.android.common.HandlerUtils;
import com.sugar.android.common.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

// 类似于android support v4包下的Fragment

/**
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment implements BaseRunnable {

    final String TAG;

    private View mContentView;

    /**
     * 懒加载标志位, 当加载过了就不再加载了
     */
    private boolean isLazyLoad = true;

    public BaseFragment() {
        this.TAG = "BaseFragment-" + this.getClass().getSimpleName();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public static void doOtherThing(Runnable runnable) {
        runnable.run();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(getClass().getSimpleName(), "==onCreate==");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.mContentView != null) {
            return this.mContentView;
        }
        this.mContentView = inflater.inflate(this.setLayoutResourceID(), container, false);
        ButterKnife.bind(this, this.mContentView);
        this.init();
        this.setUpView();
        this.setUpData();
        return this.mContentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(getClass().getSimpleName(), "==onResume==");
        if (this.isLazyLoad) {
            this.onLazyLoad();
            this.isLazyLoad = false;
        }
    }

    /**
     * 执行懒加载操作
     */
    protected abstract void onLazyLoad();

    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    /**
     * 一些View的相关操作
     */
    protected void setUpView() {

    }

    /**
     * 一些Data的相关操作
     */
    protected void setUpData() {

    }

    /**
     *
     */
    protected void init() {

    }

    View getContentView() {
        return this.mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Logger.d(this.getClass().getSimpleName(), "==onStart()==");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(this.getClass().getSimpleName(), "==onPause()==");
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
        Logger.d(this.getClass().getSimpleName(), "==onStop()==");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(this.getClass().getSimpleName(), "==onDestroy==");
    }

    /**
     * 在主线程中执行UI任务
     *
     * @param runnable
     */
    @Override
    public void invoke(Runnable runnable) {
        if (AppUtils.isMainThread()) {
            runnable.run();
        } else {
            HandlerUtils.postMain(runnable);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String msg) {

    }
}
