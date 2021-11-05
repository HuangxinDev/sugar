package com.njxm.smart.mvvm

/**
 *
 * @author huangxin
 * @date 2021/8/16
 */
class UserRepository {
//    private val webservice: Webservice = Retrofit.Builder()

    private val userCache: UserCache = TODO()

//    suspend fun getUser(userId: String): User {
//        val cached = userCache.get(userId)
//        if (cached != null) {
//            return cached;
//        }
//        val freshUser = webservice.getUser(userId)
//        userCache.put(userId, freshUser)
//        return freshUser
//    }
}