/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities.adapter;

import java.util.List;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.jsonbean.EduTypeBean;
import com.ntxm.smart.R;

public class EduTypeAdapter extends BaseQuickAdapter<EduTypeBean, BaseViewHolder> {

    public EduTypeAdapter(@Nullable List<EduTypeBean> data) {
        super(R.layout.item_user_edu_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EduTypeBean item) {
        helper.setText(R.id.item_title, item.getSdName());
        helper.setVisible(R.id.icon, item.isSelected());
        helper.setVisible(R.id.divider1, helper.getAdapterPosition() != this.mData.size());
    }
}
