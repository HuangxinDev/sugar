/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.presenter

import com.njxm.smart.contract.LoginContract
import com.njxm.smart.contract.LoginContract.Presenter
import com.njxm.smart.model.LoginModel

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
    }

    override fun requestSms(mobile: String?, code: String?) {
    }

    override fun loginAccount(objectMap: Map<String, String>?) {
        mView?.showLoading()
        mLoginModel?.login("")
        mView?.hideLoading()
        mView?.onLoginState(1)
    }
}