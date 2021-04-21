/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.ntxm.smart.R;

public class ItemView extends LinearLayout {

    private final float mContactsSpacing;
    private final String title;
    private final int testCount;
    private TextView tvTitle;
    private ImageButton mAddBtn;
    private HorizontalScrollView mHorizontalScrollView;
    private LinearLayout mContactsLayout;
    private OnItemClickListener listener;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ItemView, defStyleAttr,
                defStyleRes);
        this.mContactsSpacing = a.getDimensionPixelSize(R.styleable.ItemView_contacts_spacing, 0);
        this.title = a.getString(R.styleable.ItemView_title);
        this.testCount = a.getInt(R.styleable.ItemView_contacts_count, 0);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View rootView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_custom, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.tvTitle = rootView.findViewById(R.id.item_title);
        this.tvTitle.setText(this.title);
        this.mAddBtn = rootView.findViewById(R.id.item_add);
        this.mAddBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                com.njxm.smart.view.ItemView.this.addContact(1);
                if (com.njxm.smart.view.ItemView.this.listener != null) {
                    com.njxm.smart.view.ItemView.this.listener.onAddItem();
                }
            }
        });
        this.mHorizontalScrollView = rootView.findViewById(R.id.horizontal_scrollview);
        this.mContactsLayout = rootView.findViewById(R.id.contact_layouts);
        this.addView(rootView);
        this.addContact(this.testCount);
    }

    public void addContact(int count) {
        for (int i = 0; i < count; i++) {
            ViewGroup contactView = (ViewGroup) LayoutInflater.from(this.getContext()).inflate(R.layout.item_contacts, null);
            ImageView mHead = contactView.findViewById(R.id.item_head);
            TextView mContactName = contactView.findViewById(R.id.item_name);
            View view = contactView.findViewById(R.id.item_delete);
            view.setVisibility(this.mAddBtn.getVisibility());
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    View parent = (View) v.getParent();
                    int viewAt = com.njxm.smart.view.ItemView.this.mContactsLayout.indexOfChild(parent);
                    if (viewAt != -1) {
                        com.njxm.smart.view.ItemView.this.mContactsLayout.removeViewAt(viewAt);
                    }
                    int childCount = com.njxm.smart.view.ItemView.this.mContactsLayout.getChildCount();
                    if (viewAt == 0 && childCount > 0) {
                        com.njxm.smart.view.ItemView.this.mContactsLayout.getChildAt(0).setPadding(0, 0, 0, 0);
                    }

                    com.njxm.smart.view.ItemView.this.mHorizontalScrollView.setVisibility((com.njxm.smart.view.ItemView.this.mContactsLayout.getChildCount() == 0) ? View.GONE : View.VISIBLE);
                }
            });
            int childCount = this.mContactsLayout.getChildCount();
            if (childCount != 0) {
                contactView.setPadding((int) this.mContactsSpacing, 0, 0, 0);
            }
            this.mContactsLayout.addView(contactView);
        }
        this.mHorizontalScrollView.setVisibility((this.mContactsLayout.getChildCount() == 0) ? View.GONE : View.VISIBLE);
        this.mHorizontalScrollView.fullScroll(View.FOCUS_RIGHT);
    }

    public void setTitle(CharSequence text) {
        this.tvTitle.setText(text);
    }

    public void setTitle(@StringRes int resId) {
        this.tvTitle.setText(resId);
    }

    public void showAdd(boolean showAdd) {
        this.mAddBtn.setVisibility(showAdd ? android.view.View.VISIBLE : android.view.View.GONE);

        int visible = this.mAddBtn.getVisibility();

        for (int i = 0; i < this.mContactsLayout.getChildCount(); i++) {
            View view = this.mContactsLayout.getChildAt(i);
            if (view instanceof ViewGroup) {
                for (int j = 0; j < ((ViewGroup) view).getChildCount(); j++) {
                    View child = ((ViewGroup) view).getChildAt(j);
                    if (child.getId() == R.id.item_delete) {
                        child.setVisibility(visible);
                        break;
                    }
                }
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onAddItem();
    }
}
