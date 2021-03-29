package com.njxm.smart.view;

import com.ntxm.smart.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class DividerLine extends View {

    private final int lineColor;
    private final int lineHeight;
    private final Paint mPaint = new Paint();

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
        this.setWillNotDraw(false);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DividerLine,
                defStyleAttr, defStyleRes);
        this.lineColor = a.getColor(R.styleable.DividerLine_lineColor, Color.BLACK);
        this.lineHeight = a.getDimensionPixelOffset(R.styleable.DividerLine_dividerHeight, 10);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.setColor(this.lineColor);
        this.mPaint.setStrokeWidth(this.lineHeight * 1.0f);
        canvas.drawLine(0, 0, this.getWidth(), this.lineHeight, this.mPaint);
    }
}
