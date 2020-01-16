package com.njxm.smart.activities;

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
import com.njxm.smart.activities.adapter.MyCerticateListAdapter;
import com.njxm.smart.activities.adapter.MyCertificateAdapter;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.SelectCertificateEvent;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.ResolutionUtil;
import com.njxm.smart.utils.SPUtils;
import com.ns.demo.R;

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
import okhttp3.Request;
import okhttp3.RequestBody;

public class UserCertificateActivity extends BaseActivity implements HttpCallBack {


    private static final int REQUEST_UPLOAD_CERTIFICATION = 109;

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

    /**
     * 0:空页面
     * 1:有证书数据页面
     * 2:添加证书页面
     * 3:选择证书类型页面
     */
    private int certificateState = 0;

    private int lastCertificateState = 0;


    @BindView(R.id.default_layout)
    protected RelativeLayout rlDefault;

    @BindView(R.id.upload_btn)
    protected TextView tvUploadBtn;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_certificate_actiivty;
    }

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @BindView(R.id.adapter_layout)
    protected LinearLayout llAdapterLayout;

    @BindView(R.id.commit_btn)
    protected AppCompatTextView tvCommitBtn;

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

    protected int clickPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("持有证书");
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        showRightBtn(true, R.mipmap.new_add);

        refreshCertificateList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myCertificateAdapter = new MyCertificateAdapter(this);

        myCertificateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                clickPosition = position;
                if (view.getId() == R.id.certificate_add) {
                    Intent intent = new Intent(UserCertificateActivity.this, CertificateTypeActivity.class);
                    startActivity(intent);
                } else if (view.getId() == R.id.certificate_image_show) {
                    takePhoto(100);
                }
            }
        });

        myCerticateListAdapter = new MyCerticateListAdapter(this, mCertificateListItems);
        recyclerView.setLayoutManager(layoutManager);

        tvUploadBtn.setOnClickListener(this);
    }

    /**
     * 请求证书数据列表
     */
    private void refreshCertificateList() {
        HttpUtils.getInstance().request(RequestEvent.newBuilder()
                .url(HttpUrlGlobal.URL_GET_USER_CERTIFICATE_LIST)
                .addBodyJson("userId", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .build());
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
        } else {
            certificateState = lastCertificateState;
            init(certificateState);
        }
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        if (certificateState == 0 || certificateState == 1) {
            lastCertificateState = certificateState;
            certificateState = 2;
            init(certificateState);
        } else if (certificateState == 2) {
            myCertificateAdapter.addData(new CertificateItem());
            myCertificateAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 请求后数据，刷新新数据
     * @param listItems 请求回来的数据列表
     */
    @Subscribe
    public void onGetCertificateList(List<CertificateListItem> listItems) {
        if (listItems == null || listItems.size() <= 0) {
            init(0);
        } else {
            mCertificateListItems = listItems;
            myCerticateListAdapter.setNewData(mCertificateListItems);
            init(1);
        }
    }


    @Override
    public void onSuccess(int requestId, boolean success, int code, String data) {
        if (requestId == REQUEST_UPLOAD_CERTIFICATION) {
            refreshCertificateList();
        }
    }

    @Override
    public void onFailed(String errMsg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Bitmap drawable =
                                Glide.with(UserCertificateActivity.this)
                                        .asBitmap().load(photoFile)
                                        .submit(ResolutionUtil.dp2Px(172), ResolutionUtil.dp2Px(109))
                                        .get();
                        BitmapUtils.saveBitmap(drawable, photoFile);
                        mCertificateItems.get(clickPosition).file = photoFile;
                        invoke(new Runnable() {
                            @Override
                            public void run() {
                                myCertificateAdapter.setNewData(mCertificateItems);
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
     * @param event
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void updateList(SelectCertificateEvent event) {
        if (clickPosition == -1 || event == null) {
            return;
        }
        CertificateItem item = mCertificateItems.get(clickPosition);
        item.name = event.getName();
        item.id = event.getCertId();
        myCertificateAdapter.setNewData(mCertificateItems);
    }

    @OnClick(R.id.commit_btn)
    protected void uploadCertificate() {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("sucUserId", SPUtils.getStringValue(KeyConstant.KEY_USER_ID));
        for (CertificateItem item : mCertificateItems) {
            if (item.file == null) {
                continue;
            }
            builder.addFormDataPart("files", item.id + ".jpg", RequestBody.create(MediaType.parse("image/png"),
                    item.file));
        }

        Request request = new Request.Builder()
                .url(HttpUrlGlobal.URL_UPLOAD_CERTIFICATE_LIST)
                .headers(HttpUtils.getPostHeaders())
                .post(builder.build())
                .build();

        HttpUtils.getInstance().postData(REQUEST_UPLOAD_CERTIFICATION, request, this);
    }
}
