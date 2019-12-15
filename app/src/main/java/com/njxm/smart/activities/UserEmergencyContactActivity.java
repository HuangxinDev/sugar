package com.njxm.smart.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.ns.demo.R;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

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
        JSONObject object = new JSONObject();
        object.put("id", SPUtils.getStringValue(KeyConstant.KEY_USE_ID));
        object.put("contact", etUserName.getText().toString().trim());
        object.put("contactPhone", etUserPhone.getText().toString().trim());

        RequestBody requestBody =
                FormBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE), object.toJSONString());

        final Request request = new Request.Builder().url(HttpUrlGlobal.HTTP_MY_USER_EMERGENCY_CONTACT)
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                .post(requestBody)
                .build();

        SPUtils.putValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT,
                etUserName.getText().toString().trim());
        SPUtils.putValue(KeyConstant.KEY_USER_EMERGENCY_CONTACT_PHONE,
                etUserPhone.getText().toString().trim());

        HttpUtils.getInstance().postData(1, request, null);
        finish();
    }
}
