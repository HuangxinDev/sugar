package com.njxm.smart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.ns.demo.R;

public class DividerLine extends View {

    private int lineColor;
    private int lineHeight;

    public DividerLine(Context context) {
        this(context, null);
    }

    public DividerLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DividerLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DividerLine,
                defStyleAttr, defStyleRes);
        lineColor = a.getColor(R.styleable.DividerLine_lineColor, Color.BLACK);
        lineHeight = a.getDimensionPixelOffset(R.styleable.DividerLine_dividerHeight, 10);
        a.recycle();
    }

    private Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(lineHeight * 1.0f);
        canvas.drawLine(0, 0, getWidth(), lineHeight, mPaint);
    }
}
