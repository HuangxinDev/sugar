package com.njxm.smart.activities.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.model.jsonbean.WorkCenterItemBean;
import com.njxm.smart.utils.LogTool;
import com.ns.demo.R;

import java.util.List;

public class TestAdapter extends BaseMultiItemQuickAdapter<WorkCenterItemBean, BaseViewHolder> {

    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TestAdapter(List<WorkCenterItemBean> data) {
        super(data);
        addItemType(WorkCenterItemBean.ITEM_TITLE_TYPE, R.layout.item_fragment_workcenter_list);
        addItemType(WorkCenterItemBean.ITEM_CONTENT_TYPE, R.layout.item_workcenter);
    }

    public TestAdapter(Context context, List<WorkCenterItemBean> data) {
        this(data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkCenterItemBean item) {
        switch (getItemViewType(helper.getAdapterPosition())) {
            case WorkCenterItemBean.ITEM_TITLE_TYPE:
                helper.setText(R.id.title, item.getName());
                break;
            case WorkCenterItemBean.ITEM_CONTENT_TYPE:
                helper.setText(R.id.item_text, item.getName());
                LogTool.printD("Sugar icon url: %s", item.getIcon());
                Glide.with(context).load(HttpUrlGlobal.HTTP_MY_USER_HEAD_URL_PREFIX + item.getIcon())
                        .apply(new RequestOptions().placeholder(R.mipmap.mine_icon_real_name_auth))
                        .into((ImageView) helper.getView(R.id.item_image));
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == WorkCenterItemBean.ITEM_TITLE_TYPE) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }
}
