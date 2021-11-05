package com.njxm.smart.activities.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njxm.smart.model.jsonbean.AddressBean
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 1.0
 *
 * @author huangxin
 * @date 2021/7/20
 */
class LoginViewModel : ViewModel {
    var loginStatus = MutableLiveData<Int>()
    private var repository: LoginRepository? = null

    private val cities: MutableLiveData<List<AddressBean>> = MutableLiveData()

    constructor() {
        GlobalScope.launch {
            // download cities
            cities.value = null
        }
    }


    constructor(repository: LoginRepository) {
        this.repository = repository
        Thread { loginStatus.postValue(repository.login().code()) }.start()

        val rootNode = BinaryNode(8, 0)
        rootNode.right = 9
        rootNode.right = 10

    }

    open class BinaryNode<AnyType>(theElement: AnyType, left: AnyType? = null) {
        private var theElement: AnyType? = null
        private var left: AnyType? = null
        var right: AnyType? = null

        init {
            this.theElement = theElement
            this.left = left
        }
    }
}