package com.njxm.smart.api;


import com.njxm.smart.bean.ServerResponseBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Hxin on 2020/3/31
 * Function: 上传文件接口
 */
public interface UploadFileApi {

    @POST("{path}")
    @Multipart
    Call<ServerResponseBean> uploadFile(@Path(value = "path") String path, @Part List<MultipartBody.Part> parts);
}
