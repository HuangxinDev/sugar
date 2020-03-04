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

    private float mContactsSpacing;
    private String title;
    private int testCount;

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
        mContactsSpacing = a.getDimensionPixelSize(R.styleable.ItemView_contacts_spacing, 0);
        title = a.getString(R.styleable.ItemView_title);
        testCount = a.getInt(R.styleable.ItemView_contacts_count, 0);
        a.recycle();
    }

    private TextView tvTitle;
    private ImageButton mAddBtn;
    private HorizontalScrollView mHorizontalScrollView;
    private LinearLayout mContactsLayout;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.item_custom, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tvTitle = rootView.findViewById(R.id.item_title);
        tvTitle.setText(title);
        mAddBtn = rootView.findViewById(R.id.item_add);
        mAddBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact(1);
                if (listener != null) {
                    listener.onAddItem();
                }
            }
        });
        mHorizontalScrollView = rootView.findViewById(R.id.horizontal_scrollview);
        mContactsLayout = rootView.findViewById(R.id.contact_layouts);
        addView(rootView);
        addContact(testCount);
    }

    public void addContact(int count) {
        for (int i = 0; i < count; i++) {
            ViewGroup contactView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.item_contacts, null);
            ImageView mHead = contactView.findViewById(R.id.item_head);
            TextView mContactName = contactView.findViewById(R.id.item_name);
            View view = contactView.findViewById(R.id.item_delete);
            view.setVisibility(mAddBtn.getVisibility());
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    View parent = (View) v.getParent();
                    int viewAt = mContactsLayout.indexOfChild(parent);
                    if (viewAt != -1) {
                        mContactsLayout.removeViewAt(viewAt);
                    }
                    int childCount = mContactsLayout.getChildCount();
                    if (viewAt == 0 && childCount > 0) {
                        mContactsLayout.getChildAt(0).setPadding(0, 0, 0, 0);
                    }

                    mHorizontalScrollView.setVisibility((mContactsLayout.getChildCount() == 0) ? View.GONE : View.VISIBLE);
                }
            });
            int childCount = mContactsLayout.getChildCount();
            if (childCount != 0) {
                contactView.setPadding((int) mContactsSpacing, 0, 0, 0);
            }
            mContactsLayout.addView(contactView);
        }
        mHorizontalScrollView.setVisibility((mContactsLayout.getChildCount() == 0) ? View.GONE : View.VISIBLE);
        mHorizontalScrollView.fullScroll(View.FOCUS_RIGHT);
    }


    public void setTitle(CharSequence text) {
        tvTitle.setText(text);
    }

    public void setTitle(@StringRes int resId) {
        tvTitle.setText(resId);
    }

    public void showAdd(boolean showAdd) {
        mAddBtn.setVisibility(showAdd ? VISIBLE : GONE);

        int visible = mAddBtn.getVisibility();

        for (int i = 0; i < mContactsLayout.getChildCount(); i++) {
            View view = mContactsLayout.getChildAt(i);
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

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onAddItem();
    }
}
