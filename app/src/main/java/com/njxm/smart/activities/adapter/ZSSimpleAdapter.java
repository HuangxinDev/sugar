package com.njxm.smart.activities.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ntxm.smart.R;

import java.util.ArrayList;
import java.util.List;

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
