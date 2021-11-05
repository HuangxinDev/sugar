package com.njxm.smart.mvvm

interface UserCache {
    suspend fun get(userId: String): User?
    fun put(userId: String, freshUser: User) {
        TODO("Not yet implemented")
    }
}
