/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.api;

import java.util.List;

import com.njxm.smart.bean.PermissionBean;
import com.njxm.smart.bean.ServerResponseBean;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Hxin on 2020/3/28
 * Function: 工作中心用户功能列表数据
 */
public interface GetUserFuctionItemsApi {

    @POST("/api/sys/user/findResourceList")
    Call<ServerResponseBean<List<PermissionBean>>> getFeatureItems();
}
