package com.njxm.smart.model;

import com.njxm.smart.bean.LoginBean;
import com.njxm.smart.contract.LoginContract;

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
