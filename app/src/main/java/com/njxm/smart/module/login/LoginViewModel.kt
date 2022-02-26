package com.njxm.smart.module.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

/**
 * Login ViewModel
 * @author huangxin
 * @date 2022/2/22
 */
class LoginViewModel : ViewModel() {
    var username: LiveData<String>? = null
    var userpassword: LiveData<String>? = null
    var userPhone: LiveData<String>? = null
    var userVerfiyCode: LiveData<String>? = null
    var code: LiveData<String>? = null
    

}