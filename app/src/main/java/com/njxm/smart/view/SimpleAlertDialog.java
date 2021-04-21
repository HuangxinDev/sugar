/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatTextView;

import com.ntxm.smart.R;

public class SimpleAlertDialog extends AlertDialog implements OnClickListener {


    private AppCompatTextView yesBtn;
    private AppCompatTextView noBtn;
    private AppCompatTextView messgaeContent;

    private OnClickListener mOnClickListener;
    private OnClickListener mOnClickListener2;
    private DialogOnClickListener simpleDialogOnClickListener;

    public SimpleAlertDialog(Context context) {
        this(context, 0);
    }

    public SimpleAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.simple_dialog_layout);

        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        this.yesBtn = this.findViewById(R.id.dialog_yes);
        this.yesBtn.setOnClickListener(this);
        this.noBtn = this.findViewById(R.id.dialog_no);
        this.noBtn.setOnClickListener(this);
        this.messgaeContent = this.findViewById(R.id.dialog_content);


        Window window = this.getWindow();

        if (window != null) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.horizontalMargin = 0;
            window.setAttributes(wlp);
        }
    }

    public SimpleAlertDialog setMsg(int textId) {
        this.messgaeContent.setText(textId);
        return this;
    }

    public SimpleAlertDialog setMsg(CharSequence text) {
//        messgaeContent.setText(text);
        return this;
    }

    public SimpleAlertDialog setYesBtn(CharSequence text) {
        this.yesBtn.setText(text);
        return this;
    }

    public SimpleAlertDialog setYesBtn(int textId) {
        this.yesBtn.setText(textId);
        return this;
    }

    public SimpleAlertDialog setNoBtn(CharSequence text) {
        this.noBtn.setText(text);
        return this;
    }

    public SimpleAlertDialog setNoBtn(int textId) {
        this.noBtn.setText(textId);
        return this;
    }

    public SimpleAlertDialog setDialogClickListener(DialogOnClickListener listener) {
        this.simpleDialogOnClickListener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (this.simpleDialogOnClickListener != null) {
            this.simpleDialogOnClickListener.onClick(this, v == this.yesBtn);
        }
    }

    public interface DialogOnClickListener {
        void onClick(DialogInterface dialog, boolean isPositiveBtn);
    }
}
