package com.njxm.smart.api;

import com.njxm.smart.bean.PermissionBean;
import com.njxm.smart.bean.ServerResponseBean;

import java.util.List;

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
