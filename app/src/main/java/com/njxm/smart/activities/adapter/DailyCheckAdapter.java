package com.njxm.smart.activities.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.jsonbean.DailyCheckTaskBean;
import com.ntxm.smart.R;

import java.util.List;

import androidx.annotation.Nullable;


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

        int state = item.getTaskState();
        helper.setBackgroundRes(R.id.task_state, state == 0 ? R.drawable.color_orange_state :
                (state == 1 ? R.drawable.color_green_state : R.drawable.color_blue_state));
        helper.setText(R.id.task_state, state == 0 ? "未开始" : state == 1 ? "进行中" : "完成");
        helper.setText(R.id.task_time, item.getCreateTime());
        helper.setVisible(R.id.divider3, helper.getAdapterPosition() != this.mData.size() - 1);
    }
}
