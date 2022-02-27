/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.model;

import com.njxm.smart.contract.LoginContract;
import com.njxm.smart.http.bean.LoginBean;

/**
 * Created by Hxin on 2020/5/14
 * Function: 需要Login的逻辑 以及 Login POJO对象
 */
public class LoginModel implements LoginContract.Model {

    @Override
    public LoginBean login(String json) {
        // TODO: 2020/5/14 做Json的解析并返回LoginBean的对象
        return null;
    }
}
