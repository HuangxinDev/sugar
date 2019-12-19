package com.njxm.smart.activities.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.njxm.smart.activities.AboutUsActivity;
import com.njxm.smart.activities.MedicalReportActivity;
import com.njxm.smart.activities.PersonalInformationActivity;
import com.njxm.smart.activities.RealNameAuthenticationActivity;
import com.njxm.smart.activities.SettingsActivity;
import com.njxm.smart.activities.UserCertificateActivity;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.CircleImageView;
import com.ns.demo.R;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * "我的" Fragment
 */
public class PersonalFragment extends BaseFragment implements View.OnClickListener,
        HttpCallBack {


    private static final int REQUEST_USER_INFO_BASE = 344;
    private static final int REQUEST_USER_INFO_DETAIL = 545;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        requestUserBaseNews();
        requestUserDetailNews();
    }

    private void requestUserDetailNews() {
        JSONObject object1 = new JSONObject();
        object1.put("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID));
        RequestBody formBody1 = FormBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE),
                object1.toJSONString());

        Request request1 = new Request.Builder().url(HttpUrlGlobal.HTTP_MY_USER_DETAIL_NEWS)
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                .post(formBody1)
                .build();

        HttpUtils.getInstance().postData(REQUEST_USER_INFO_DETAIL, request1, this);
    }

    private void requestUserBaseNews() {
        JSONObject object = new JSONObject();
        object.put("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID));
        RequestBody formBody = FormBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE),
                object.toString());
        Request request = new Request.Builder().url(HttpUrlGlobal.HTTP_MY_USER_INIT_NEWS)
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                .post(formBody)
                .build();

        HttpUtils.getInstance().postData(REQUEST_USER_INFO_BASE, request, this);
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

    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        if (!success) {
            return;
        }
        final UserBean bean = JSONObject.parseObject(data, UserBean.class);
        if (requestId == REQUEST_USER_INFO_BASE) {
            SPUtils.putValue(KeyConstant.KEY_USERNAME, bean.getUserName());
            SPUtils.putValue(KeyConstant.KEY_MEDICAL_STATUS, bean.getMedicalStatus());
        } else if (requestId == REQUEST_USER_INFO_DETAIL) {
            SPUtils.putValue(KeyConstant.KEY_USER_TEL_PHONE, bean.getPhone());
            SPUtils.putValue(KeyConstant.KEY_USER_ADDRESS, bean.getAllAddress());
            SPUtils.putValue(KeyConstant.KEY_USER_DETAIL_ADDRESS, bean.getAddress());
            SPUtils.putValue(KeyConstant.KEY_USER_EDUCATION_STATUS, bean.getEducation());
            SPUtils.putValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT, bean.getContact());
            SPUtils.putValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT_PHONE, bean.getContactPhone());
            SPUtils.putValue(KeyConstant.KEY_USER_HEAD_ICON, bean.getIcon());
            SPUtils.putValue(KeyConstant.KEY_USER_FACE_URL, bean.getFaceUrl());
        }
        initData(bean);
    }

    @Override
    public void onFailed(String errMsg) {

    }

    private void initData(final UserBean bean) {
        invoke(new Runnable() {
            public void run() {
                mUserNewsBtn.setText(bean.getUserName());
                int medicalStatus =
                        Integer.parseInt(SPUtils.getValue(KeyConstant.KEY_MEDICAL_STATUS, "0"));

                mMedicalStarItem.setVisibility((medicalStatus == 0 || medicalStatus == 3) ?
                        View.VISIBLE : View.GONE);

                if (getActivity() != null && StringUtils.isNotEmpty(bean.getIcon())) {
                    Glide.with(getActivity())
                            .load(HttpUrlGlobal.HTTP_MY_USER_HEAD_URL_PREFIX + bean.getIcon())
                            .into(ivUserHead);
                }
            }
        });
    }

}
