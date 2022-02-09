/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.ntxm.smart.R;

public class AlertDialogUtils {

    private OnButtonClickListener clickListener;

    public static AlertDialogUtils getInstance() {
        return new AlertDialogUtils();
    }

    /**
     * 带有确认取消按钮的自定义dialog
     *
     * @param context 上下文
     * @param message 显示的信息
     */
    public void showConfirmDialog(Context context, String message, String yesText,
                                  String noText) {
        View view =
                LayoutInflater.from(context).inflate(R.layout.simple_dialog_layout, null);
        AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        AppCompatTextView tvMsg = view.findViewById(R.id.dialog_content);
        AppCompatTextView tvConfirm = view.findViewById(R.id.dialog_yes);
        AppCompatTextView tvCancel = view.findViewById(R.id.dialog_no);
        View divider = view.findViewById(R.id.divider2);
        ViewUtils.setText(tvMsg, message);
        ViewUtils.setText(tvCancel, noText);
        ViewUtils.setText(tvConfirm, yesText);
        boolean isShowConfirmBtn = StringUtils.isNotEmpty(yesText);
        boolean isShowCancelBtn = StringUtils.isNotEmpty(noText);
        ViewUtils.setVisibility(tvConfirm, isShowConfirmBtn);
        ViewUtils.setVisibility(tvCancel, isShowCancelBtn);
        ViewUtils.setVisibility(divider, isShowCancelBtn && isShowConfirmBtn);
        tvCancel.setOnClickListener(v -> onClickCancel(dialog));
        tvConfirm.setOnClickListener(v -> onClickConfirm(dialog));
        dialog.show();
    }


    void onClickConfirm(@NonNull AlertDialog dialog) {
        try {
            clickListener.onPositiveButtonClick(dialog);
        } catch (Exception exception) {
            dismiss(dialog);
        }
    }

    void onClickCancel(@NonNull AlertDialog dialog) {
        try {
            clickListener.onNegativeButtonClick(dialog);
        } catch (Exception exception) {
            dismiss(dialog);
        }
    }

    private void dismiss(@NonNull AlertDialog dialog) {
        dialog.dismiss();
    }

    private boolean isNullListener() {
        return clickListener == null;
    }

    /**
     * 按钮点击回调接口
     */
    public interface OnButtonClickListener {
        /**
         * 确定按钮点击回调方法
         *
         * @param dialog 当前 AlertDialog，传入它是为了在调用的地方对 dialog 做操作，比如 dismiss()
         *               也可以在该工具类中直接  dismiss() 掉，就不用将 AlertDialog 对象传出去了
         */
        void onPositiveButtonClick(AlertDialog dialog);

        /**
         * 取消按钮点击回调方法
         *
         * @param dialog 当前AlertDialog
         */
        void onNegativeButtonClick(AlertDialog dialog);
    }

}
