package com.njxm.smart.view;

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

import com.ntxm.smart.R;

public class ButtonBarItem extends LinearLayout {

    private ImageView itemIcon;
    private TextView itemText;

    private int iconNormalRes;
    private int iconSelectedRes;

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
        iconNormalRes = ta.getResourceId(R.styleable.ButtonBarItem_iconNormal, 0);
        iconSelectedRes = ta.getResourceId(R.styleable.ButtonBarItem_iconSelected, 0);
        float spacing = ta.getDimension(R.styleable.ButtonBarItem_spacing, 16);
        int stateListDrawable = ta.getResourceId(R.styleable.ButtonBarItem_icon, 0);
        ColorStateList textColor = ta.getColorStateList(R.styleable.ButtonBarItem_textColor);
        ta.recycle();
        LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        itemIcon = new ImageView(context);
        itemIcon.setLayoutParams(layoutParams);
//        itemIcon.setImageDrawable(stateListDrawable);
        itemIcon.setImageResource(stateListDrawable);
//        itemIcon.setBackgroundResource(res);
        itemText = new TextView(context);
        layoutParams.setMargins(0, (int) spacing, 0, 0);
        itemText.setLayoutParams(layoutParams);
        itemText.setGravity(Gravity.CENTER);
        itemText.setText(text);
        itemText.setTextColor(textColor);
        addView(itemIcon);
        addView(itemText);

        setActivated(false);
    }

    @Override
    public void setActivated(boolean activated) {
        super.setActivated(activated);
        if (itemIcon != null) {
            itemIcon.setImageResource(activated ? iconSelectedRes : iconNormalRes);
//            itemIcon.setActivated(activated);
        }
        if (itemText != null) {
//            itemText.setActivated(activated);
            itemText.setTextColor(Color.parseColor(activated ? "#FF007AFF" : "#FF96A1AD"));
        }
    }
}
