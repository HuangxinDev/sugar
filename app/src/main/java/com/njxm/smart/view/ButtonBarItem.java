package com.njxm.smart.view;

import com.ntxm.smart.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class ButtonBarItem extends LinearLayout {

    private final ImageView itemIcon;
    private final TextView itemText;

    private final int iconNormalRes;
    private final int iconSelectedRes;

    public ButtonBarItem(Context context) {
        this(context, null);
    }

    public ButtonBarItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonBarItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ButtonBarItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ButtonBarItem);
        String text = ta.getString(R.styleable.ButtonBarItem_text);
        this.iconNormalRes = ta.getResourceId(R.styleable.ButtonBarItem_iconNormal, 0);
        this.iconSelectedRes = ta.getResourceId(R.styleable.ButtonBarItem_iconSelected, 0);
        float spacing = ta.getDimension(R.styleable.ButtonBarItem_spacing, 16);
        int stateListDrawable = ta.getResourceId(R.styleable.ButtonBarItem_icon, 0);
        ColorStateList textColor = ta.getColorStateList(R.styleable.ButtonBarItem_textColor);
        ta.recycle();
        LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        this.itemIcon = new ImageView(context);
        this.itemIcon.setLayoutParams(layoutParams);
//        itemIcon.setImageDrawable(stateListDrawable);
        this.itemIcon.setImageResource(stateListDrawable);
//        itemIcon.setBackgroundResource(res);
        this.itemText = new TextView(context);
        layoutParams.setMargins(0, (int) spacing, 0, 0);
        this.itemText.setLayoutParams(layoutParams);
        this.itemText.setGravity(Gravity.CENTER);
        this.itemText.setText(text);
        this.itemText.setTextColor(textColor);
        this.addView(this.itemIcon);
        this.addView(this.itemText);

        this.setActivated(false);
    }

    @Override
    public void setActivated(boolean activated) {
        super.setActivated(activated);
        if (this.itemIcon != null) {
            this.itemIcon.setImageResource(activated ? this.iconSelectedRes : this.iconNormalRes);
//            itemIcon.setActivated(activated);
        }
        if (this.itemText != null) {
//            itemText.setActivated(activated);
            this.itemText.setTextColor(Color.parseColor(activated ? "#FF007AFF" : "#FF96A1AD"));
        }
    }
}
