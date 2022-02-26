package com.sugar.android.common.view;

import android.view.View;

/**
 * 安全防暴力点击监听
 *
 * @author huangxin
 * @date 2022/2/26
 */
public abstract class SafeOnClickListener implements View.OnClickListener {
    /**
     * 上次点击控件的时间
     */
    private long lastClickTime = 0;
    /**
     * 防暴力点击的间隔时间
     */
    private int safeDuration = 800;

    public SafeOnClickListener() {
    }

    public SafeOnClickListener(int safeDuration) {
        this.safeDuration = safeDuration;
    }

    @Override
    public void onClick(View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime <= safeDuration) {
            return;
        }
        lastClickTime = clickTime;
        onSafeClick(v);
    }

    /**
     * 安全点击事件回调
     *
     * @param view view
     */
    public abstract void onSafeClick(View view);
}
