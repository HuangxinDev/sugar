package com.njxm.smart.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.njxm.smart.activities.BaseActivity;
import com.njxm.smart.base.BaseRunnable;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.utils.AppUtils;
import com.njxm.smart.utils.LogTool;

// 类似于android support v4包下的Fragment

/**
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment implements BaseRunnable, HttpCallBack {

    private View mContentView;
    private Context mContext;

    private Handler mHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogTool.printD("%s exec onCreateView", getClass().getSimpleName());
        if (mContentView != null) {
            return mContentView;
        }
        mContentView = inflater.inflate(setLayoutResourceID(), null);
        mContext = getContext();
        init();
        setUpView();
        setUpData();
        return mContentView;
    }

    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    /**
     * 一些View的相关操作
     */
    protected abstract void setUpView();

    /**
     * 一些Data的相关操作
     */
    protected abstract void setUpData();

    /**
     *
     */
    protected void init() {

    }

    public View getContentView() {
        return mContentView;
    }

    public Context getContext() {
        return mContext;
    }

    protected Handler getMainHandler() {
        return mHandler;
    }

    @Override
    public void invoke(Runnable runnable) {
        if (AppUtils.isMainThread()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).onSuccess(requestId, success, code, data);
        }
    }

    @Override
    public void onFailed(String errMsg) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).onFailed(errMsg);
        }
    }
}
