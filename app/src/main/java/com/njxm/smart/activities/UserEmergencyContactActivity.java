package com.njxm.smart.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.ns.demo.R;

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
        setActionBarTitle("紧急联系人");
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        showRightBtn(true, "保存");

        etUserName = findViewById(R.id.user_name);
        etUserPhone = findViewById(R.id.news_user_phone);

        etUserName.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT));
        etUserPhone.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT_PHONE));
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        uploadContacts();
    }

    private void uploadContacts() {
        if (StringUtils.isEmpty(etUserName.getText().toString())
                || StringUtils.isEmpty(etUserPhone.getText().toString())) {
            showToast("姓名和手机号不可为空");
            return;
        }

        HttpUtils.getInstance().request(new RequestEvent.Builder()
                .url(HttpUrlGlobal.HTTP_MY_USER_EMERGENCY_CONTACT)
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .addBodyJson("contact", etUserName.getText().toString().trim())
                .addBodyJson("contactPhone", etUserPhone.getText().toString().trim())
                .build());
        SPUtils.putValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT, etUserName.getText().toString().trim());
        SPUtils.putValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT_PHONE, etUserPhone.getText().toString().trim());
        finish();
    }
}
