package com.njxm.smart.activities.fragments;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.njxm.smart.activities.SuggestionsActivity;
import com.njxm.smart.activities.adapter.WorkCenterItemAdapter;
import com.njxm.smart.api.GetUserFuctionItemsApi;
import com.njxm.smart.bean.PermissionBean;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.StringUtils;
import com.ntxm.smart.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    void onLazyLoad() {
        mAdapter = new WorkCenterItemAdapter(getActivity(), mData);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (adapter.getItemViewType(position) == WorkCenterItemAdapter.ITEM_TITLE_TYPE) {
                return;
            }
            PermissionBean workCenterData = (PermissionBean) adapter.getItem(position);

            if (workCenterData == null || StringUtils.isEmpty(workCenterData.getUrl())) {
                return;
            }

            String routerPath = workCenterData.getSrLinkType() == 0 ?
                    "/" + workCenterData.getUrl().replace(":", "/") : "/app/webview";
            ARouter.getInstance().build(routerPath)
                    .withString("loadUrl", workCenterData.getUrl())
                    .navigation(getActivity(), new NavCallback() {
                        @Override
                        public void onArrival(Postcard postcard) {

                        }

                        @Override
                        public void onLost(Postcard postcard) {
                            super.onLost(postcard);
                            EventBus.getDefault().post(new ToastEvent("正在开发,敬请期待"));
                        }
                    });
        });
        mRecyclerView.setAdapter(mAdapter);

        GetUserFuctionItemsApi api = HttpUtils.getInstance().getApi(GetUserFuctionItemsApi.class);
        api.getData(HttpUtils.getRequestHeaders()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        List<PermissionBean> permissionBeans = JSON.parseArray(object.getString("data"), PermissionBean.class);
                        List<MultiItemEntity> data = new ArrayList<>();
                        for (PermissionBean parent : permissionBeans) {
                            List<PermissionBean> childrenBeans = parent.getChildren();
                            if (childrenBeans != null) {
                                for (PermissionBean child : childrenBeans) {
                                    parent.addSubItem(child);
                                }
                            }
                            data.add(parent);
                        }
                        invoke(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.setNewData(data);
                                mAdapter.expandAll(0, true);
                            }
                        });
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @OnClick({R.id.suggestion_box})
    void onClickEvent(View view) {
        if (view.getId() == R.id.suggestion_box) {
            startActivity(new Intent(getActivity(), SuggestionsActivity.class));
        }
    }

}