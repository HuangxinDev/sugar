/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.api;

import com.njxm.smart.bean.ServerResponseBean;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Hxin on 2020/3/30
 * Function: 上传录入人脸头像接口
 */
public interface UploadInputFaceApi {

    @Multipart
    @POST("/api/sys/user/updateFace")
    Call<ServerResponseBean> uploadFacePhoto(@Part MultipartBody.Part... part);
}
