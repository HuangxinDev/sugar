package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.model.jsonbean.UserBean;
import com.ntxm.smart.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = GlobalRouter.USER_PHONE)
public class UserPhoneActivity extends BaseActivity {

    @BindView(R.id.news_user_phone)
    protected TextView tvUserPhone;

    @Override
    protected int setContentLayoutId() {
        return R.layout.user_phone_activity;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("手机");
        showLeftBtn(true, R.mipmap.arrow_back_blue);
    }


    @OnClick(R.id.settings_update_phone)
    public void onViewClicked(View v) {
        startActivity(new Intent(this, UpdateTelPhoneActivity.class));
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshUI(UserBean bean) {
        try {
            StringBuilder builder = new StringBuilder(11);
            builder.append(bean.getPhone());
            tvUserPhone.setText(builder.replace(3, 7, "****").toString());
        } catch (Exception e) {
            tvUserPhone.setText("手机号未获取到");
        }
    }
}
