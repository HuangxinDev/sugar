package com.njxm.smart.activities.fragments;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.AboutUsActivity;
import com.njxm.smart.activities.MedicalReportActivity;
import com.njxm.smart.activities.PersonalInformationActivity;
import com.njxm.smart.activities.RealNameAuthenticationActivity;
import com.njxm.smart.activities.SettingsActivity;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.R;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * "我的" Fragment
 */
public class PersonalFragment extends BaseFragment implements View.OnClickListener {


    private BaseQuickAdapter mPersonFragmentListAdapter;

    // 个人信息页面按钮
    private AppCompatTextView mUserNewsBtn;

    // 实名认证
    View mRealItem;
    View mRealStarItem;
    View mMedicalItem;
    View mMedicalStarItem;
    View mCertItem;
    View mAboutUsItem;
    View mSettingItem;

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
        mRealItem.setOnClickListener(this);
        mMedicalItem.setOnClickListener(this);
        mCertItem.setOnClickListener(this);
        mAboutUsItem.setOnClickListener(this);
        mSettingItem.setOnClickListener(this);


        JSONObject object = new JSONObject();
        object.put("id", SPUtils.getStringValue("id"));

        RequestBody formBody = FormBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE),
                object.toString());
        Request request = new Request.Builder().url("http://119.3.136.127:7776/api/sys/user/findUserForIndex")
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue("userAccount"))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue("token"))
                .post(formBody)
                .build();

        HttpUtils.getInstance().postData(0, request, new HttpCallBack() {
            @Override
            public void onSuccess(int requestId, boolean success, int code, final String data) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject json = JSONObject.parseObject(data);
                        mUserNewsBtn.setText(json.getString("userName"));
                    }
                });
            }

            @Override
            public void onFailed() {

            }
        });
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
        }
    }
}
