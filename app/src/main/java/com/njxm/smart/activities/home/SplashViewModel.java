package com.njxm.smart.activities.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 闪屏页ViewModel
 * <p>
 * ViewModel作用：
 * 1. 为页面组件提供数据，即LiveData
 * 2. 数据处理业务逻辑，和模型进行通信
 *
 * @author huangxin
 * @date 2021/7/18
 */
public class SplashViewModel extends ViewModel {
    LiveData<String> urls = new MutableLiveData<>();
}
