package com.njxm.smart.activities.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.njxm.smart.base.BaseRunnable;
import com.njxm.smart.utils.AppUtils;
import com.njxm.smart.utils.LogTool;

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

    private Handler mHandler;

    /**
     * 懒加载标志位, 当加载过了就不再加载了
     */
    private boolean isLazyLoad = true;

    public BaseFragment() {
        this.TAG = "BaseFragment-" + this.getClass().getSimpleName();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
        LogTool.printD(TAG, "==onCreate==");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView != null) {
            return mContentView;
        }
        mContentView = inflater.inflate(setLayoutResourceID(), container, false);
        ButterKnife.bind(this, mContentView);
        init();
        setUpView();
        setUpData();
        return mContentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogTool.printD(TAG, "==onResume==");
        if (isLazyLoad) {
            onLazyLoad();
            isLazyLoad = false;
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
        return mContentView;
    }

    protected Handler getMainHandler() {
        return mHandler;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        LogTool.printD(TAG, "==onStart()==");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogTool.printD(TAG, "==onPause()==");
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
        LogTool.printD(TAG, "==onStop()==");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogTool.printD(TAG, "==onDestroy==");
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
            mHandler.post(runnable);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String msg) {

    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void doOtherThing(Runnable runnable) {
        runnable.run();
    }
}
