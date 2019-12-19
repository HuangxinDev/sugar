package com.njxm.smart.activities.adapter;

import android.app.Activity;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.activities.UserCertificateActivity;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.utils.StringUtils;
import com.ns.demo.R;

import java.util.List;

public class MyCerticateListAdapter extends BaseQuickAdapter<UserCertificateActivity.CertificateListItem, BaseViewHolder> {

    private Activity activity;

    public MyCerticateListAdapter(int layoutResId, @Nullable List<UserCertificateActivity.CertificateListItem> data) {
        super(layoutResId, data);
    }

    public MyCerticateListAdapter(Activity activity,
                                  @Nullable List<UserCertificateActivity.CertificateListItem> data) {
        super(R.layout.my_certificate_list_item, data);
        this.activity = activity;
    }

    public MyCerticateListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCertificateActivity.CertificateListItem item) {

        Glide.with(activity).load(HttpUrlGlobal.HTTP_MY_USER_HEAD_URL_PREFIX + item.certificateImage)
                .into((ImageView) helper.getView(R.id.certificate_image_show));

        if (StringUtils.isNotEmpty(item.certificateName)) {
            helper.setText(R.id.certificate_name, item.certificateName);
        }
        if (StringUtils.isNotEmpty(item.status)) {
            helper.setText(R.id.certificate_update_state, item.status);
        }
    }
}
