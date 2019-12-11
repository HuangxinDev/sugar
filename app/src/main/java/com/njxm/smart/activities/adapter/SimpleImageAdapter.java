package com.njxm.smart.activities.adapter;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ns.demo.R;

import java.util.List;

public class SimpleImageAdapter extends BaseQuickAdapter<Drawable, BaseViewHolder> {


    public SimpleImageAdapter(int layoutResId, @Nullable List<Drawable> data) {
        super(layoutResId, data);
    }

    public SimpleImageAdapter(@Nullable List<Drawable> data) {
        this(R.layout.item_simple_image_layout, data);
    }

    public SimpleImageAdapter() {
        this(R.layout.item_simple_image_layout, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Drawable item) {
        helper.setImageDrawable(R.id.item_image, item);
    }
}
