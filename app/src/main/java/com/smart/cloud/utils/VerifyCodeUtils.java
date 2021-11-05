package com.smart.cloud.utils;

import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.tools.network.HttpUtils;

/**
 * @author huangxin
 * @date 2021/8/10
 */
public class VerifyCodeUtils {
    public static void getQRCode() {
        HttpUtils.getInstance().request(RequestEvent.newBuilder().url(UrlPath.PATH_PICTURE_VERIFY.getUrl()).build());
    }
}
