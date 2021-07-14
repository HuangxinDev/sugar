package com.njxm.smart.ui.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njxm.smart.model.jsonbean.UserBean

class MainViewModel : ViewModel() {
    private var liveData: LiveData<UserBean> = MutableLiveData()


    public fun updateUser(userBean: UserBean?) {
        userBean?.let {
            liveData.apply {

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}