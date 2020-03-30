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
