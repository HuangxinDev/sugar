package com.njxm.smart.activities.fragments;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.njxm.smart.activities.SuggestionsActivity;
import com.njxm.smart.activities.adapter.WorkCenterItemAdapter;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.model.jsonbean.WorkCenterSubBean;
import com.njxm.smart.model.jsonbean.WorkCenterTitleBean;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.JsonUtils;
import com.njxm.smart.utils.StringUtils;
import com.ns.demo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 工作中心Fragment
 */
public class WorkCenterFragment extends BaseFragment {


    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.suggestion_box)
    protected AppCompatTextView mSuggestionBox;

    private WorkCenterItemAdapter mAdapter;

    private final List<MultiItemEntity> mData = new ArrayList<>();

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main_workcenter;
    }

    @Override
    protected void init() {
        super.init();
        HttpUtils.getInstance().request(RequestEvent.newBuilder().url(HttpUrlGlobal.URL_WORKCENTER_ITEMS).build());
    }

    @Subscribe
    public void reponse(ResponseEvent event) {
        List<WorkCenterTitleBean> mWorkCenterItemBeans = JsonUtils.getJsonArray(event.getData(), WorkCenterTitleBean.class);
        List<MultiItemEntity> data = new ArrayList<>();
        for (WorkCenterTitleBean bean : mWorkCenterItemBeans) {
            for (WorkCenterSubBean subBean : bean.getChildren()) {
                bean.addSubItem(subBean);
            }
            data.add(bean);
        }
        EventBus.getDefault().post(data);
    }

    @Override
    protected void setUpView() {
    }

    @Override
    protected void setUpData() {
        mAdapter = new WorkCenterItemAdapter(getActivity(), mData);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (adapter.getItemViewType(position) == WorkCenterItemAdapter.ITEM_TITLE_TYPE) {
                return;
            }

            WorkCenterSubBean workCenterData = (WorkCenterSubBean) adapter.getItem(position);

            if (workCenterData == null || StringUtils.isEmpty(workCenterData.getUrl())) {
                return;
            }

            if (workCenterData.getUrl().startsWith("http")) {
                ARouter.getInstance().build("/app/webview").withString("loadUrl",
                        workCenterData.getUrl()).navigation();
            } else {
                ARouter.getInstance().build(workCenterData.getUrl()).navigation(getActivity(), new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        super.onLost(postcard);
                        EventBus.getDefault().post(new ToastEvent("正在开发,敬请期待"));
                    }
                });
            }
        });
        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.expandAll(0, true);
    }

    @OnClick({R.id.suggestion_box})
    protected void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.suggestion_box:
                startActivity(new Intent(getActivity(), SuggestionsActivity.class));
                break;
            default:

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(List<MultiItemEntity> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        mAdapter.setNewData(datas);
        mAdapter.expandAll(0, true);
    }
}