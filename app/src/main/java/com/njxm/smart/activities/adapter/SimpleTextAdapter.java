package com.njxm.smart.activities.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.jsonbean.AddressBean;
import com.ns.demo.R;

import java.util.List;

public class SimpleTextAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {

    public SimpleTextAdapter(@Nullable List<AddressBean> data) {
        super(R.layout.item_simple_text_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {
        helper.setText(R.id.simple_text, item.getName());
    }
}
