package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.SelectCertificateEvent;
import com.njxm.smart.model.jsonbean.CertificateParentBean;
import com.njxm.smart.tools.network.HttpUtils;
import com.ntxm.smart.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 证书种类Activity
 */
public class CertificateTypeActivity extends BaseActivity {
    @Override
    protected int setContentLayoutId() {
        return R.layout.my_certificate_type_activity;
    }


    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    private List<CertificateParentBean> mDatas = new ArrayList<>();

    private SimpleAdapter mAdapter;

    @BindView(R.id.navi_bar)
    protected LinearLayout llBarContainer;

    @BindView(R.id.allType)
    protected TextView tvAllType;

    @BindView(R.id.search_certificate)
    protected AppCompatImageButton ibSearchCertificate;

    @BindView(R.id.search_bar)
    protected AppCompatEditText etSearchContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBarTitle.setText("证书种类");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SimpleAdapter(R.layout.item_simple_text_layout, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CertificateParentBean bean = ((CertificateParentBean) adapter.getItem(position));
                if (bean != null && bean.isChildExit()) {
                    HashMap<String, String> map = new HashMap<>();

                    RequestEvent requestEvent = new RequestEvent.Builder()
                            .url(UrlPath.PATH_SUB_CERTIFICATE_TYPE_PULL.getUrl())
                            .addBodyJson("sctParentId", bean.getId())
                            .build();
                    HttpUtils.getInstance().request(requestEvent);
                    llBarContainer.addView(createTextView(bean.getSctName(), Integer.parseInt(bean.getId())));
                } else {
                    EventBus.getDefault().postSticky(new SelectCertificateEvent(bean.getSctName(), bean.getId()));
                    finish();
                }
            }
        });

        requestMainType();
    }

    private void requestMainType() {
        HttpUtils.getInstance().request(new RequestEvent.Builder()
                .url(UrlPath.PATH_CERTIFICATE_TYPE_PULL.getUrl())
                .addBodyJson("", "")
                .build());
    }

    @Override
    public void onResponse(ResponseEvent event) {

        final String url = event.getUrl();

        if (url.equals(UrlPath.PATH_CERTIFICATE_TYPE_PULL.getUrl())) {
            JSONObject jsonObject = JSONObject.parseObject(event.getData());
            mDatas = JSONObject.parseArray(jsonObject.getString("data"),
                    CertificateParentBean.class);
        } else if (url.equals(UrlPath.PATH_SUB_CERTIFICATE_TYPE_PULL.getUrl())) {
            mDatas = JSONObject.parseArray(event.getData(), CertificateParentBean.class);
        } else {
            super.onResponse(event);
        }

        invoke(new Runnable() {
            @Override
            public void run() {
                mAdapter.setNewData(mDatas);
            }
        });
    }

    @OnClick(R.id.allType)
    protected void requestAllType() {
        requestMainType();
        removeViewAfterClickView(tvAllType);
    }

    private static class SimpleAdapter extends BaseQuickAdapter<CertificateParentBean, BaseViewHolder> {

        public SimpleAdapter(int layoutResId, @Nullable List<CertificateParentBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CertificateParentBean item) {
            helper.setText(R.id.simple_text, item.getSctName());
        }
    }

    private TextView createTextView(String text, int id) {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setMinWidth(200);
        textView.setLayoutParams(layoutParams);
        textView.setId(id);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeViewAfterClickView(v);
            }
        });
        textView.setText(text);
        return textView;
    }

    private void removeViewAfterClickView(View clickView) {
        int index = llBarContainer.indexOfChild(clickView);
        if (index == -1) {
            return;
        }

        for (int i = index + 1; i < llBarContainer.getChildCount(); i++) {
            llBarContainer.removeViewAt(i);
        }
    }

    /**
     * search_certificate
     */
    @OnClick(R.id.search_certificate)
    protected void searchCertificate() {
//        if (StringUtils.isEmpty(etSearchContent.getText().toString())) {
//            showToast("搜索内容为空");
//            return;
//        }
        removeViewAfterClickView(tvAllType);
        HttpUtils.getInstance().request(new RequestEvent.Builder()
                .url(UrlPath.PATH_CERTIFICATE_TYPE_PULL.getUrl())
                .addBodyJson("sctName", etSearchContent.getText().toString())
                .build());

    }


}
