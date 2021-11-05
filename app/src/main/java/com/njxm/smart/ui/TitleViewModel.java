package com.njxm.smart.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 标题ViewModel
 *
 * @author huangxin
 * @date 2021/8/6
 */
public class TitleViewModel extends ViewModel {
    public final MutableLiveData<String> title = new MutableLiveData<>();

    public void setTitle(String title) {
        this.title.setValue(title);
    }
}
