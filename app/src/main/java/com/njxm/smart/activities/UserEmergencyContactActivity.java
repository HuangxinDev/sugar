package com.njxm.smart.activities;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.ntxm.smart.R;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

@Route(path = GlobalRouter.USER_EMERGENCY_CONTACT)
public class UserEmergencyContactActivity extends BaseActivity {

    private AppCompatEditText etUserName;
    private AppCompatEditText etUserPhone;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_user_emergency_contact_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle("紧急联系人");
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
        this.showRightBtn(true, "保存");

        this.etUserName = this.findViewById(R.id.user_name);
        this.etUserPhone = this.findViewById(R.id.news_user_phone);

        this.etUserName.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT));
        this.etUserPhone.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT_PHONE));
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        this.uploadContacts();
    }

    private void uploadContacts() {
        if (StringUtils.isEmpty(this.etUserName.getText().toString())
                || StringUtils.isEmpty(this.etUserPhone.getText().toString())) {
            com.njxm.smart.activities.BaseActivity.showToast("姓名和手机号不可为空");
            return;
        }

        HttpUtils.getInstance().request(new RequestEvent.Builder()
                .url(UrlPath.PATH_USER_EMERGENCY_PEOPLE_NEWS_COMMIT.getUrl())
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .addBodyJson("contact", this.etUserName.getText().toString().trim())
                .addBodyJson("contactPhone", this.etUserPhone.getText().toString().trim())
                .build());
        SPUtils.putValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT, this.etUserName.getText().toString().trim());
        SPUtils.putValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT_PHONE, this.etUserPhone.getText().toString().trim());
        this.finish();
    }
}
