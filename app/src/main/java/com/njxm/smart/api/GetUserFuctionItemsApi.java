package com.njxm.smart.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Hxin on 2020/3/28
 * Function: 工作中心用户功能列表数据
 */
public interface GetUserFuctionItemsApi {

    @POST("/api/sys/user/findResourceList")
    Call<ResponseBody> getData();
}
