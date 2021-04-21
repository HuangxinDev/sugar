/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities.adapter;

import java.util.List;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.jsonbean.AddressBean;
import com.ntxm.smart.R;

public class SimpleTextAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {

    public SimpleTextAdapter(@Nullable List<AddressBean> data) {
        super(R.layout.item_simple_text_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {
        helper.setText(R.id.simple_text, item.getName());
    }
}
