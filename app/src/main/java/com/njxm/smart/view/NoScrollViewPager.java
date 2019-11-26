package com.njxm.smart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NoScrollViewPager extends ViewPager {

    private boolean mNeedScroll = false; // 是否需要滚动

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }


    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNeedScroll(boolean needScroll) {
        this.mNeedScroll = needScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mNeedScroll) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mNeedScroll) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, mNeedScroll);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
}
