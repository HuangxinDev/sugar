package com.njxm.smart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class ContactsView extends LinearLayout {

    private final LinearLayout contactsLayout;

    public ContactsView(Context context) {
        this(context, null);
    }

    public ContactsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ContactsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.setOrientation(LinearLayout.VERTICAL);
        this.contactsLayout = new LinearLayout(context);
        this.contactsLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView textView = new TextView(context);
        textView.setText("巡检人");
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.addView(textView);
        this.addView(this.contactsLayout);
    }


    public void addData() {
        TextView textView = new TextView(this.contactsLayout.getContext());
        textView.setText("联系人");
        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.contactsLayout.addView(textView);
        this.contactsLayout.invalidate();
    }


}
