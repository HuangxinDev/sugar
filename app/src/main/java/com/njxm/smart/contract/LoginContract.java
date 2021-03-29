package com.njxm.smart.contract;

import com.njxm.smart.bean.LoginBean;
import com.njxm.smart.view.BaseView;

import java.util.Map;

import android.graphics.Bitmap;

/**
 * Created by Hxin on 2020/5/14
 * Function: 登录页面契约合同
 */
public interface LoginContract {
    interface Model {
        LoginBean login(String json);
    }

    interface View extends BaseView {
        void onLoginState(int state);

        void onQrCode(Bitmap bitmap);
    }

    interface Presenter {
        /**
         * 用户登录
         *
         * @param objectMap 登录请求的参数信息
         */
        void loginAccount(Map<String, String> objectMap);

        /**
         * 请求二维码信息
         */
        void requestQrCode();

        /**
         * 请求短信信息
         */
        void requestSms(String mobile, String code);
    }
}
