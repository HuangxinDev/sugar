package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.adapter.SimpleTextAdapter;
import com.njxm.smart.global.HttpUrlGlobal;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.BaseAddressBean;
import com.njxm.smart.model.jsonbean.ProvinceBean;
import com.njxm.smart.tools.network.HttpCallBack;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.LogTool;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.utils.StringUtils;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UserAddressActivity extends BaseActivity {

    private AppCompatTextView tvUserAddress;
    private AppCompatEditText etUserAddressDetail;

    private RecyclerView mRecyclerView;

    private List<BaseAddressBean> mProvinceBeans = new ArrayList<>();

    private boolean selectSuccess = false;

    private SimpleTextAdapter simpleTextAdapter;

    private List<BaseAddressBean> proviceBaseBeans;
    private List<BaseAddressBean> cityBaseBeans;
    private List<BaseAddressBean> areaBaseBeans;

    String province;
    String city;
    String distance;

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
        tvUserAddress.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_ADDRESS));
        tvUserAddress.setOnClickListener(this);
        etUserAddressDetail = findViewById(R.id.user_address_details);
        etUserAddressDetail.setText(SPUtils.getStringValue(KeyConstant.KEY_USER_DETAIL_ADDRESS));
        mProvinceBeans.addAll(JSONObject.parseArray(SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST), ProvinceBean.class));

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleTextAdapter = new SimpleTextAdapter(mProvinceBeans);
        simpleTextAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BaseAddressBean baseAddressBean = (BaseAddressBean) adapter.getData().get(position);

                if (selectSuccess) {
                    showToast("选择结束");
                    return;
                }

                switch (baseAddressBean.getType()) {
                    case 0:
                        if (selectAddressType == 0) {
                            selectAddressType = 1;
                            proviceBaseBeans = adapter.getData();
                        }
                        province = baseAddressBean.getBaseCode();
                        tvUserAddress.setText("");
                        break;
                    case 1:
                        if (selectAddressType == 1) {
                            selectAddressType = 2;
                            cityBaseBeans = adapter.getData();
                        }
                        city = baseAddressBean.getBaseCode();
                        break;
                    case 2:
                        if (selectAddressType == 2) {
                            areaBaseBeans = adapter.getData();
                        }
                        selectSuccess = true;
                        distance = baseAddressBean.getBaseCode();
                        break;
                    default:
                }


                tvUserAddress.setText(String.format("%s%s",
                        tvUserAddress.getText().toString().trim(), baseAddressBean.getBaseName()));


                if (baseAddressBean.getBaseAddressBeans() != null && baseAddressBean.getBaseAddressBeans().size() > 0) {
                    adapter.setNewData(baseAddressBean.getBaseAddressBeans());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        mRecyclerView.setAdapter(simpleTextAdapter);
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();

        if (StringUtils.isEmpty(province) || StringUtils.isEmpty(city) || StringUtils.isEmpty(distance)
                || StringUtils.isEmpty(etUserAddressDetail.getText().toString())) {
            showToast("请填写相应信息");
            return;
        }

        JSONObject object = new JSONObject();
        object.put("id", SPUtils.getStringValue(KeyConstant.KEY_USER_ID));
        object.put("province", province);
        object.put("city", city);
        object.put("district", distance);
        object.put("address", etUserAddressDetail.getText().toString());

        RequestBody requestBody =
                FormBody.create(MediaType.parse(HttpUrlGlobal.CONTENT_JSON_TYPE), object.toJSONString());

        Request request = new Request.Builder().url("http://119.3.136.127:7776/api/sys/user/updateAddress")
                .addHeader("Platform", "APP")
                .addHeader("Content-Type", HttpUrlGlobal.CONTENT_JSON_TYPE)
                .addHeader("Account", SPUtils.getStringValue(KeyConstant.KEY_USER_ACCOUNT))
                .addHeader("Authorization", "Bearer-" + SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
                .post(requestBody)
                .build();

        HttpUtils.getInstance().postData(-1, request, new HttpCallBack() {
            @Override
            public void onSuccess(int requestId, boolean success, int code, String data) {
                LogTool.printD("上传成功，code: %s", code);
                SPUtils.putValue(KeyConstant.KEY_USER_ADDRESS, tvUserAddress.getText().toString());
                SPUtils.putValue(KeyConstant.KEY_USER_DETAIL_ADDRESS, etUserAddressDetail.getText().toString());
                finish();
            }

            @Override
            public void onFailed(String errMsg) {

            }
        });
    }
}
