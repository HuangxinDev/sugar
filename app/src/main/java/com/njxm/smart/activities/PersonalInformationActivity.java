package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.ns.demo.R;

/**
 * 个人信息页面（由主页 我的-姓名跳转）
 */
public class PersonalInformationActivity extends BaseActivity {

    private View mUserBaseNews;
    private ImageView mUserNewsImage;
    private View mUserBaseDetailNews;

    private TextView tvUserName;
    private TextView tvUserPhone;
    private TextView tvUserAddress;
    private TextView tvUserEducation;

    private LinearLayout llUserPhone;
    private LinearLayout llUserInputFace;
    private LinearLayout llUserEducation;
    private LinearLayout llUserAddress;
    private RelativeLayout rlUserEmergencyContact;

    private boolean showDetails = false;


    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("个人信息");
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        mUserBaseNews = findViewById(R.id.news_user_base_new);
        mUserNewsImage = findViewById(R.id.news_user_base_new_detail);
        mUserBaseDetailNews = findViewById(R.id.news_user_base_new_sub);
        mUserBaseNews.setOnClickListener(this);

        llUserPhone = findViewById(R.id.news_user_phone_ll);
        llUserPhone.setOnClickListener(this);
        llUserInputFace = findViewById(R.id.news_user_input_face_ll);
        llUserInputFace.setOnClickListener(this);
        llUserEducation = findViewById(R.id.news_user_education_item);
        llUserEducation.setOnClickListener(this);
        llUserAddress = findViewById(R.id.news_user_address_item);
        llUserAddress.setOnClickListener(this);
        rlUserEmergencyContact = findViewById(R.id.news_user_emergency_contact);
        rlUserEmergencyContact.setOnClickListener(this);

        tvUserName = findViewById(R.id.news_user_name);
        tvUserPhone = findViewById(R.id.news_user_phone);
        tvUserAddress = findViewById(R.id.news_user_address);
        tvUserEducation = findViewById(R.id.news_user_education);

        tvUserName.setText(SPUtils.getStringValue(KeyConstant.KEY_USERNAME));

        tvUserPhone.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_TEL_PHONE));
        tvUserAddress.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_ADDRESS));
        final String eduStatus = SPUtils.getStringValue(KeyConstant.KEY_USER_EDUCATION_STATUS);
        tvUserEducation.setText(StringUtils.isEmpty(eduStatus) ? "未上传" : eduStatus);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mUserBaseNews) {
            showDetails = !showDetails;
            mUserBaseDetailNews.setVisibility(showDetails ? View.VISIBLE : View.GONE);
            mUserNewsImage.setImageResource(showDetails ? R.mipmap.arrow_down : R.mipmap.arrow_detail);
        } else if (v == llUserPhone) {
            startActivity(new Intent(this, UserPhoneActivity.class));
        }
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        finish();
    }
}
