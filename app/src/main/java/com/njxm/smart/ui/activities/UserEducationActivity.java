/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.EduTypeBean;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.ui.activities.adapter.EduTypeAdapter;
import com.njxm.smart.utils.SPUtils;
import com.ntxm.smart.R;
import com.sugar.android.common.safe.SafeIntent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 学历页面
 */
@Route(path = GlobalRouter.USER_CETIFICATION)
public class UserEducationActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;
    private List<EduTypeBean> typeBeans = new ArrayList<>();
    private EduTypeAdapter adapter;
    private int lastSelected = 0;
    private int selectedId = 0;
    private String mUserEdu;

    @Override
    protected int getLayoutId() {
        return R.layout.my_user_education_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
        this.setActionBarTitle("学历");
        SafeIntent safeIntent = new SafeIntent(getIntent());
        this.mUserEdu = safeIntent.getStringExtra("params");
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new EduTypeAdapter(this.typeBeans);
        this.adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                com.njxm.smart.ui.activities.UserEducationActivity.this.lastSelected = com.njxm.smart.ui.activities.UserEducationActivity.this.selectedId;
                com.njxm.smart.ui.activities.UserEducationActivity.this.selectedId = position;
                if (com.njxm.smart.ui.activities.UserEducationActivity.this.lastSelected == com.njxm.smart.ui.activities.UserEducationActivity.this.selectedId) {
                    return;
                }
                try {
                    com.njxm.smart.ui.activities.UserEducationActivity.this.typeBeans.get(com.njxm.smart.ui.activities.UserEducationActivity.this.selectedId).setSelected(true);
                    com.njxm.smart.ui.activities.UserEducationActivity.this.typeBeans.get(com.njxm.smart.ui.activities.UserEducationActivity.this.lastSelected).setSelected(false);
                } catch (ArrayIndexOutOfBoundsException ex) {

                } finally {
                    adapter.notifyDataSetChanged();
                    com.njxm.smart.ui.activities.UserEducationActivity.this.uploadEdu();
                }
            }
        });
        this.mRecyclerView.setAdapter(this.adapter);
        RequestEvent requestEvent = RequestEvent.newBuilder().url(UrlPath.PATH_USER_EDU_PULL.getUrl())
                .addBodyJson("code", "education_type")
                .build();
        HttpUtils.getInstance().request(requestEvent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshEduState(List<EduTypeBean> beans) {
        this.typeBeans = beans;
        for (EduTypeBean bean : this.typeBeans) {
            if (bean.getSdName().equals(this.mUserEdu)) {
                bean.setSelected(true);
                this.selectedId = this.lastSelected = this.typeBeans.indexOf(bean);
                break;
            }
        }
        this.adapter.setNewData(this.typeBeans);
    }

    private void uploadEdu() {
        if (this.selectedId == -1 || this.typeBeans.size() == 0) {
            return;
        }

        RequestEvent requestEvent = RequestEvent.newBuilder()
                .url(UrlPath.PATH_USER_EDU_NEWS_COMMIT.getUrl())
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .addBodyJson("education", this.typeBeans.get(this.selectedId).getValue())
                .build();
        HttpUtils.getInstance().request(requestEvent);
    }
}
