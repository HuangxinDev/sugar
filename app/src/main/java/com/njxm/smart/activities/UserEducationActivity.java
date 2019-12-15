package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.R;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 学历页面
 */
public class UserEducationActivity extends BaseActivity {

    private static final String[] EDU_STRING = {"高中", "大专", "本科", "研究生"};

    private ImageView[] ivs = new ImageView[4];

    private int selected = -1;


    @Override
    protected int setContentLayoutId() {
        return R.layout.my_user_education_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLeftBtn(true, R.mipmap.arrow_back);
        setActionBarTitle("学历");

        View llHightStudent = findViewById(R.id.ll_1);
        LinearLayout ll_2 = findViewById(R.id.ll_2);
        LinearLayout ll_3 = findViewById(R.id.ll_3);
        LinearLayout ll_4 = findViewById(R.id.ll_4);
        llHightStudent.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);
        ll_4.setOnClickListener(this);

        ivs[0] = findViewById(R.id.seat1);
        ivs[1] = findViewById(R.id.seat2);
        ivs[2] = findViewById(R.id.seat3);
        ivs[3] = findViewById(R.id.seat4);

        refresh();
    }

    private void refresh() {
        for (int i = 0; i < ivs.length; i++) {
            if (i == selected) {
                ivs[i].setVisibility(View.VISIBLE);
            } else {
                ivs[i].setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.ll_1:
                selected = 0;
                break;
            case R.id.ll_2:
                selected = 1;
                break;
            case R.id.ll_3:
                selected = 2;
                break;
            case R.id.ll_4:
                selected = 3;
                break;
        }
        refresh();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (selected >= 0 && selected <= 3) {
            JSONObject object = new JSONObject();
            object.put("id", SPUtils.getStringValue(KeyConstant.KEY_USE_ID));
            object.put("education", selected + "");

            RequestBody requestBody =
                    RequestBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE), object.toString());

            Request request = new Request.Builder()
                    .url(HttpUrlGlobal.HTTP_MY_USER_EDUCATION)
                    .addHeader("Platform", "APP")
                    .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                    .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                    .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                    .post(requestBody)
                    .build();

            HttpUtils.getInstance().postData(-1, request, null);
        }


    }
}
