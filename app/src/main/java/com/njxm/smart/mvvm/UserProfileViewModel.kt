package com.njxm.smart.mvvm

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

    init {
        viewModelScope.launch {
//            user.value = userRepository.getUser(userId)
        }
    }
}