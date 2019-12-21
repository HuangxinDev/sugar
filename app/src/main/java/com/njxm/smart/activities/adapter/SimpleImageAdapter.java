package com.njxm.smart.activities.adapter;

import android.app.Activity;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ns.demo.R;

import java.util.List;

public class SimpleImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    private boolean isShowDelete = false;

    private Activity activity;

    private RequestOptions requestOptions;

    public SimpleImageAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        init();
    }

    public SimpleImageAdapter(Activity activity, @Nullable List<String> data) {
        this(R.layout.item_simple_image_layout, data);
        this.activity = activity;
        init();
    }

    public SimpleImageAdapter(Activity activity) {
        this(R.layout.item_simple_image_layout, null);
        this.activity = activity;
        init();
    }

    private void init() {
        requestOptions = new RequestOptions().centerCrop();
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        Glide.with(activity)
                .load(item)
                .apply(requestOptions)
                .into((ImageView) helper.getView(R.id.item_image));
        helper.setVisible(R.id.item_cancel,
                isShowDelete && (helper.getAdapterPosition() != getData().size() - 1));
        if (isShowDelete) {
            helper.setNestView(R.id.item_cancel);
        }
    }

    public boolean isShowDelete() {
        return isShowDelete;
    }

    public void setShowDelete(boolean showDelete) {
        isShowDelete = showDelete;
    }
}
