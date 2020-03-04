package com.njxm.smart.activities.adapter;

import android.app.Activity;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.activities.UserCertificateActivity;
import com.njxm.smart.utils.StringUtils;
import com.ntxm.smart.R;

import java.util.List;

public class MyCertificateAdapter extends BaseQuickAdapter<UserCertificateActivity.CertificateItem, BaseViewHolder> {

    private Activity context;

    public MyCertificateAdapter(@Nullable List<UserCertificateActivity.CertificateItem> data,
                                Activity context) {
        super(R.layout.my_certificate_add_item, data);
        this.context = context;
    }

    public MyCertificateAdapter(Activity activity) {
        super(R.layout.my_certificate_add_item);
        this.context = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCertificateActivity.CertificateItem item) {
        if (StringUtils.isNotEmpty(item.name)) {
            helper.setText(R.id.certificate_name, item.name);
        }

        if (item.file != null) {
            Glide.with(context).load(item.file)
                    .into((ImageView) helper.getView(R.id.certificate_image_show));
        }

        // 注册Item click事件
        helper.setNestView(R.id.certificate_add);
        helper.setNestView(R.id.certificate_image_show);


        helper.setVisible(R.id.divider1, helper.getAdapterPosition() != mData.size() - 1);
    }
}
