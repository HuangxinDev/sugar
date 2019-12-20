package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.njxm.smart.activities.adapter.MyCerticateListAdapter;
import com.njxm.smart.activities.adapter.MyCertificateAdapter;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserCertificateActivity extends BaseActivity {


    private static final int GET_CERTIFICATE_LIST = 686;

    public static class CertificateItem {
        public String name;
        public File file;
    }

    public static class CertificateListItem {
        @JSONField(name = "filePath")
        public String certificateImage;
        @JSONField(name = "typeName")
        public String certificateName;

        @JSONField(name = "typeIcon")
        public String certificateIcon;

        @JSONField(name = "id")
        public String certificateId;

        @JSONField(name = "status")
        public String status;
    }

    /**
     * 0:空页面
     * 1:有证书数据页面
     * 2:添加证书页面
     * 3:选择证书类型页面
     */
    private int certificateState = 0;

    private int lastCertificateState = 0;


    private RelativeLayout rlDefault;

    private TextView tvUploadBtn;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_certificate_actiivty;
    }

    RecyclerView recyclerView;

    LinearLayout llAdapterLayout;

    AppCompatTextView tvCommitBtn;

    /**
     * 添加证书adapter
     */
    MyCertificateAdapter myCertificateAdapter;

    private List<CertificateItem> mCertificateItems = new ArrayList<>();

    /**
     * 证书展示Adapter
     */
    MyCerticateListAdapter myCerticateListAdapter;

    private List<CertificateListItem> mCertificateListItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("持有证书");
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        showRightBtn(true, R.mipmap.new_add);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", SPUtils.getStringValue(KeyConstant.KEY_USER_ID));
        HttpUtils.getInstance().postData(GET_CERTIFICATE_LIST,
                HttpUtils.getJsonRequest(HttpUrlGlobal.URL_GET_USER_CERTIFICATE_LIST, hashMap), this);


        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myCertificateAdapter = new MyCertificateAdapter(this);
        myCerticateListAdapter = new MyCerticateListAdapter(this, mCertificateListItems);
        recyclerView.setLayoutManager(layoutManager);

        rlDefault = findViewById(R.id.default_layout);
        rlDefault.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        tvUploadBtn = findViewById(R.id.upload_btn);
        tvUploadBtn.setOnClickListener(this);

        llAdapterLayout = findViewById(R.id.adapter_layout);
        tvCommitBtn = findViewById(R.id.commit_btn);
    }

    private void init(int certificateState) {
        this.certificateState = certificateState;
        if (certificateState == 0) {
            llAdapterLayout.setVisibility(View.GONE);
            rlDefault.setVisibility(View.VISIBLE);
        } else if (certificateState == 1) {
            llAdapterLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            tvCommitBtn.setVisibility(View.GONE);
            rlDefault.setVisibility(View.GONE);
            myCerticateListAdapter.setNewData(mCertificateListItems);
            recyclerView.setAdapter(myCerticateListAdapter);
        } else if (certificateState == 2) {
            llAdapterLayout.setVisibility(View.VISIBLE);
            tvCommitBtn.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            mCertificateItems.clear();
            mCertificateItems.add(new CertificateItem());
            myCertificateAdapter.setNewData(mCertificateItems);
            recyclerView.setAdapter(myCertificateAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == tvUploadBtn) {
            rlDefault.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            lastCertificateState = 0;
            certificateState = 2;
        }
        init(certificateState);
    }

    @Override
    public void onBackPressed() {
        onClickLeftBtn();
    }

    @Override
    public void onClickLeftBtn() {
        if (certificateState == 0 || certificateState == 1) {
            finish();
            return;
        }
        certificateState = lastCertificateState;
        init(certificateState);
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        lastCertificateState = certificateState;
        if (certificateState == 0 || certificateState == 1) {
            certificateState = 2;
            init(certificateState);
        } else if (certificateState == 2) {
            myCertificateAdapter.addData(new CertificateItem());
            myCertificateAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        super.onSuccess(requestId, success, code, data);

        if (requestId == GET_CERTIFICATE_LIST) {
            if (data.equals("{}")) { // 空列表
                init(0);
            } else {
                mCertificateListItems = JSONObject.parseArray(data, CertificateListItem.class);
                myCerticateListAdapter.setNewData(mCertificateListItems);
                init(1);
            }
        }

    }
}
