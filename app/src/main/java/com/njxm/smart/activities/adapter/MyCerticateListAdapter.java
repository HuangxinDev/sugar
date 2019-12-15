package com.njxm.smart.activities.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.activities.UserCertificateActivity;
import com.njxm.smart.utils.StringUtils;
import com.ns.demo.R;

import java.util.List;

public class MyCerticateListAdapter extends BaseQuickAdapter<UserCertificateActivity.CertificateListItem, BaseViewHolder> {

    public MyCerticateListAdapter(int layoutResId, @Nullable List<UserCertificateActivity.CertificateListItem> data) {
        super(layoutResId, data);
    }

    public MyCerticateListAdapter(@Nullable List<UserCertificateActivity.CertificateListItem> data) {
        super(R.layout.my_certificate_list_item, data);
    }

    public MyCerticateListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCertificateActivity.CertificateListItem item) {
        if (StringUtils.isNotEmpty(item.name)) {
            helper.setText(R.id.certificate_name, item.name);
        }
        if (StringUtils.isNotEmpty(item.state)) {
            helper.setText(R.id.certificate_name, item.state);
        }
    }
}
