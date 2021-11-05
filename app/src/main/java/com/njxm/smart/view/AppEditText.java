/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.njxm.smart.utils.AppUtils;
import com.njxm.smart.utils.StringUtils;
import com.ntxm.smart.R;


/**
 * 登录页 编辑框 复合组件
 */
public class AppEditText extends ConstraintLayout implements View.OnClickListener {

    private static final int RIGHT_NONE = 0;
    private static final int RIGHT_TEXT = 1;
    private static final int RIGHT_IMAGE = 2;
    Handler handler = new Handler(Looper.getMainLooper());
    private int mRightType = com.njxm.smart.view.AppEditText.RIGHT_NONE;
    private View rootView;
    private AppCompatImageView mAppCompatImageView;
    private AppCompatEditText mAppCompatEditText;
    private AppCompatTextView mAppCompatTextView;
    private AppCompatImageView mDividerView;
    private boolean showPwd = false;
    private OnClickListener clickListener;

    public AppEditText(Context context) {
        this(context, null);
    }

    public AppEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getInit(context);

        Resources.Theme theme = context.getTheme();
        TypedArray ta = theme.obtainStyledAttributes(attrs, R.styleable.AppEditText, defStyleAttr, 0);
        int n = ta.getIndexCount();
        int inputType = ta.getInt(R.styleable.AppEditText_inputType, InputType.TYPE_CLASS_TEXT);
        boolean toRightOfEdit = false;
        String editText = null;
        String rightText = ta.getString(R.styleable.AppEditText_rightText);
        String editHint = null;
        int leftIconRes = 0;
        int rightIconRes = 0;
        int rightTextColor = Color.BLACK;

        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.AppEditText_edit_text:
                    editText = ta.getString(attr);
                    break;
                case R.styleable.AppEditText_hint:
                    editHint = ta.getString(attr);
                    break;
                case R.styleable.AppEditText_toRightOfEdit:
                    toRightOfEdit = ta.getBoolean(attr, false);
                    break;
                case R.styleable.AppEditText_right_type:
                    this.mRightType = ta.getInt(attr, AppEditText.RIGHT_NONE);
                    break;
                case R.styleable.AppEditText_leftIcon:
                    leftIconRes = ta.getResourceId(attr, 0);
                    break;
                case R.styleable.AppEditText_rightIcon:
                    rightIconRes = ta.getResourceId(attr, 0);
                    break;
                case R.styleable.AppEditText_rightTextColor:
                    rightTextColor = ta.getColor(attr, Color.BLACK);
                    break;
            }
        }
        this.mAppCompatImageView.setImageResource(leftIconRes);

        if (toRightOfEdit) {
            LayoutParams layoutParams = (LayoutParams) this.mDividerView.getLayoutParams();
            layoutParams.rightToRight = R.id.edit;
            this.mDividerView.setLayoutParams(layoutParams);
        }
        setValue(inputType, editText, editHint);

        if (this.mRightType == AppEditText.RIGHT_NONE) {
            this.mAppCompatTextView.setVisibility(View.GONE);
        } else if (this.mRightType == AppEditText.RIGHT_IMAGE) {
            this.mAppCompatTextView.setText("");
            this.mAppCompatTextView.setBackgroundResource(rightIconRes);
        } else {
            this.mAppCompatTextView.setBackground(null);
            this.mAppCompatTextView.setText(rightText);
            this.mAppCompatTextView.setTextColor(rightTextColor);
        }
        ta.recycle();

        this.mAppCompatTextView.setOnClickListener(this);
        this.mAppCompatImageView.setOnClickListener(this);
    }


    private void getInit(Context context) {
        init(context);
    }

    private void setValue(int inputType, String editText, String editHint) {
        this.mAppCompatEditText.setHint(editHint);
        this.mAppCompatEditText.setText(editText);
        this.mAppCompatEditText.setInputType(inputType);
    }

    private void init(Context context) {
        this.rootView = LayoutInflater.from(context).inflate(R.layout.item_custom_edit_text, this, true);
        this.mAppCompatImageView = this.rootView.findViewById(R.id.edit_icon);
        this.mAppCompatEditText = this.rootView.findViewById(R.id.edit);
        this.mAppCompatTextView = this.rootView.findViewById(R.id.edit_image_text);
        this.mDividerView = this.rootView.findViewById(R.id.divider1);
    }

    @Override
    public void onClick(View v) {
        if (this.clickListener != null) {
            this.clickListener.onClick(v);
        }
        if (this.mAppCompatTextView == v) {
            this.mAppCompatEditText.setInputType(this.showPwd ? EditorInfo.TYPE_TEXT_VARIATION_PASSWORD :
                    EditorInfo.TYPE_NULL);
            this.showPwd = !this.showPwd;
        }
    }

    public String getText() {
        return (this.mAppCompatEditText == null || this.mAppCompatEditText.getText() == null) ? "" :
                this.mAppCompatEditText.getText().toString().trim();
    }

    public void setRightText(CharSequence charSequence) {
        if (this.mAppCompatTextView != null && this.mRightType == com.njxm.smart.view.AppEditText.RIGHT_TEXT) {
            if (AppUtils.isMainThread()) {
                this.mAppCompatTextView.setText(charSequence);
            } else {
                this.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        com.njxm.smart.view.AppEditText.this.mAppCompatTextView.setText(charSequence);
                    }
                });
            }
        }
    }

    public void setRightText(int resId) {
        if (this.mAppCompatTextView != null && this.mRightType == com.njxm.smart.view.AppEditText.RIGHT_TEXT) {
            this.mAppCompatTextView.setText(resId);
        }
    }

    public void setRightImage(Drawable drawable) {
        if (this.mAppCompatTextView != null && this.mRightType == com.njxm.smart.view.AppEditText.RIGHT_IMAGE) {
            this.mAppCompatTextView.setBackground(drawable);
        }
    }

    public void setRightImage(int resId) {
        if (this.mAppCompatTextView != null && this.mRightType == com.njxm.smart.view.AppEditText.RIGHT_IMAGE) {
            this.mAppCompatTextView.setBackgroundResource(resId);
        }
    }

    /**
     * 实际EditText
     *
     * @return EditText对象
     */
    public AppCompatEditText getEditText() {
        return this.mAppCompatEditText;
    }

    public void setEditText(CharSequence charSequence) {
        if (this.mAppCompatEditText != null) {
            this.mAppCompatEditText.setText(charSequence);
        }
    }

    public void setEditText(int resId) {
        if (this.mAppCompatEditText != null) {
            this.mAppCompatEditText.setText(resId);
        }
    }

    /**
     * 清除EditText上面的文本
     */
    public void clearText() {
        if (this.mAppCompatEditText != null) {
            this.mAppCompatEditText.getEditableText().clear();
        }
    }

    /**
     * 判断文本是否为空
     *
     * @return true 为空
     */
    public boolean isEmpty() {
        if (this.mAppCompatEditText == null) {
            return true;
        }

        return StringUtils.isEmpty(this.mAppCompatEditText.getText().toString());
    }

    /**
     * 实际right view
     *
     * @return view
     */
    public AppCompatTextView getRightTextView() {
        return this.mAppCompatTextView;
    }

    public void setOnRightClickListener(OnClickListener onRightClickListener) {
        this.clickListener = onRightClickListener;
    }
}
