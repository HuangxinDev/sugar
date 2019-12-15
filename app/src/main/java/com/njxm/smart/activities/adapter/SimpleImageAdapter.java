package com.njxm.smart.activities.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;

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
    protected void convert(final BaseViewHolder helper, final Drawable item) {
        helper.setImageDrawable(R.id.item_image, item);

        if (helper.getAdapterPosition() == getData().size() - 1) {
            helper.setVisible(R.id.item_cancel, false);
        } else {
            helper.setVisible(R.id.item_cancel, true);
            helper.setOnClickListener(R.id.item_cancel, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData().remove(item);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
