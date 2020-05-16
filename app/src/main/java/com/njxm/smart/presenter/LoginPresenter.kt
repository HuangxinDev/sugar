package com.njxm.smart.presenter

import android.os.SystemClock
import com.alibaba.fastjson.JSONObject
import com.njxm.smart.api.GetSmsApi
import com.njxm.smart.contract.LoginContract
import com.njxm.smart.contract.LoginContract.Presenter
import com.njxm.smart.global.KeyConstant
import com.njxm.smart.model.LoginModel
import com.njxm.smart.tools.network.HttpUtils
import com.njxm.smart.utils.BitmapUtils
import com.njxm.smart.utils.SPUtils
import okhttp3.FormBody
import okhttp3.Request
import retrofit2.Retrofit
import java.io.IOException

/**
 * Created by Hxin on 2020/5/14
 * Function:
 */
class LoginPresenter : BasePresenter<LoginContract.View>(), Presenter {
    private var mLoginModel: LoginModel? = null

    init {
        mLoginModel = LoginModel()
    }

    override fun requestQrCode() {
        if (!isViewAttached()) {
            throw RuntimeException("视图未绑定，请先绑定视图")
        }

        val request = Request.Builder()
                .url("http://119.3.136.127:7777/auth/kaptcha/get")
                .post(FormBody.Builder().build())
                .build()

        HttpUtils.getInstance().okHttpClient.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                mView?.onError("网络异常，请稍后再试2")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val data = response.body()?.string()

                data?.let {
                    val dataObject = com.alibaba.fastjson.JSONObject.parseObject(it)
                    val jo = dataObject.getJSONObject("data")
                    val bitmapStr = jo.getString("kaptcha")
                    SPUtils.putValue(KeyConstant.KEY_QR_IMAGE_TOKEN, jo.getString("kaptchaToken"))
                    bitmapStr?.let {
                        mView?.onQrCode(BitmapUtils.stringToBitmap(bitmapStr))
                    }
                }
            }
        }
        )
    }

    override fun requestSms(mobile: String?, code: String?) {

        val smsApi = Retrofit.Builder()
                .client(HttpUtils.getInstance().okHttpClient)
                .baseUrl("http://119.3.136.121:7777")
                .build()
                .create(GetSmsApi::class.java)

        val jsonObject = JSONObject()
        jsonObject["mobile"] = mobile
        jsonObject["code"] = code
//        smsApi.sendSms()
    }

    override fun loginAccount(objectMap: Map<String, String>?) {
        mView?.showLoading()
        SystemClock.sleep(2000)
        mLoginModel?.login("")
        mView?.hideLoading()
        mView?.onLoginState(1)
    }

}