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
import com.njxm.smart.api.UploadFileApi;
import com.njxm.smart.bean.ServerResponseBean;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.njxm.smart.view.CircleImageView;
import com.ntxm.smart.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 个人信息页面（由主页 我的-姓名跳转）
 */
public class PersonalInformationActivity extends BaseActivity {

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

    @BindView(R.id.user_post_name)
    protected TextView tvUserJobName;

    private boolean showDetails = false;

    private static final int REQUEST_USER_HEAD = 412;

    private UserBean mNewestUserBean;


    @Override
    protected int setContentLayoutId() {
        return R.layout.my_user_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("个人信息");
        showLeftBtn(true, R.mipmap.arrow_back_blue);
    }

    @Override
    protected void onStart() {
        super.onStart();
        RequestEvent requestEvent = RequestEvent.newBuilder()
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .url(UrlPath.PATH_USER_DETAILS_NEWS.getUrl())
                .build();
        HttpUtils.getInstance().request(requestEvent);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                ARouter.getInstance().build(GlobalRouter.USER_CETIFICATION).withString("params",
                        tvUserEducation.getText().toString()).navigation();
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
                        uploadHeadFile();
                    }
                }
            });
        }
    }

    private void uploadHeadFile() {

        UploadFileApi api = HttpUtils.getInstance().getApi(UploadFileApi.class);

        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(MultipartBody.Part.createFormData("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID)));
        parts.add(MultipartBody.Part.createFormData("file", photoFile.getName(),
                RequestBody.create(MediaType.parse("image/png"), photoFile)));

        api.uploadFile(UrlPath.PATH_USER_HEAD_COMMIT.getPath(), parts).enqueue(new Callback<ServerResponseBean>() {
            @Override
            public void onResponse(Call<ServerResponseBean> call, Response<ServerResponseBean> response) {
                EventBus.getDefault().post(new ToastEvent(response.code() == 200 ? "上传成功" : response.message()));
                invoke(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(PersonalInformationActivity.this).load(photoFile).into(ivUserHead);
                    }
                });
            }

            @Override
            public void onFailure(Call<ServerResponseBean> call, Throwable t) {
                EventBus.getDefault().post(new ToastEvent("网络异常"));
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshUI(UserBean bean) {
        mNewestUserBean = bean;
        tvUserName.setText(bean.getUserName());
        tvUserCompanyName.setText(bean.getCompanyName());
        tvUserDepartmentName.setText(bean.getDeptName());
        tvUserTeamName.setText(bean.getTeamName());
        tvUserPhone.setText(bean.getPhone());
        tvUserAddress.setText(StringUtils.isEmpty(bean.getAllAddress()) ? "请补充地址" : bean.getAddress());
        tvUserEducation.setText(StringUtils.isEmpty(bean.getEducation()) ? "未上传" : bean.getEducation());
        tvUserJobName.setText(bean.getWorkType());
        Glide.with(this)
                .load(bean.getIcon())
                .apply(new RequestOptions().placeholder(R.mipmap.personal_news_head))
                .into(ivUserHead);
        tvUsereEmergencyContact.setText(bean.getContactPhone());
    }
}
