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
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ntxm.smart.R;
import com.sugar.android.common.utils.TextViewUtils;


/**
 * 登录页 编辑框 复合组件
 */
public class AppEditText extends ConstraintLayout implements View.OnClickListener {
    private final View rootView;
    private final AppCompatImageView iconIv;
    private final AppCompatEditText inputEditText;
    private final AppCompatTextView imageTv;
    private boolean showPwd = false;
    private OnClickListener clickListener;

    public AppEditText(Context context) {
        this(context, null);
    }

    /**/
    public AppEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.rootView = LayoutInflater.from(context).inflate(R.layout.item_custom_edit_text, this, true);
        this.iconIv = this.rootView.findViewById(R.id.edit_icon);
        this.inputEditText = this.rootView.findViewById(R.id.edit);
        this.imageTv = this.rootView.findViewById(R.id.edit_image_text);
    }


    public void setIconIv(@DrawableRes int resId) {
        com.sugar.android.common.utils.ViewUtils.setImageResource(iconIv, resId);
    }

    @Override
    public void onClick(View v) {
        if (this.clickListener != null) {
            this.clickListener.onClick(v);
        }
        if (this.imageTv == v) {
            this.inputEditText.setInputType(this.showPwd ? EditorInfo.TYPE_TEXT_VARIATION_PASSWORD :
                    EditorInfo.TYPE_NULL);
            this.showPwd = !this.showPwd;
        }
    }

    public String getText() {
        return (this.inputEditText == null || this.inputEditText.getText() == null) ? "" :
                this.inputEditText.getText().toString().trim();
    }

    public void setRightText(String content) {
        TextViewUtils.setText(imageTv, content);
    }

    /**
     * 实际EditText
     *
     * @return EditText对象
     */
    public AppCompatEditText getEditText() {
        return this.inputEditText;
    }

    public void setEditText(String text) {
        TextViewUtils.setText(inputEditText, text);
    }

    public void setEditText(int resId) {
        TextViewUtils.setText(inputEditText, resId);
    }

    /**
     * 清除EditText上面的文本
     */
    public void clearText() {
        TextViewUtils.setText(inputEditText, "");
    }

    /**
     * 实际right view
     *
     * @return view
     */
    public AppCompatTextView getRightTextView() {
        return this.imageTv;
    }

    public void setOnRightClickListener(OnClickListener onRightClickListener) {
        this.clickListener = onRightClickListener;
    }
}
