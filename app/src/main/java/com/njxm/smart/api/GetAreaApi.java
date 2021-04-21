/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.api;

import java.util.List;

import com.njxm.smart.model.jsonbean.AddressBean;

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
