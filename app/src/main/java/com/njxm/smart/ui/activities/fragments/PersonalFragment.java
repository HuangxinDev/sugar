/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.ui.activities.AboutUsActivity;
import com.njxm.smart.ui.activities.MedicalReportActivity;
import com.njxm.smart.ui.activities.PersonalInformationActivity;
import com.njxm.smart.ui.activities.QRUserActivity;
import com.njxm.smart.ui.activities.RealNameAuthenticationActivity;
import com.njxm.smart.ui.activities.SettingsActivity;
import com.njxm.smart.ui.activities.UserCertificateActivity;
import com.njxm.smart.view.CircleImageView;
import com.ntxm.smart.R;
import com.sugar.android.common.utils.SPUtils;
import com.sugar.android.common.utils.StringUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;

/**
 * "我的" Fragment
 */
public class PersonalFragment extends BaseFragment implements View.OnClickListener {
    // 个人信息页面按钮
    private AppCompatTextView mUserNewsBtn;

    private CircleImageView ivUserHead;

    // 实名认证
    private View mRealItem;

    private View mRealStarItem;

    private View mMedicalItem;

    private View mMedicalStarItem;

    private View mCertItem;

    private View mAboutUsItem;

    private View mSettingItem;

    private LinearLayout llRealNamePop; // 实名认证提示

    private LinearLayout llMedicalPop; // 体检报告提示

    private static void requestUserBaseNews() {
        RequestEvent event = RequestEvent.newBuilder()
                .url(UrlPath.PATH_USER_BASE_NEWS.getUrl())
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .build();
        HttpUtils.getInstance().request(event);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.my_activity;
    }

    @Override
    protected void init() {
        super.init();
        this.mUserNewsBtn = this.getContentView().findViewById(R.id.user_name);
        this.mUserNewsBtn.setOnClickListener(this);
        this.mRealItem = this.getContentView().findViewById(R.id.my_real_item);
        this.mRealStarItem = this.getContentView().findViewById(R.id.my_real_item_star);
        this.mMedicalItem = this.getContentView().findViewById(R.id.my_medical_item);
        this.mMedicalStarItem = this.getContentView().findViewById(R.id.my_medical_item_star);
        this.mCertItem = this.getContentView().findViewById(R.id.my_cert_item);
        this.mAboutUsItem = this.getContentView().findViewById(R.id.my_about_us_item);
        this.mSettingItem = this.getContentView().findViewById(R.id.my_setting_item);
        this.ivUserHead = this.getContentView().findViewById(R.id.user_head);
        this.mRealItem.setOnClickListener(this);
        this.mMedicalItem.setOnClickListener(this);
        this.mCertItem.setOnClickListener(this);
        this.mAboutUsItem.setOnClickListener(this);
        this.mSettingItem.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @OnClick(R.id.qr_btn)
    protected void onViewClicked() {
        this.startActivity(new Intent(this.getActivity(), QRUserActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onLazyLoad() {
        RequestEvent requestEvent = RequestEvent.newBuilder()
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .url(UrlPath.PATH_USER_DETAILS_NEWS.getUrl())
                .build();
        HttpUtils.getInstance().request(requestEvent);
        com.njxm.smart.ui.activities.fragments.PersonalFragment.requestUserBaseNews();
    }

    @Override
    protected void setUpView() {
    }

    @Override
    protected void setUpData() {
    }

    @Override
    public void onClick(View v) {
        if (v == this.mUserNewsBtn) {
            Intent intent = new Intent(this.getActivity(), PersonalInformationActivity.class);
            this.startActivity(intent);
        } else if (v == this.mAboutUsItem) {
            Intent intent = new Intent(this.getActivity(), AboutUsActivity.class);
            this.startActivity(intent);
        } else if (v == this.mRealItem) {
            Intent intent = new Intent(this.getActivity(), RealNameAuthenticationActivity.class);
            this.startActivity(intent);
        } else if (v == this.mMedicalItem) {
            Intent intent = new Intent(this.getActivity(), MedicalReportActivity.class);
            this.startActivity(intent);
        } else if (v == this.mSettingItem) {
            Intent intent = new Intent(this.getActivity(), SettingsActivity.class);
            this.startActivity(intent);
        } else if (v == this.mCertItem) {
            this.startActivity(new Intent(this.getActivity(), UserCertificateActivity.class));
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void initData(UserBean bean) {
        this.mUserNewsBtn.setText(bean.getUserName());
        int medicalStatus =
                Integer.parseInt(SPUtils.getValue(KeyConstant.KEY_MEDICAL_STATUS, "0"));
        this.mMedicalStarItem.setVisibility((medicalStatus == 0 || medicalStatus == 3) ?
                View.VISIBLE : View.GONE);
        if (StringUtils.isNotEmpty(bean.getIcon())) {
            Glide.with(this.getActivity())
                    .load(bean.getIcon())
                    .apply(new RequestOptions().centerCrop().placeholder(R.mipmap.mine_icon_user_head).override(100, 100))
                    .into(this.ivUserHead);
        }
    }
}
