package com.njxm.smart.api;

import com.njxm.smart.model.jsonbean.AddressBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Hxin on 2020/3/28
 * Function: 获取省市区数据的接口
 */
public interface GetAreaApi {

    @POST(value = "/sys/provinceAndCity/findAll")
    @Headers(value = {"Platform:APP", "Content-Type:application/json"})
    Call<ResponseBody> getAreaData();

    @POST(value = "/sys/provinceAndCity/findAll")
    @Headers(value = {"Platform:APP", "Content-Type:application/json"})
    Call<List<AddressBean>> testArrayTest();
}
