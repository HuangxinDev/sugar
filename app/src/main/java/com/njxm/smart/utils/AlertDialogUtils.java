package com.njxm.smart.utils;

import com.ntxm.smart.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;

public class AlertDialogUtils {

    private static OnButtonClickListener onButtonClickListener;

//    /**
//     * 弹出自定义样式的AlertDialog
//     *
//     * @param context 上下文
//     * @param title   AlertDialog的标题
//     * @param tv      点击弹出框选择条目后，要改变文字的TextView
//     * @param args    作为弹出框中item显示的字符串数组
//     */
//    public void showAlertDialog(Context context, String title, final TextView tv, final List<String> args) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//
//        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_salary, null);
//        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title_alert_dialog_salary);
//        ListView list = (ListView) view.findViewById(R.id.lv_alert_dialog_salary);
//        tvTitle.setText(title);
//        ListAdapter adpter = new ArrayAdapter<String>(context, R.layout.item_listview_salary, R.id.tv_item_listview_salary, args);
//        list.setAdapter(adpter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String str = args.get(position);
//                tv.setText(str);
//                if (onDialogItemSelectListener != null) {
//                    onDialogItemSelectListener.onItemSelect(str);
//                }
//                dialog.dismiss();
//            }
//        });
//
//        dialog.getWindow().setContentView(view);
//    }
private OnDialogItemSelectListener onDialogItemSelectListener;
    private boolean isShow = false;

    public static AlertDialogUtils getInstance() {
        return new AlertDialogUtils();
    }

    public void setOnDialogItemSelectListener(AlertDialogUtils.OnDialogItemSelectListener onDialogItemSelectListener) {
        this.onDialogItemSelectListener = onDialogItemSelectListener;
    }

    /**
     * 带有确认取消按钮的自定义dialog
     *
     * @param context 上下文
     * @param message 显示的信息
     */
    public void showConfirmDialog(Context context, String message, String yesText,
                                  String noText, OnButtonClickListener clickListener) {

        View view = LayoutInflater.from(context).inflate(R.layout.simple_dialog_layout, null);

        AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        AppCompatTextView tvMsg = view.findViewById(R.id.dialog_content);
        AppCompatTextView tvConfirm = view.findViewById(R.id.dialog_yes);
        AppCompatTextView tvCancel = view.findViewById(R.id.dialog_no);
        View divider = view.findViewById(R.id.divider2);
        tvMsg.setText(message);
        tvCancel.setText(noText);
        tvConfirm.setText(yesText);

        divider.setVisibility((StringUtils.isNotEmpty(yesText) && StringUtils.isNotEmpty(noText)) ? View.VISIBLE : View.INVISIBLE);
        tvConfirm.setVisibility(StringUtils.isEmpty(yesText) ? View.GONE : View.VISIBLE);
        tvCancel.setVisibility(StringUtils.isEmpty(noText) ? View.GONE : View.VISIBLE);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onNegativeButtonClick(dialog);
                } else {
                    dialog.dismiss();
                }
                com.njxm.smart.utils.AlertDialogUtils.this.isShow = false;
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onPositiveButtonClick(dialog);
                } else {
                    dialog.dismiss();
                }
                com.njxm.smart.utils.AlertDialogUtils.this.isShow = false;
            }
        });

        dialog.show();
        this.isShow = true;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        AlertDialogUtils.onButtonClickListener = onButtonClickListener;
    }

    /**
     * item选中回调接口
     */
    public interface OnDialogItemSelectListener {
        /**
         * item选中回调方法
         *
         * @param str 选中的item中的String
         */
        void onItemSelect(String str);
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
