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
import com.njxm.smart.eventbus.SelectCertificateEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.model.jsonbean.CertificateParentBean;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.ns.demo.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 证书种类Activity
 */
public class CertificateTypeActivity extends BaseActivity implements HttpCallBack {
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
                    map.put("sctParentId", bean.getId());
                    HttpUtils.getInstance().postData(200, HttpUtils.getJsonRequest("http://119.3" +
                                    ".136.127:7776/api/sys/userCertificate/findTypeList", map),
                            CertificateTypeActivity.this);
                    llBarContainer.addView(createTextView(bean.getSctName(), Integer.parseInt(bean.getId())));
                } else {
                    EventBus.getDefault().postSticky(new SelectCertificateEvent(bean.getSctName(),
                            bean.getId()));
                    finish();
                }
            }
        });
        HttpUtils.getInstance().postData(100,
                HttpUtils.getJsonRequest(HttpUrlGlobal.URL_GET_CERTIFICATE_MAIN_LIST, null), this);
    }

    @OnClick(R.id.allType)
    protected void requestAllType() {
        HttpUtils.getInstance().postData(100,
                HttpUtils.getJsonRequest(HttpUrlGlobal.URL_GET_CERTIFICATE_MAIN_LIST, null), this);
        removeViewAfterClickView(tvAllType);
    }


    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        if (requestId == 100) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            mDatas = JSONObject.parseArray(jsonObject.getString("data"),
                    CertificateParentBean.class);
        } else if (requestId == 200) {
            mDatas = JSONObject.parseArray(data, CertificateParentBean.class);
        }

        invoke(new Runnable() {
            @Override
            public void run() {
                mAdapter.setNewData(mDatas);
            }
        });
    }

    @Override
    public void onFailed(String errMsg) {
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
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sctName", etSearchContent.getText().toString());
        HttpUtils.getInstance().postData(100,
                HttpUtils.getJsonRequest(HttpUrlGlobal.URL_GET_CERTIFICATE_MAIN_LIST, hashMap), this);
    }


}
