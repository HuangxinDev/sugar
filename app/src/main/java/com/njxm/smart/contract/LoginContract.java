/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.contract;

import android.graphics.Bitmap;

import androidx.annotation.UiThread;

import com.njxm.smart.http.bean.LoginBean;
import com.njxm.smart.view.BaseView;

import java.util.Map;

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

        @UiThread
        void showQrCode(Bitmap bitmap);
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
