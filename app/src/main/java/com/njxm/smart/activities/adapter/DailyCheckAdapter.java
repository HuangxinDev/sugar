package com.njxm.smart.activities.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.jsonbean.DailyCheckTaskBean;
import com.ns.demo.R;

import java.util.List;

public class DailyCheckAdapter extends BaseQuickAdapter<DailyCheckTaskBean, BaseViewHolder> {

    public DailyCheckAdapter(int layoutResId, @Nullable List<DailyCheckTaskBean> data) {
        super(layoutResId, data);
    }

    public DailyCheckAdapter(@Nullable List<DailyCheckTaskBean> data) {
        this(R.layout.workcenter_daily_check_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyCheckTaskBean item) {
        helper.setText(R.id.task_content, item.getTaskContent());
        helper.setText(R.id.task_state, item.getTaskState() == 0 ? "未开始" : (item.getTaskState() ==
                1 ? "进行中" : "完成"));
        helper.setText(R.id.task_time, item.getCreateTime());
        helper.setVisible(R.id.divider3, helper.getAdapterPosition() != mData.size() - 1);
    }
}
