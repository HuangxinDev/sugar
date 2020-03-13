package com.njxm.smart.activities;

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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.adapter.SimpleTextAdapter;
import com.njxm.smart.constant.GlobalRouter;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.AddressBean;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.ntxm.smart.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Route(path = GlobalRouter.USER_ADDRESS)
public class UserAddressActivity extends BaseActivity {

    private AppCompatTextView tvUserAddress;
    private AppCompatEditText etUserAddressDetail;

    private SimpleTextAdapter simpleTextAdapter;

    private List<AddressBean> provinceBaseBeans = new ArrayList<>();
    private List<AddressBean> cityBaseBeans = new ArrayList<>();
    private List<AddressBean> areaBaseBeans = new ArrayList<>();

    private TextView tvPop;

    String province = "";
    String city = "";
    String distance = "";

    String provinceId = "";
    String cityId = "";
    String distanceId = "";

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
        setActionBarTitle("现居地址");
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        showRightBtn(true, "保存");

        tvUserAddress = findViewById(R.id.user_address_area);
        tvUserAddress.setOnClickListener(this);
        etUserAddressDetail = findViewById(R.id.user_address_details);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleTextAdapter = new SimpleTextAdapter(provinceBaseBeans);
        tvPop = findViewById(R.id.retry_select);
        tvPop.setOnClickListener(this);
        simpleTextAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AddressBean baseAddressBean = (AddressBean) adapter.getData().get(position);
                switch (selectAddressType) {
                    case 0:
                        LogTool.printD("click province: %s", baseAddressBean.getName());
                        cityBaseBeans = baseAddressBean.getAreas();
                        province = baseAddressBean.getName();
                        provinceId = baseAddressBean.getId();
                        adapter.setNewData(cityBaseBeans);
                        selectAddressType = 1;
                        tvPop.setText("点击重新选择");

                        break;
                    case 1:
                        areaBaseBeans = baseAddressBean.getAreas();
                        selectAddressType = 2;
                        city = baseAddressBean.getName();
                        cityId = baseAddressBean.getId();
                        adapter.setNewData(areaBaseBeans);
                        break;
                    case 2:
                        distance = baseAddressBean.getName();
                        distanceId = baseAddressBean.getId();
                        break;
                    default:
                }

                tvUserAddress.setText(province + city + distance);
            }
        });

        mRecyclerView.setAdapter(simpleTextAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                provinceBaseBeans = JSONObject.parseArray(SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST), AddressBean.class);
                simpleTextAdapter.setNewData(provinceBaseBeans);
            }
        }).start();
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();

        if (StringUtils.isEmpty(province) || StringUtils.isEmpty(city) || StringUtils.isEmpty(distance)
                || StringUtils.isEmpty(etUserAddressDetail.getText().toString())) {
            showToast("请正确选择地区和填写地址");
            return;
        }

        HttpUtils.getInstance().request(new RequestEvent.Builder()
                .url(UrlPath.PATH_USER_ADDRESS_COMMIT.getUrl())
                .addBodyJson("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID))
                .addBodyJson("province", provinceId)
                .addBodyJson("city", cityId)
                .addBodyJson("district", distanceId)
                .addBodyJson("address", etUserAddressDetail.getText().toString())
                .build());
    }

    @Override
    public void onResponse(ResponseEvent event) {
        if (event.getUrl().equals(UrlPath.PATH_USER_ADDRESS_COMMIT.getUrl())) {
            SPUtils.putValue(KeyConstant.KEY_USER_ADDRESS, tvUserAddress.getText().toString());
            SPUtils.putValue(KeyConstant.KEY_USER_DETAIL_ADDRESS, etUserAddressDetail.getText().toString());
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == tvPop) {
            simpleTextAdapter.setNewData(provinceBaseBeans);
            selectAddressType = 0;
            tvPop.setText("请选择");
            tvUserAddress.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_ADDRESS));
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void updateUserAddress(UserBean bean) {
        tvUserAddress.setText(bean.getAllAddress());
        etUserAddressDetail.setText(bean.getAddress());
    }
}
