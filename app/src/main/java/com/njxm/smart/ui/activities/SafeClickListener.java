package com.njxm.smart.ui.activities;

import android.view.View;

public abstract class SafeClickListener implements View.OnClickListener {
    long lastClickTime = 0;
    private int intervalTime = 800;

    public SafeClickListener() {
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    @Override
    public void onClick(View v) {
        if (isQuickClick()) {
            return;
        }
        lastClickTime = System.currentTimeMillis();
        this.onSafeClick(v);
    }

    public abstract void onSafeClick(View view);

    /**
     * 300ms内是否重复点击
     *
     * @return true:是
     */
    public boolean isQuickClick() {
        return System.currentTimeMillis() - lastClickTime <= intervalTime;
    }
}