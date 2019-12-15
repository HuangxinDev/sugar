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

import com.njxm.smart.activities.adapter.MyCerticateListAdapter;
import com.njxm.smart.activities.adapter.MyCertificateAdapter;
import com.ns.demo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserCertificateActivity extends BaseActivity {

    public static class CertificateItem {
        public String name;
        public File file;
    }

    public static class CertificateListItem {
        public String name;
        public String state;
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

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myCertificateAdapter = new MyCertificateAdapter(this);
        myCerticateListAdapter = new MyCerticateListAdapter(mCertificateListItems);
        recyclerView.setLayoutManager(layoutManager);

        rlDefault = findViewById(R.id.default_layout);
        rlDefault.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        tvUploadBtn = findViewById(R.id.upload_btn);
        tvUploadBtn.setOnClickListener(this);

        llAdapterLayout = findViewById(R.id.adapter_layout);
        tvCommitBtn = findViewById(R.id.commit_btn);
        init();
    }

    private void init() {
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
        init();
    }

    @Override
    public void onBackPressed() {
        onClickLeftBtn();
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();

        if (certificateState == 0 || certificateState == 1) {
            finish();
            return;
        }
        certificateState = lastCertificateState;
        init();
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        lastCertificateState = certificateState;
        if (certificateState == 0 || certificateState == 1) {
            certificateState = 2;
            init();
        } else if (certificateState == 2) {
            myCertificateAdapter.addData(new CertificateItem());
            myCertificateAdapter.notifyDataSetChanged();
        }
    }
}
