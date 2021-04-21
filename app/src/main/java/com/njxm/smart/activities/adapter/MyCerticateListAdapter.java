/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities.adapter;

import java.util.List;

import android.app.Activity;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.activities.UserCertificateActivity;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.utils.StringUtils;
import com.ntxm.smart.R;

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

        Glide.with(this.activity).load(UrlPath.PATH_PICTURE_PREFIX.getUrl() + item.certificateImage)
                .into((ImageView) helper.getView(R.id.certificate_image_show));

        if (StringUtils.isNotEmpty(item.certificateName)) {
            helper.setText(R.id.certificate_name, item.certificateName);
        }
        helper.setText(R.id.certificate_update_state, item.status == 0 ? "未审核" : (item.status == 1 ?
                "审核通过" :
                "审核不通过"));
    }
}
