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
        setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            if (i != getChildCount() - 1) {
                super.onLayout(changed, l + 24, t, r + 24, b);
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int selected = -1;

    public void setDotSize(final int size) {
        if (size <= 0) {
            return;
        }

//        normal.setMargins(10, 0, 10, 0);
//        selectedParams.setMargins(9, 0, 9, 0);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(11, 0, 11, 0);
        ImageView imageView;
        for (int i = 0; i < size; i++) {
            imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.dot_normal);
            imageView.setLayoutParams(params);
            addView(imageView);
        }

        // 刷新
        setSelected(0);
    }

//    private LinearLayout.LayoutParams normal = new LinearLayout.LayoutParams(6 * 4, 6 * 4);
//    private LinearLayout.LayoutParams selectedParams = new LinearLayout.LayoutParams(7 * 4, 7 * 4);

    public void setSelected(int position) {
        // 刷新
        if (position == selected) {
            return;
        }

        ImageView view = (ImageView) getChildAt(position);
        if (view == null) {
            LogTool.printD("image view is null");
            return;
        }
        view.setEnabled(false);
        View src = getChildAt(selected);
        if (src != null) {
            src.setEnabled(true);
        }
        selected = position;
        invalidate();
    }
}
