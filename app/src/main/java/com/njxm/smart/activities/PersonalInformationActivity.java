package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.ns.demo.R;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 个人信息页面（由主页 我的-姓名跳转）
 */
public class PersonalInformationActivity extends BaseActivity implements HttpCallBack {


    private static final int REQUEST_USER_INFO = 532;

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

        tvUserName.setText(SPUtils.getStringValue("userName"));

        JSONObject object = new JSONObject();
        object.put("id", SPUtils.getStringValue("id"));

        RequestBody formBody = FormBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE),
                object.toString());

        Request request = new Request.Builder().url("http://119.3.136.127:7776/api/sys/user/findById")
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue("userAccount"))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue("token"))
                .post(formBody)
                .build();

        HttpUtils.getInstance().postData(REQUEST_USER_INFO, request, this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mUserBaseNews) {
            showDetails = !showDetails;
            mUserBaseDetailNews.setVisibility(showDetails ? View.VISIBLE : View.GONE);
            mUserNewsImage.setImageResource(showDetails ? R.mipmap.arrow_down : R.mipmap.arrow_detail);
        }
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        finish();
    }

    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        if (requestId == REQUEST_USER_INFO) {
            if (success) {
                initData(JSONObject.parseObject(data, UserBean.class));
            }
        }
    }

    private void initData(UserBean bean) {
        if (bean == null) {
            return;
        }

        tvUserPhone.setText(bean.getPhone());
        tvUserAddress.setText(bean.getAllAddress());
        tvUserEducation.setText(StringUtils.isEmpty(bean.getEducation()) ? "未上传" : bean.getEducation());
    }

    @Override
    public void onFailed() {

    }
}
