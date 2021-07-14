/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities.adapter;

import java.util.List;

import android.app.Activity;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ntxm.smart.R;

public class SimpleImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    private boolean isShowDelete = false;

    private Activity activity;

    private RequestOptions requestOptions;

    public SimpleImageAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.init();
    }

    public SimpleImageAdapter(Activity activity, @Nullable List<String> data) {
        this(R.layout.item_simple_image_layout, data);
        this.activity = activity;
        this.init();
    }

    public SimpleImageAdapter(Activity activity) {
        this(R.layout.item_simple_image_layout, null);
        this.activity = activity;
        this.init();
    }

    private void init() {
        this.requestOptions = new RequestOptions().centerCrop();
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(this.activity)
                .load(item)
                .apply(this.requestOptions)
                .into((ImageView) helper.getView(R.id.item_image));
        helper.setVisible(R.id.item_cancel,
                this.isShowDelete && (helper.getAdapterPosition() != this.getData().size() - 1));
        if (this.isShowDelete) {
            helper.setNestView(R.id.item_cancel);
        }
    }

    public boolean isShowDelete() {
        return this.isShowDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.isShowDelete = showDelete;
    }
}
