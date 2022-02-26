/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities.adapter;

import android.app.Activity;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.ui.activities.UserCertificateActivity;
import com.ntxm.smart.R;
import com.sugar.android.common.utils.StringUtils;

import java.util.List;

public class MyCertificateAdapter extends BaseQuickAdapter<UserCertificateActivity.CertificateItem, BaseViewHolder> {

    private final Activity context;

    public MyCertificateAdapter(@Nullable List<UserCertificateActivity.CertificateItem> data,
                                Activity context) {
        super(R.layout.my_certificate_add_item, data);
        this.context = context;
    }

    public MyCertificateAdapter(Activity activity) {
        super(R.layout.my_certificate_add_item);
        this.context = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCertificateActivity.CertificateItem item) {
        if (StringUtils.isNotEmpty(item.name)) {
            helper.setText(R.id.certificate_name, item.name);
        }

        if (item.file != null) {
            Glide.with(this.context).load(item.file)
                    .into((ImageView) helper.getView(R.id.certificate_image_show));
        }

        // 注册Item click事件
        helper.setNestView(R.id.certificate_add);
        helper.setNestView(R.id.certificate_image_show);


        helper.setVisible(R.id.divider1, helper.getAdapterPosition() != this.mData.size() - 1);
    }
}
