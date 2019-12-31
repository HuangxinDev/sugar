package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.CircleImageView;
import com.ns.demo.R;

import org.greenrobot.eventbus.EventBus;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 个人信息页面（由主页 我的-姓名跳转）
 */
public class PersonalInformationActivity extends BaseActivity implements HttpCallBack {

    private View mUserBaseNews;
    private ImageView mUserNewsImage;
    private View mUserBaseDetailNews;

    private CircleImageView ivUserHead;
    private TextView tvUserName;
    private TextView tvUserPhone;
    private TextView tvUserAddress;
    private TextView tvUserEducation;
    private TextView tvUsereEmergencyContact;

    private LinearLayout llUserPhone;
    private LinearLayout llUserInputFace;
    private LinearLayout llUserEducation;
    private LinearLayout llUserAddress;
    private LinearLayout llUserEmergencyContact;

    private boolean showDetails = false;

    private static final int REQUEST_USER_HEAD = 412;


    @Override
    protected int setContentLayoutId() {
        return R.layout.my_user_activity;
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
        llUserEmergencyContact = findViewById(R.id.news_user_emergency_contact_item);
        llUserEmergencyContact.setOnClickListener(this);

        ivUserHead = findViewById(R.id.news_user_head);
        tvUserName = findViewById(R.id.news_user_name);
        tvUserPhone = findViewById(R.id.news_user_phone);
        tvUserAddress = findViewById(R.id.news_user_address);
        tvUserEducation = findViewById(R.id.news_user_education);
        tvUsereEmergencyContact = findViewById(R.id.news_user_emergency_contact);

        ivUserHead.setOnClickListener(this);

        tvUserName.setText(SPUtils.getStringValue(KeyConstant.KEY_USERNAME));

        tvUserPhone.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_TEL_PHONE));

        Glide.with(this)
                .load(HttpUrlGlobal.HTTP_MY_USER_HEAD_URL_PREFIX + SPUtils.getStringValue(KeyConstant.KEY_USER_HEAD_ICON))
                .apply(new RequestOptions().placeholder(R.mipmap.personal_news_head))
                .into(ivUserHead);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvUsereEmergencyContact.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT));
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
        } else if (v == llUserInputFace) {
            startActivity(new Intent(this, InputFaceActivity.class));
        } else if (v == llUserEducation) {
            startActivity(new Intent(this, UserEducationActivity.class));
        } else if (v == llUserEmergencyContact) {
            startActivity(new Intent(this, UserEmergencyContactActivity.class));
        } else if (v == ivUserHead) {
            takePhoto(1);
        } else if (v == llUserAddress) {
            startActivity(new Intent(this, UserAddressActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            invoke(new Runnable() {
                @Override
                public void run() {
                    if (photoFile != null && photoFile.exists() && photoFile.length() > 0) {
                        Glide.with(PersonalInformationActivity.this).load(photoFile).into(ivUserHead);
                        uploadHeadFile();
                    }
                }
            });
        }
    }

    private void uploadHeadFile() {
        MultipartBody builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .addFormDataPart("file", photoFile.getName(),
                        RequestBody.create(MediaType.parse("image/png"), photoFile))
                .build();

        Request request = new Request.Builder()
                .url(HttpUrlGlobal.HTTP_MY_USER_HEAD)
                .headers(HttpUtils.getPostHeaders())
                .post(builder)
                .build();

        HttpUtils.getInstance().postData(REQUEST_USER_HEAD, request, this);
    }

    @Override
    public void onSuccess(int requestId, final boolean success, int code, final String data) {
        super.onSuccess(requestId, success, code, data);
        if (requestId == REQUEST_USER_HEAD) {
            if (success) {
                showToast("头像上传成功");
                EventBus.getDefault().post(new RequestEvent());
            } else {
                showToast(data);
            }
        }
    }

    @Override
    public void onFailed(String errMsg) {

    }
}
