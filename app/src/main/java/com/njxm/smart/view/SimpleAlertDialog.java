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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_dialog_layout);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        yesBtn = findViewById(R.id.dialog_yes);
        yesBtn.setOnClickListener(this);
        noBtn = findViewById(R.id.dialog_no);
        noBtn.setOnClickListener(this);
        messgaeContent = findViewById(R.id.dialog_content);


        Window window = getWindow();

        if (window != null) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.horizontalMargin = 0;
            window.setAttributes(wlp);
        }
    }

    public SimpleAlertDialog setMsg(int textId) {
        messgaeContent.setText(textId);
        return this;
    }

    public SimpleAlertDialog setMsg(CharSequence text) {
//        messgaeContent.setText(text);
        return this;
    }


    public SimpleAlertDialog setYesBtn(CharSequence text) {
        yesBtn.setText(text);
        return this;
    }


    public SimpleAlertDialog setYesBtn(int textId) {
        yesBtn.setText(textId);
        return this;
    }

    public SimpleAlertDialog setNoBtn(CharSequence text) {
        noBtn.setText(text);
        return this;
    }

    public SimpleAlertDialog setNoBtn(int textId) {
        noBtn.setText(textId);
        return this;
    }

    public SimpleAlertDialog(Context context) {
        this(context, 0);
    }


    public SimpleAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private DialogOnClickListener simpleDialogOnClickListener;

    public SimpleAlertDialog setDialogClickListener(DialogOnClickListener listener) {
        this.simpleDialogOnClickListener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (simpleDialogOnClickListener != null) {
            simpleDialogOnClickListener.onClick(this, v == yesBtn);
        }
    }

    public interface DialogOnClickListener {
        void onClick(DialogInterface dialog, boolean isPositiveBtn);
    }
}
