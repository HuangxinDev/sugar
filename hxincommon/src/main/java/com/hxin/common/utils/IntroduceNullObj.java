package com.hxin.common.utils;

import androidx.lifecycle.ViewModel;

/**
 * 引入Null对象
 *
 * @author huangxin
 * @date 2021/8/15
 */
public class IntroduceNullObj {
    void test() {
        ViewModel viewModel = null;
        ViewModel defaultModel = new NullViewModel();


    }

    static class NullViewModel extends ViewModel {

    }
}
