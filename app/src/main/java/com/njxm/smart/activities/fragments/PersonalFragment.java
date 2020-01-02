package com.njxm.smart.activities.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.njxm.smart.activities.AboutUsActivity;
import com.njxm.smart.activities.MedicalReportActivity;
import com.njxm.smart.activities.PersonalInformationActivity;
import com.njxm.smart.activities.QRUserActivity;
import com.njxm.smart.activities.RealNameAuthenticationActivity;
import com.njxm.smart.activities.SettingsActivity;
import com.njxm.smart.activities.UserCertificateActivity;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.CircleImageView;
import com.ns.demo.R;

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


    @Override
    protected int setLayoutResourceID() {
        return R.layout.my_activity;
    }

    @Override
    protected void init() {
        super.init();
        mUserNewsBtn = getContentView().findViewById(R.id.user_name);
        mUserNewsBtn.setOnClickListener(this);
        mRealItem = getContentView().findViewById(R.id.my_real_item);
        mRealStarItem = getContentView().findViewById(R.id.my_real_item_star);
        mMedicalItem = getContentView().findViewById(R.id.my_medical_item);
        mMedicalStarItem = getContentView().findViewById(R.id.my_medical_item_star);
        mCertItem = getContentView().findViewById(R.id.my_cert_item);
        mAboutUsItem = getContentView().findViewById(R.id.my_about_us_item);
        mSettingItem = getContentView().findViewById(R.id.my_setting_item);
        ivUserHead = getContentView().findViewById(R.id.user_head);
        mRealItem.setOnClickListener(this);
        mMedicalItem.setOnClickListener(this);
        mCertItem.setOnClickListener(this);
        mAboutUsItem.setOnClickListener(this);
        mSettingItem.setOnClickListener(this);

        requestUserBaseNews();
    }

    @Override
    public void onStart() {
        super.onStart();
        RequestEvent requestEvent = RequestEvent.newBuilder()
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .url(HttpUrlGlobal.HTTP_MY_USER_DETAIL_NEWS)
                .build();
        HttpUtils.getInstance().request(requestEvent);
    }

    @OnClick(R.id.qr_btn)
    protected void onViewClicked() {
        startActivity(new Intent(getActivity(), QRUserActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void requestUserBaseNews() {
        RequestEvent event = RequestEvent.newBuilder()
                .url(HttpUrlGlobal.HTTP_MY_USER_INIT_NEWS)
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .build();
        HttpUtils.getInstance().request(event);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
    }

    @Override
    public void onClick(View v) {
        if (v == mUserNewsBtn) {
            Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
            startActivity(intent);
        } else if (v == mAboutUsItem) {
            Intent intent = new Intent(getActivity(), AboutUsActivity.class);
            startActivity(intent);
        } else if (v == mRealItem) {
            Intent intent = new Intent(getActivity(), RealNameAuthenticationActivity.class);
            startActivity(intent);
        } else if (v == mMedicalItem) {
            Intent intent = new Intent(getActivity(), MedicalReportActivity.class);
            startActivity(intent);
        } else if (v == mSettingItem) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        } else if (v == mCertItem) {
            startActivity(new Intent(getActivity(), UserCertificateActivity.class));
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void initData(UserBean bean) {
        mUserNewsBtn.setText(bean.getUserName());
        int medicalStatus =
                Integer.parseInt(SPUtils.getValue(KeyConstant.KEY_MEDICAL_STATUS, "0"));

        mMedicalStarItem.setVisibility((medicalStatus == 0 || medicalStatus == 3) ?
                View.VISIBLE : View.GONE);

        if (StringUtils.isNotEmpty(bean.getIcon())) {
            Glide.with(getActivity())
                    .load(HttpUrlGlobal.HTTP_MY_USER_HEAD_URL_PREFIX + bean.getIcon())
                    .apply(new RequestOptions().centerCrop().placeholder(R.mipmap.mine_icon_user_head).override(100, 100))
                    .into(ivUserHead);
        }
    }

}
