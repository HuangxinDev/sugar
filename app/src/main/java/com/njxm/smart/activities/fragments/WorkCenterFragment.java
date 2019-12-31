package com.njxm.smart.activities.fragments;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.activities.DailyCheckActivity;
import com.njxm.smart.activities.DailyCheckDetailActivity;
import com.njxm.smart.activities.SearchActivity;
import com.njxm.smart.activities.SuggestionsActivity;
import com.njxm.smart.activities.adapter.TestAdapter;
import com.njxm.smart.model.jsonbean.WorkCenterItemBean;
import com.njxm.smart.tools.network.HttpUtils;
import com.ns.demo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        OkHttpClient client = HttpUtils.getInstance().getOkHttpClient();
        Request request = new Request.Builder()
                .url("http://119.3.136.127:7776/api/sys/user/findResourceList")
                .headers(HttpUtils.getPostHeaders())
                .post(new FormBody.Builder().build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject object = JSON.parseObject(response.body().string());
                mWorkCenterItemBeans = JSONObject.parseArray(object.getString("data"), WorkCenterItemBean.class);
                for (int i = mWorkCenterItemBeans.size() - 1; i >= 0; i--) {
                    if (mWorkCenterItemBeans.get(i).getChildren() != null) {
                        mWorkCenterItemBeans.addAll(i + 1,
                                mWorkCenterItemBeans.get(i).getChildren());
                    }
                }
                EventBus.getDefault().post(mWorkCenterItemBeans);
//                refreshUI(mWorkCenterItemBeans);
            }
        });

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
                throw new NullPointerException("异常指针");
            }
            WorkCenterItemBean workCenterData = (WorkCenterItemBean) adapter.getItem(position);
            switch (workCenterData.getName()) {
                case "日常巡检":

                    startActivity(new Intent(getActivity(), DailyCheckActivity.class));
                    break;
                case "日常巡检详情":
                    startActivity(new Intent(getActivity(), DailyCheckDetailActivity.class));
                    break;
                case "查找联系人":
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                    break;
                case "考勤":
                    ARouter.getInstance().build("/app/main").withInt("index", 0).navigation();
                    break;
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