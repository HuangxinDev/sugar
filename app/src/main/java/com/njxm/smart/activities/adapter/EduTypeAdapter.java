package com.njxm.smart.activities.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.jsonbean.EduTypeBean;
import com.ntxm.smart.R;

import java.util.List;

public class EduTypeAdapter extends BaseQuickAdapter<EduTypeBean, BaseViewHolder> {

    public EduTypeAdapter(@Nullable List<EduTypeBean> data) {
        super(R.layout.item_user_edu_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EduTypeBean item) {
        helper.setText(R.id.item_title, item.getSdName());
        helper.setVisible(R.id.icon, item.isSelected());
        helper.setVisible(R.id.divider1, helper.getAdapterPosition() != mData.size());
    }
}
