package com.njxm.smart.http;

import com.njxm.smart.bean.PermissionBean;
import com.njxm.smart.bean.ServerResponseBean;
import com.njxm.smart.http.bean.LoginBean;
import com.njxm.smart.http.login.LoginResponse;
import com.njxm.smart.model.jsonbean.AddressBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 网络接口数据接口
 *
 * @author huangxin
 * @date 2022/2/27
 */
public interface WebService {
    @POST(value = "/sys/provinceAndCity/findAll")
    @Headers(value = {"Platform:APP", "Content-Type:application/json"})
    Call<ResponseBody> getAreaData();

    @POST(value = "/sys/provinceAndCity/findAll")
    @Headers(value = {"Platform:APP", "Content-Type:application/json"})
    Call<AddressBean> testArrayTest();

    @POST("/auth/sms/sendSms")
    void sendSms(@Body Converter<String, String> converter);

    @POST("/api/sys/user/findResourceList")
    Call<ServerResponseBean<List<PermissionBean>>> getFeatureItems();

    @POST("/auth/{way}/login")
    @Headers(value = {"Content-Type:application/x-www-form-urlencoded"})
    Call<ServerResponseBean<LoginBean>> login(@Path("way") String name, @QueryMap Map<String, String> params);

    Call<LoginResponse> login();

    Response<LoginResponse> requestLogin();

    @POST("auth/kaptcha/get")
    void requestQrCode();

    /**
     *
     */
    @FormUrlEncoded
    @POST("/abc/abc/{index}")
    @Headers({"Content-Type:application/json",
            "Platform:APP"})
    @Multipart
    void test(@Path("index") String index, @PartMap(encoding = "utf-8") MultipartBody.Part data, @FieldMap String... values);

    @Multipart
    void postFile(@Part okhttp3.MultipartBody.Part... parts);

    @POST("{path}")
    @Multipart
    Call<ServerResponseBean> uploadFile(@Path(value = "path") String path, @Part List<MultipartBody.Part> parts);

    @Multipart
    @POST("/api/sys/user/updateFace")
    Call<ServerResponseBean> uploadFacePhoto(@Part MultipartBody.Part... part);
}
