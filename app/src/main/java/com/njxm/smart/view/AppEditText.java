package com.njxm.smart.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.njxm.smart.utils.StringUtils;
import com.ns.demo.R;


/**
 * 登录页 编辑框 复合组件
 */
public class AppEditText extends ConstraintLayout implements View.OnClickListener {

    private int mRightType = RIGHT_NONE;
    private static final int RIGHT_NONE = 0;
    private static final int RIGHT_TEXT = 1;
    private static final int RIGHT_IMAGE = 2;
    private View rootView;
    private AppCompatImageView mAppCompatImageView;
    private AppCompatEditText mAppCompatEditText;
    private AppCompatTextView mAppCompatTextView;
    private AppCompatImageView mDividerView;

    private boolean showPwd = false;

    public AppEditText(Context context) {
        this(context, null);
    }

    public AppEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (rootView == null) {
            rootView = LayoutInflater.from(context).inflate(R.layout.item_custom_edit_text, this, true);
            mAppCompatImageView = rootView.findViewById(R.id.edit_icon);
            mAppCompatEditText = rootView.findViewById(R.id.edit);
            mAppCompatTextView = rootView.findViewById(R.id.edit_image_text);
            mDividerView = rootView.findViewById(R.id.divider1);
        }

        final Resources.Theme theme = context.getTheme();
        TypedArray ta = theme.obtainStyledAttributes(attrs, R.styleable.AppEditText, defStyleAttr, 0);

        int n = ta.getIndexCount();

        int inputType = ta.getInt(R.styleable.AppEditText_inputType, EditorInfo.TYPE_CLASS_TEXT);
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
                    mRightType = ta.getInt(attr, RIGHT_NONE);
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
        mAppCompatImageView.setImageResource(leftIconRes);

        if (toRightOfEdit) {
            LayoutParams layoutParams = (LayoutParams) mDividerView.getLayoutParams();
            layoutParams.rightToRight = R.id.edit;
            mDividerView.setLayoutParams(layoutParams);
        }
        mAppCompatEditText.setHint(editHint);
        mAppCompatEditText.setText(editText);
        mAppCompatEditText.setInputType(inputType);

        if (mRightType == RIGHT_NONE) {
            mAppCompatTextView.setVisibility(GONE);
        } else if (mRightType == RIGHT_IMAGE) {
            mAppCompatTextView.setText("");
            mAppCompatTextView.setBackgroundResource(rightIconRes);
        } else {
            mAppCompatTextView.setBackground(null);
            mAppCompatTextView.setText(rightText);
            mAppCompatTextView.setTextColor(rightTextColor);
        }
        ta.recycle();

        mAppCompatTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mAppCompatTextView == v) {
            mAppCompatEditText.setInputType(showPwd ? EditorInfo.TYPE_TEXT_VARIATION_PASSWORD :
                    EditorInfo.TYPE_NULL);
            showPwd = !showPwd;
        }
    }

    public void setEditText(CharSequence charSequence) {
        if (mAppCompatEditText != null) {
            mAppCompatEditText.setText(charSequence);
        }
    }

    public void setEditText(int resId) {
        if (mAppCompatEditText != null) {
            mAppCompatEditText.setText(resId);
        }
    }

    public String getText() {
        return (mAppCompatEditText == null || mAppCompatEditText.getText() == null) ? "" :
                mAppCompatEditText.getText().toString().trim();
    }

    public void setRightText(CharSequence charSequence) {
        if (mAppCompatTextView != null && mRightType == RIGHT_TEXT) {
            mAppCompatTextView.setText(charSequence);
        }
    }

    public void setRightText(int resId) {
        if (mAppCompatTextView != null && mRightType == RIGHT_TEXT) {
            mAppCompatTextView.setText(resId);
        }
    }

    public void setRightImage(Drawable drawable) {
        if (mAppCompatTextView != null && mRightType == RIGHT_IMAGE) {
            mAppCompatTextView.setBackground(drawable);
        }
    }

    public void setRightImage(int resId) {
        if (mAppCompatTextView != null && mRightType == RIGHT_IMAGE) {
            mAppCompatTextView.setBackgroundResource(resId);
        }
    }

    /**
     * 实际EditText
     *
     * @return EditText对象
     */
    public AppCompatEditText getEditText() {
        return mAppCompatEditText;
    }

    /**
     * 清除EditText上面的文本
     */
    public void clearText() {
        if (mAppCompatEditText != null) {
            mAppCompatEditText.getEditableText().clear();
        }
    }

    /**
     * 判断文本是否为空
     *
     * @return true 为空
     */
    public boolean isEmpty() {
        if (mAppCompatEditText == null) {
            return true;
        }

        return StringUtils.isEmpty(mAppCompatEditText.getText().toString());
    }

    /**
     * 实际right view
     *
     * @return view
     */
    public AppCompatTextView getRightTextView() {
        return mAppCompatTextView;
    }
}
