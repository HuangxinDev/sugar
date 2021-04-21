/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.njxm.smart.utils.LogTool;
import com.ntxm.smart.R;

public class DotsView extends LinearLayout {

    private Context context;
    private int selected = -1;

    public DotsView(Context context) {
        this(context, null);
    }

    public DotsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
        this.context = context;
    }

    public DotsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < this.getChildCount(); i++) {
            if (i != this.getChildCount() - 1) {
                super.onLayout(changed, l + 24, t, r + 24, b);
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setDotSize(int size) {
        if (size <= 0) {
            return;
        }

//        normal.setMargins(10, 0, 10, 0);
//        selectedParams.setMargins(9, 0, 9, 0);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(11, 0, 11, 0);
        ImageView imageView;
        for (int i = 0; i < size; i++) {
            imageView = new ImageView(this.context);
            imageView.setBackgroundResource(R.drawable.dot_normal);
            imageView.setLayoutParams(params);
            this.addView(imageView);
        }

        // 刷新
        this.setSelected(0);
    }

//    private LinearLayout.LayoutParams normal = new LinearLayout.LayoutParams(6 * 4, 6 * 4);
//    private LinearLayout.LayoutParams selectedParams = new LinearLayout.LayoutParams(7 * 4, 7 * 4);

    public void setSelected(int position) {
        // 刷新
        if (position == this.selected) {
            return;
        }

        ImageView view = (ImageView) this.getChildAt(position);
        if (view == null) {
            LogTool.printD("image view is null");
            return;
        }
        view.setEnabled(false);
        View src = this.getChildAt(this.selected);
        if (src != null) {
            src.setEnabled(true);
        }
        this.selected = position;
        this.invalidate();
    }
}
