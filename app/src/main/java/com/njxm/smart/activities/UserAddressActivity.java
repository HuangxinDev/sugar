package com.njxm.smart.activities;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.adapter.SimpleTextAdapter;
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
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.ntxm.smart.R;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@Route(path = GlobalRouter.USER_ADDRESS)
public class UserAddressActivity extends BaseActivity implements AreaContract.View {

    String province = "";
    String city = "";
    String distance = "";
    String provinceId = "";
    String cityId = "";
    String distanceId = "";
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
        this.simpleTextAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AddressBean baseAddressBean = (AddressBean) adapter.getData().get(position);
                switch (com.njxm.smart.activities.UserAddressActivity.this.selectAddressType) {
                    case 0:
                        LogTool.printD("click province: %s", baseAddressBean.getName());
                        com.njxm.smart.activities.UserAddressActivity.this.cityBaseBeans = baseAddressBean.getAreas();
                        com.njxm.smart.activities.UserAddressActivity.this.province = baseAddressBean.getName();
                        com.njxm.smart.activities.UserAddressActivity.this.provinceId = baseAddressBean.getId();
                        adapter.setNewData(com.njxm.smart.activities.UserAddressActivity.this.cityBaseBeans);
                        com.njxm.smart.activities.UserAddressActivity.this.selectAddressType = 1;
                        com.njxm.smart.activities.UserAddressActivity.this.tvPop.setText("点击重新选择");

                        break;
                    case 1:
                        com.njxm.smart.activities.UserAddressActivity.this.areaBaseBeans = baseAddressBean.getAreas();
                        com.njxm.smart.activities.UserAddressActivity.this.selectAddressType = 2;
                        com.njxm.smart.activities.UserAddressActivity.this.city = baseAddressBean.getName();
                        com.njxm.smart.activities.UserAddressActivity.this.cityId = baseAddressBean.getId();
                        adapter.setNewData(com.njxm.smart.activities.UserAddressActivity.this.areaBaseBeans);
                        break;
                    case 2:
                        com.njxm.smart.activities.UserAddressActivity.this.distance = baseAddressBean.getName();
                        com.njxm.smart.activities.UserAddressActivity.this.distanceId = baseAddressBean.getId();
                        break;
                    default:
                }

                com.njxm.smart.activities.UserAddressActivity.this.tvUserAddress.setText(com.njxm.smart.activities.UserAddressActivity.this.province + com.njxm.smart.activities.UserAddressActivity.this.city + com.njxm.smart.activities.UserAddressActivity.this.distance);
            }
        });

        mRecyclerView.setAdapter(this.simpleTextAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                com.njxm.smart.activities.UserAddressActivity.this.provinceBaseBeans = JSONObject.parseArray(SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST), AddressBean.class);
                com.njxm.smart.activities.UserAddressActivity.this.simpleTextAdapter.setNewData(com.njxm.smart.activities.UserAddressActivity.this.provinceBaseBeans);
            }
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

        if (StringUtils.isEmpty(this.province) || StringUtils.isEmpty(this.city) || StringUtils.isEmpty(this.distance)
                || StringUtils.isEmpty(this.etUserAddressDetail.getText().toString())) {
            com.njxm.smart.activities.BaseActivity.showToast("请正确选择地区和填写地址");
            return;
        }

        HttpUtils.getInstance().request(new RequestEvent.Builder()
                .url(UrlPath.PATH_USER_ADDRESS_COMMIT.getUrl())
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .addBodyJson("province", this.provinceId)
                .addBodyJson("city", this.cityId)
                .addBodyJson("district", this.distanceId)
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
        super.onClick(v);
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
