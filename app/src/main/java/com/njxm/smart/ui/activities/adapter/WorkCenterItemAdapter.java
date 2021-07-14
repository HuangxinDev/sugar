/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.njxm.smart.bean.PermissionBean;
import com.njxm.smart.constant.UrlPath;
import com.ntxm.smart.R;

public class WorkCenterItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int ITEM_TITLE_TYPE = 0;
    public static final int ITEM_CONTENT_TYPE = 1;

    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public WorkCenterItemAdapter(List<MultiItemEntity> data) {
        super(data);
        this.addItemType(com.njxm.smart.ui.activities.adapter.WorkCenterItemAdapter.ITEM_TITLE_TYPE, R.layout.item_fragment_workcenter_list);
        this.addItemType(com.njxm.smart.ui.activities.adapter.WorkCenterItemAdapter.ITEM_CONTENT_TYPE, R.layout.item_workcenter);
    }

    public WorkCenterItemAdapter(Context context, List<MultiItemEntity> data) {
        this(data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        PermissionBean itemBean = (PermissionBean) item;

        switch (helper.getItemViewType()) {
            case com.njxm.smart.ui.activities.adapter.WorkCenterItemAdapter.ITEM_TITLE_TYPE:
                helper.setText(R.id.title, itemBean.getName());
                helper.setText(R.id.list_state, itemBean.isExpanded() ? "收起" : "展开");

                helper.setImageResource(R.id.list_icon_state, itemBean.isExpanded() ? R.mipmap.arrow_down : R.mipmap.arrow_detail);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (itemBean.isExpanded()) {
                            com.njxm.smart.ui.activities.adapter.WorkCenterItemAdapter.this.collapse(pos);
                        } else {
                            com.njxm.smart.ui.activities.adapter.WorkCenterItemAdapter.this.expand(pos);
                        }
                    }
                });
                break;
            case com.njxm.smart.ui.activities.adapter.WorkCenterItemAdapter.ITEM_CONTENT_TYPE:
                helper.setText(R.id.item_text, itemBean.getName());
                Glide.with(this.context).load(UrlPath.PATH_PICTURE_PREFIX.getUrl() + itemBean.getIcon())
                        .apply(new RequestOptions().placeholder(R.mipmap.mine_icon_real_name_auth))
                        .into((ImageView) helper.getView(R.id.item_image));
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (com.njxm.smart.ui.activities.adapter.WorkCenterItemAdapter.this.getItemViewType(position) == com.njxm.smart.ui.activities.adapter.WorkCenterItemAdapter.ITEM_TITLE_TYPE) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }
}
