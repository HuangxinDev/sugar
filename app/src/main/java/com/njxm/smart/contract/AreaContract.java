/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.contract;

import java.util.List;

import android.location.Address;

import androidx.annotation.UiThread;

import com.njxm.smart.view.BaseView;

/**
 * Created by Hxin on 2020/5/17 Function:
 */
public interface AreaContract {
    interface Model {
        List<Address> requestAddress();
    }

    interface View extends BaseView {
        @UiThread
        void onData(List<Address> addressList);
    }

    interface Presenter {
        void requestAddress();
    }
}
