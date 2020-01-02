package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.adapter.EduTypeAdapter;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.EduTypeBean;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 学历页面
 */
@Route(path = GlobalRouter.USER_CETIFICATION)
public class UserEducationActivity extends BaseActivity {

    private List<EduTypeBean> typeBeans = new ArrayList<>();
    private EduTypeAdapter adapter;
    private int lastSelected = 0;
    private int selectedId = 0;

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_user_education_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        setActionBarTitle("学历");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EduTypeAdapter(typeBeans);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                lastSelected = selectedId;
                selectedId = position;
                try {
                    typeBeans.get(selectedId).setSelected(true);
                    typeBeans.get(lastSelected).setSelected(false);
                } catch (ArrayIndexOutOfBoundsException ex) {

                } finally {
                    adapter.notifyDataSetChanged();
                    uploadEdu();
                }
            }
        });
        mRecyclerView.setAdapter(adapter);
        RequestEvent requestEvent = RequestEvent.newBuilder().url(HttpUrlGlobal.URL_EDUCATION_LIST)
                .addBodyJson("code", "education_type")
                .build();
        HttpUtils.getInstance().request(requestEvent);
    }


    @Subscribe
    public void refreshEduState(List<EduTypeBean> beans) {
        typeBeans = beans;
        adapter.setNewData(typeBeans);
    }

    private void uploadEdu() {
        if (selectedId == -1 || typeBeans.size() == 0) {
            return;
        }

        RequestEvent requestEvent = RequestEvent.newBuilder()
                .url(HttpUrlGlobal.HTTP_MY_USER_EDUCATION)
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .addBodyJson("education", typeBeans.get(selectedId).getValue())
                .build();
        HttpUtils.getInstance().request(requestEvent);
    }
}
