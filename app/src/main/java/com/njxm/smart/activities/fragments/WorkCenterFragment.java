package com.njxm.smart.activities.fragments;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.activities.SuggestionsActivity;
import com.njxm.smart.activities.adapter.TestAdapter;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.model.jsonbean.WorkCenterItemBean;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.StringUtils;
import com.ns.demo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    private TestAdapter mAdapter;

    private List<WorkCenterItemBean> mWorkCenterItemBeans;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main_workcenter;
    }

    @Override
    protected void init() {
        super.init();


        HttpUtils.getInstance().request(RequestEvent.newBuilder().url("http://119.3.136" +
                ".127:7776/api/sys/user/findResourceList").build());
    }

    @Subscribe
    public void reponse(ResponseEvent event) {
        mWorkCenterItemBeans = JSONObject.parseArray(event.getData(), WorkCenterItemBean.class);
        for (int i = mWorkCenterItemBeans.size() - 1; i >= 0; i--) {
            if (mWorkCenterItemBeans.get(i).getChildren() != null) {
                mWorkCenterItemBeans.addAll(i + 1,
                        mWorkCenterItemBeans.get(i).getChildren());
            }
        }
        EventBus.getDefault().post(mWorkCenterItemBeans);
    }

    @Override
    protected void setUpView() {
    }

    @Override
    protected void setUpData() {
        mAdapter = new TestAdapter(getActivity(), null);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4,
                GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (adapter.getItemViewType(position) == WorkCenterItemBean.ITEM_TITLE_TYPE) {
                return;
            }

            WorkCenterItemBean workCenterData = (WorkCenterItemBean) adapter.getItem(position);

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
    public void refreshUI(List<WorkCenterItemBean> datas) {
//        invoke(new Runnable() {
//            @Override
//            public void run() {
        mAdapter.setNewData(datas);
//            }
//        });

    }
}