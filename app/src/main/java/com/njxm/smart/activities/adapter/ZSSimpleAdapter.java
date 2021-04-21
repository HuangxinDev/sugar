/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities.adapter;

import java.util.ArrayList;
import java.util.List;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ntxm.smart.R;

public class ZSSimpleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ZSSimpleAdapter() {
        super(R.layout.item_simple_text_layout);
//        addData(loadData());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.simple_text, item);
    }

    private List<String> loadData() {
        List<String> data = new ArrayList<>();
        data.add("AAA");
        data.add("BBB");
        data.add("CCC");
        data.add("DDD");
        return data;
    }
}
