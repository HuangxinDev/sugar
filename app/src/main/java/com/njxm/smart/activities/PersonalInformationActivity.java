package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.CircleImageView;
import com.ns.demo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 个人信息页面（由主页 我的-姓名跳转）
 */
public class PersonalInformationActivity extends BaseActivity implements HttpCallBack {

    @BindView(R.id.news_user_base_new)
    protected View mUserBaseNews;

    @BindView(R.id.news_user_base_new_detail)
    protected ImageView mUserNewsImage;

    @BindView(R.id.news_user_base_new_sub)
    protected View mUserBaseDetailNews;

    @BindView(R.id.news_user_head)
    protected CircleImageView ivUserHead;

    @BindView(R.id.news_user_name)
    protected TextView tvUserName;

    @BindView(R.id.news_user_phone)
    protected TextView tvUserPhone;

    @BindView(R.id.news_user_address)
    protected TextView tvUserAddress;

    @BindView(R.id.news_user_education)
    protected TextView tvUserEducation;

    @BindView(R.id.news_user_emergency_contact)
    protected TextView tvUsereEmergencyContact;

    @BindView(R.id.news_user_phone_ll)
    protected LinearLayout llUserPhone;

    @BindView(R.id.news_user_input_face_ll)
    protected LinearLayout llUserInputFace;

    @BindView(R.id.news_user_education_item)
    protected LinearLayout llUserEducation;

    @BindView(R.id.news_user_address_item)
    protected LinearLayout llUserAddress;

    @BindView(R.id.news_user_emergency_contact_item)
    protected LinearLayout llUserEmergencyContact;

    @BindView(R.id.user_company_name)
    protected TextView tvUserCompanyName;

    @BindView(R.id.user_department_name)
    protected TextView tvUserDepartmentName;

    @BindView(R.id.user_team_name)
    protected TextView tvUserTeamName;

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

//        tvUserName.setText(SPUtils.getStringValue(KeyConstant.KEY_USERNAME));
//        tvUserPhone.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_TEL_PHONE));
//        Glide.with(this)
//                .load(HttpUrlGlobal.HTTP_MY_USER_HEAD_URL_PREFIX + SPUtils.getStringValue(KeyConstant.KEY_USER_HEAD_ICON))
//                .apply(new RequestOptions().placeholder(R.mipmap.personal_news_head))
//                .into(ivUserHead);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        tvUsereEmergencyContact.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT));
//        tvUserAddress.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_ADDRESS));
//        final String eduStatus = SPUtils.getStringValue(KeyConstant.KEY_USER_EDUCATION_STATUS);
//        tvUserEducation.setText(StringUtils.isEmpty(eduStatus) ? "未上传" : eduStatus);

        EventBus.getDefault().post(new RequestEvent(RequestEvent.REQUEST_PARAMS));
    }

    @OnClick({R.id.news_user_base_new, R.id.news_user_phone_ll, R.id.news_user_input_face_ll,
            R.id.news_user_education_item, R.id.news_user_address_item,
            R.id.news_user_emergency_contact_item, R.id.news_user_head})
    protected void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.news_user_head:
                takePhoto(1);
                break;
            case R.id.news_user_base_new:
                showDetails = !showDetails;
                mUserBaseDetailNews.setVisibility(showDetails ? View.VISIBLE : View.GONE);
                mUserNewsImage.setImageResource(showDetails ? R.mipmap.arrow_down : R.mipmap.arrow_detail);
                break;
            case R.id.news_user_phone_ll:
                ARouter.getInstance().build(GlobalRouter.USER_PHONE).navigation();
                break;
            case R.id.news_user_address_item:
                ARouter.getInstance().build(GlobalRouter.USER_ADDRESS).navigation();
                break;
            case R.id.news_user_input_face_ll:
                ARouter.getInstance().build(GlobalRouter.USER_INPUT_FACE).navigation();
                break;
            case R.id.news_user_education_item:
                ARouter.getInstance().build(GlobalRouter.USER_CETIFICATION).navigation();
                break;
            case R.id.news_user_emergency_contact_item:
                ARouter.getInstance().build(GlobalRouter.USER_EMERGENCY_CONTACT).navigation();
                break;
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
            } else {
                showToast(data);
            }
        }
    }

    @Override
    public void onFailed(String errMsg) {

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshUI(UserBean bean) {
        tvUserName.setText(bean.getUserName());
        tvUserCompanyName.setText(bean.getCompanyName());
        tvUserDepartmentName.setText(bean.getDeptName());
        tvUserTeamName.setText(bean.getTeamName());
        tvUserPhone.setText(bean.getPhone());
        tvUserAddress.setText(bean.getAllAddress());
        tvUserEducation.setText(StringUtils.isEmpty(bean.getEducation()) ? "未上传" : bean.getEducation());
        Glide.with(this)
                .load(HttpUrlGlobal.HTTP_MY_USER_HEAD_URL_PREFIX + bean.getIcon())
                .apply(new RequestOptions().placeholder(R.mipmap.personal_news_head))
                .into(ivUserHead);
        tvUsereEmergencyContact.setText(bean.getContactPhone());
    }
}
