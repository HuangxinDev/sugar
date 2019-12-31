package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.adapter.EduTypeAdapter;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.EduTypeBean;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 学历页面
 */
@Route(path = GlobalRouter.USER_CETIFICATION)
public class UserEducationActivity extends BaseActivity {

    private List<EduTypeBean> typeBeans = new ArrayList<>();
    private EduTypeAdapter adapter;
    private int selectedId = -1;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_user_education_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        setActionBarTitle("学历");

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EduTypeAdapter(typeBeans);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (Object bean : adapter.getData()) {
                    if (!(bean instanceof EduTypeBean)) {
                        continue;
                    }
                    if (bean == adapter.getItem(position)) {
                        selectedId = position;
                        ((EduTypeBean) bean).setSelected(true);
                        uploadEdu();
                    } else {
                        ((EduTypeBean) bean).setSelected(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(adapter);

        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("code", "education_type");
        HttpUtils.getInstance().postData(-1,
                HttpUtils.getJsonRequest(HttpUrlGlobal.URL_EDUCATION_LIST, bodyMap),
                new HttpCallBack() {
                    @Override
                    public void onSuccess(int requestId, boolean success, int code, String data) {
                        typeBeans = JSONObject.parseArray(data, EduTypeBean.class);
                        for (EduTypeBean bean : typeBeans) {
                            if (SPUtils.getStringValue(KeyConstant.KEY_USER_EDUCATION_STATUS).equals(bean.getSdName())) {
                                bean.setSelected(true);
                                break;
                            }
                        }
                        invoke(new Runnable() {
                            @Override
                            public void run() {
                                refreshUI();
                            }
                        });
                    }

                    @Override
                    public void onFailed(String errMsg) {

                    }
                });
    }

    private void refreshUI() {
        adapter.setNewData(typeBeans);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    private void uploadEdu() {
        if (selectedId == -1 || typeBeans.size() == 0) {
            return;
        }

        JSONObject object = new JSONObject();
        object.put("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID));
        object.put("education", typeBeans.get(selectedId).getValue());
        RequestBody requestBody =
                RequestBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE), object.toString());
        Request request = new Request.Builder()
                .url(HttpUrlGlobal.HTTP_MY_USER_EDUCATION)
                .headers(HttpUtils.getPostHeaders())
                .post(requestBody)
                .build();

        HttpUtils.getInstance().postData(-1, request, new HttpCallBack() {
            @Override
            public void onSuccess(int requestId, boolean success, int code, String data) {
                SPUtils.putValue(KeyConstant.KEY_USER_EDUCATION_STATUS, typeBeans.get(selectedId).getSdName());
            }

            @Override
            public void onFailed(String errMsg) {

            }
        });
    }

}
