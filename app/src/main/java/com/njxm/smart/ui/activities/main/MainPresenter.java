/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities.main;

import com.njxm.smart.base.BasePresenter;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.tools.network.HttpUtils;
import com.sugar.android.common.utils.SPUtils;
import com.sugar.android.common.utils.StringUtils;

/**
 * Created by Hxin on 2021/4/2 Function:
 */
public class MainPresenter extends BasePresenter<MainView> {
    public static void fetchCountryAddresses() {
        if (StringUtils.isEmpty(SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST))
                || SPUtils.getStringValue(KeyConstant.KEY_COMMON_ADDRESS_LIST).equals("[]")) {
            HttpUtils.getInstance()
                    .request(RequestEvent.newBuilder()
                            .url(UrlPath.PATH_PROVINCE_CITY_AREA.getUrl()).build());
        }
    }
}
