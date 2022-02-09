package com.njxm.smart.mvvm

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 *
 * @author huangxin
 * @date 2021/8/16
 */
open class UserProfileViewModel(savedStateHandle: SavedStateHandle, userRepository: UserRepository) : ViewModel() {
    val userId: String = savedStateHandle["uid"] ?: throw IllegalArgumentException("missing user id")
    val user: LiveData<User> = TODO()

    var userName: String

    var quickLogin = false

    init {
        viewModelScope.launch {
//            user.value = userRepository.getUser(userId)
        }
    }


    fun login(view: View) {
        if (quickLogin) {

        } else {
            
        }
    }
}