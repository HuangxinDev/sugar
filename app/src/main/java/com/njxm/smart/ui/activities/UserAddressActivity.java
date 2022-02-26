/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.contract.AreaContract;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.AddressBean;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.presenter.AreaPresenter;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.ui.activities.adapter.SimpleTextAdapter;
import com.njxm.smart.utils.SPUtils;
import com.ntxm.smart.R;
import com.smart.cloud.utils.ToastUtils;
import com.sugar.android.common.utils.StringUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Route(path = GlobalRouter.USER_ADDRESS)
public class UserAddressActivity extends BaseActivity implements AreaContract.View, View.OnClickListener {
    public static final int PROVINCE_CODE = 0;
    public static final int CITY_CODE = 1;
    public static final int DISTICT_CODE = 2;
    final SelectedAddress selectedAddress = new SelectedAddress();
    private AppCompatTextView tvUserAddress;
    private AppCompatEditText etUserAddressDetail;
    private SimpleTextAdapter simpleTextAdapter;
    private List<AddressBean> provinceBaseBeans = new ArrayList<>();
    private List<AddressBean> cityBaseBeans = new ArrayList<>();
    private List<AddressBean> areaBaseBeans = new ArrayList<>();
    private AreaPresenter mPresenter;
    private TextView tvPop;
    /**
     * 0:选择省份
     * 1:选择城市
     * 2：选择区域
     */
    private int selectAddressType = 0;


    @Override
    protected int setContentLayoutId() {
        return R.layout.my_user_address_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mPresenter = new AreaPresenter();
        this.mPresenter.attachView(this);

        this.setActionBarTitle("现居地址");
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
        this.showRightBtn(true, "保存");

        this.tvUserAddress = this.findViewById(R.id.user_address_area);
        this.tvUserAddress.setOnClickListener(this);
        this.etUserAddressDetail = this.findViewById(R.id.user_address_details);
        RecyclerView mRecyclerView = this.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.simpleTextAdapter = new SimpleTextAdapter(this.provinceBaseBeans);
        this.tvPop = this.findViewById(R.id.retry_select);
        this.tvPop.setOnClickListener(this);
        this.simpleTextAdapter.setOnItemClickListener((adapter, view, position) -> {
            AddressBean baseAddressBean = (AddressBean) adapter.getData().get(position);
            selectOffice(adapter, baseAddressBean);
            UserAddressActivity.this.tvUserAddress.setText(UserAddressActivity.this.selectedAddress.province + UserAddressActivity.this.selectedAddress.city + UserAddressActivity.this.selectedAddress.distance);
        });

        mRecyclerView.setAdapter(this.simpleTextAdapter);

        parseOfficeData();
    }

    private void selectOffice(com.chad.library.adapter.base.BaseQuickAdapter adapter, AddressBean baseAddressBean) {
        switch (this.selectAddressType) {
            case PROVINCE_CODE:
                this.cityBaseBeans = baseAddressBean.getAreas();
                this.selectedAddress.province = baseAddressBean.getName();
                this.selectedAddress.provinceId = baseAddressBean.getId();
                adapter.setNewData(this.cityBaseBeans);
                this.selectAddressType = 1;
                this.tvPop.setText("点击重新选择");

                break;
            case CITY_CODE:
                this.areaBaseBeans = baseAddressBean.getAreas();
                this.selectAddressType = 2;
                this.selectedAddress.city = baseAddressBean.getName();
                this.selectedAddress.cityId = baseAddressBean.getId();
                adapter.setNewData(this.areaBaseBeans);
                break;
            case DISTICT_CODE:
                this.selectedAddress.distance = baseAddressBean.getName();
                this.selectedAddress.distanceId = baseAddressBean.getId();
                break;
            default:
        }
    }

    /**
     * 解析行政数据
     */
    private void parseOfficeData() {
        new Thread(() -> {
            UserAddressActivity.this.provinceBaseBeans = JSONObject.parseArray(SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST), AddressBean.class);
            UserAddressActivity.this.simpleTextAdapter.setNewData(UserAddressActivity.this.provinceBaseBeans);
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter.detachView();
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();

        if (StringUtils.isEmpty(this.selectedAddress.province) || StringUtils.isEmpty(this.selectedAddress.city) || StringUtils.isEmpty(this.selectedAddress.distance)
                || StringUtils.isEmpty(this.etUserAddressDetail.getText().toString())) {
            ToastUtils.showToast("请正确选择地区和填写地址");
            return;
        }

        HttpUtils.getInstance().request(new RequestEvent.Builder()
                .url(UrlPath.PATH_USER_ADDRESS_COMMIT.getUrl())
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .addBodyJson("province", this.selectedAddress.provinceId)
                .addBodyJson("city", this.selectedAddress.cityId)
                .addBodyJson("district", this.selectedAddress.distanceId)
                .addBodyJson("address", this.etUserAddressDetail.getText().toString())
                .build());
    }

    @Override
    public void onResponse(ResponseEvent event) {
        if (event.getUrl().equals(UrlPath.PATH_USER_ADDRESS_COMMIT.getUrl())) {
            SPUtils.putValue(KeyConstant.KEY_USER_ADDRESS, this.tvUserAddress.getText().toString());
            SPUtils.putValue(KeyConstant.KEY_USER_DETAIL_ADDRESS, this.etUserAddressDetail.getText().toString());
            this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == this.tvPop) {
            this.simpleTextAdapter.setNewData(this.provinceBaseBeans);
            this.selectAddressType = 0;
            this.tvPop.setText("请选择");
            this.tvUserAddress.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_ADDRESS));
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void updateUserAddress(UserBean bean) {
        this.tvUserAddress.setText(bean.getAllAddress());
        this.etUserAddressDetail.setText(bean.getAddress());
    }

    @Override
    public void onData(List<Address> addressList) {
        // 数据还没有准备好

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(@NotNull Throwable throwable) {

    }

    @Override
    public void onError(@NotNull String err) {

    }
}
