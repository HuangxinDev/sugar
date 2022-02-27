/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.annotation.JSONField;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.SelectCertificateEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.ui.activities.adapter.MyCerticateListAdapter;
import com.njxm.smart.ui.activities.adapter.MyCertificateAdapter;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.JsonUtils;
import com.njxm.smart.utils.ResolutionUtil;
import com.ntxm.smart.R;
import com.sugar.android.common.utils.SPUtils;
import com.sugar.android.common.utils.ViewUtils;
import com.sugar.android.common.view.SafeOnClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserCertificateActivity extends BaseActivity {
    private final List<CertificateItem> mCertificateItems = new ArrayList<>();

    @BindView(R.id.default_layout)
    protected RelativeLayout rlDefault;

    @BindView(R.id.upload_btn)
    protected TextView tvUploadBtn;

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @BindView(R.id.adapter_layout)
    protected LinearLayout llAdapterLayout;

    @BindView(R.id.commit_btn)
    protected AppCompatTextView tvCommitBtn;

    protected int clickPosition = -1;

    /**
     * 添加证书adapter
     */
    MyCertificateAdapter myCertificateAdapter;

    /**
     * 证书展示Adapter
     */
    MyCerticateListAdapter myCerticateListAdapter;

    /**
     * 0:空页面
     * 1:有证书数据页面
     * 2:添加证书页面
     * 3:选择证书类型页面
     */
    private int certificateState = 0;

    private int lastCertificateState = 0;

    private List<CertificateListItem> mCertificateListItems = new ArrayList<>();

    /**
     * 请求证书数据列表
     */
    private static void refreshCertificateList() {
        HttpUtils.getInstance().request(RequestEvent.newBuilder()
                .url(UrlPath.PATH_USER_CERTIFICATE_PULL.getUrl())
                .addBodyJson("userId", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .build());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.my_certificate_actiivty;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle("持有证书");
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
        this.showRightBtn(true, R.mipmap.new_add);
        com.njxm.smart.ui.activities.UserCertificateActivity.refreshCertificateList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.myCertificateAdapter = new MyCertificateAdapter(this);
        this.myCertificateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                com.njxm.smart.ui.activities.UserCertificateActivity.this.clickPosition = position;
                if (view.getId() == R.id.certificate_add) {
                    Intent intent = new Intent(UserCertificateActivity.this, CertificateTypeActivity.class);
                    com.njxm.smart.ui.activities.UserCertificateActivity.this.startActivity(intent);
                } else if (view.getId() == R.id.certificate_image_show) {
                    com.njxm.smart.ui.activities.UserCertificateActivity.this.takePhoto(100);
                }
            }
        });
        this.myCerticateListAdapter = new MyCerticateListAdapter(this, this.mCertificateListItems);
        this.recyclerView.setLayoutManager(layoutManager);
        ViewUtils.setOnClickListener(tvUploadBtn, new SafeOnClickListener() {
            @Override
            public void onSafeClick(View view) {
                rlDefault.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                lastCertificateState = 0;
                certificateState = 2;
                init(certificateState);
            }
        });
    }

    private void init(int certificateState) {
        this.certificateState = certificateState;
        if (certificateState == 0) {
            this.llAdapterLayout.setVisibility(View.GONE);
            this.rlDefault.setVisibility(View.VISIBLE);
        } else if (certificateState == 1) {
            this.llAdapterLayout.setVisibility(View.VISIBLE);
            this.recyclerView.setVisibility(View.VISIBLE);
            this.tvCommitBtn.setVisibility(View.GONE);
            this.rlDefault.setVisibility(View.GONE);
            this.myCerticateListAdapter.setNewData(this.mCertificateListItems);
            this.recyclerView.setAdapter(this.myCerticateListAdapter);
        } else if (certificateState == 2) {
            this.llAdapterLayout.setVisibility(View.VISIBLE);
            this.rlDefault.setVisibility(View.GONE);
            this.tvCommitBtn.setVisibility(View.VISIBLE);
            this.recyclerView.setVisibility(View.VISIBLE);
            this.mCertificateItems.clear();
            this.mCertificateItems.add(new CertificateItem());
            this.myCertificateAdapter.setNewData(this.mCertificateItems);
            this.recyclerView.setAdapter(this.myCertificateAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        this.onClickLeftBtn();
    }

    @Override
    public void onClickLeftBtn() {
        if (this.certificateState == 0 || this.certificateState == 1) {
            this.finish();
        } else {
            this.certificateState = this.lastCertificateState;
            this.init(this.certificateState);
        }
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        if (this.certificateState == 0 || this.certificateState == 1) {
            this.lastCertificateState = this.certificateState;
            this.certificateState = 2;
            this.init(this.certificateState);
        } else if (this.certificateState == 2) {
            this.myCertificateAdapter.addData(new CertificateItem());
            this.myCertificateAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 请求后数据，刷新新数据
     *
     * @param listItems 请求回来的数据列表
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetCertificateList(List<CertificateListItem> listItems) {
        if (listItems == null || listItems.size() <= 0) {
            this.init(0);
        } else {
            this.mCertificateListItems = listItems;
            this.myCerticateListAdapter.setNewData(this.mCertificateListItems);
            this.init(1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap drawable =
                                Glide.with(UserCertificateActivity.this)
                                        .asBitmap().load(com.njxm.smart.ui.activities.UserCertificateActivity.this.photoFile)
                                        .submit(ResolutionUtil.dp2Px(172), ResolutionUtil.dp2Px(109))
                                        .get();
                        BitmapUtils.saveBitmap(drawable, com.njxm.smart.ui.activities.UserCertificateActivity.this.photoFile);
                        com.njxm.smart.ui.activities.UserCertificateActivity.this.mCertificateItems.get(com.njxm.smart.ui.activities.UserCertificateActivity.this.clickPosition).file = com.njxm.smart.ui.activities.UserCertificateActivity.this.photoFile;
                        com.njxm.smart.ui.activities.UserCertificateActivity.this.invoke(new Runnable() {
                            @Override
                            public void run() {
                                com.njxm.smart.ui.activities.UserCertificateActivity.this.myCertificateAdapter.setNewData(com.njxm.smart.ui.activities.UserCertificateActivity.this.mCertificateItems);
                            }
                        });
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 刷新数据证书类型选择
     *
     * @param event
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void updateList(SelectCertificateEvent event) {
        if (this.clickPosition == -1 || event == null) {
            return;
        }
        CertificateItem item = this.mCertificateItems.get(this.clickPosition);
        item.name = event.getName();
        item.id = event.getCertId();
        this.myCertificateAdapter.setNewData(this.mCertificateItems);
    }

    @OnClick(R.id.commit_btn)
    protected void uploadCertificate() {
        RequestEvent.Builder requestBuilder = new RequestEvent.Builder()
                .url(UrlPath.PATH_USER_CERTIFICATE_COMMIT.getUrl())
                .addPart(MultipartBody.Part.createFormData("sucUserId", SPUtils.getStringValue(KeyConstant.KEY_USER_ID)));
        for (CertificateItem item : this.mCertificateItems) {
            if (item.file == null) {
                continue;
            }
            requestBuilder.addPart(MultipartBody.Part.createFormData("files", item.id + ".jpg", RequestBody.create(MediaType.parse("image/png"), item.file)));
        }
        HttpUtils.getInstance().doPostFile(requestBuilder.build());
    }

    @Override
    public void onResponse(ResponseEvent event) {
        String url = event.getUrl();
        if (url.equals(UrlPath.PATH_USER_CERTIFICATE_COMMIT.getUrl())) {
            com.njxm.smart.ui.activities.UserCertificateActivity.refreshCertificateList();
        } else if (url.equals(UrlPath.PATH_USER_CERTIFICATE_PULL.getUrl())) {
            EventBus.getDefault().post(JsonUtils.getJsonArray(event.getData(),
                    CertificateListItem.class));
        }
    }

    public static class CertificateItem {
        public String id;

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
        public int status;
    }
}
