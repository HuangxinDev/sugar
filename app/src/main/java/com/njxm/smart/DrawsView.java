package com.njxm.smart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class DrawsView extends View {

    public float currentX = 40;
    public float currentY = 50;

    Paint p = new Paint();

    public DrawsView(Context context) {
        super(context);

    }

    public DrawsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.p.setColor(Color.RED);
        canvas.drawCircle(this.currentX, this.currentY, 15, this.p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.currentX = event.getX();
        this.currentY = event.getY();
        this.invalidate();
        return true;
    }
}
