package com.njxm.smart.contract;

import com.njxm.smart.view.BaseView;

import java.util.List;

import android.location.Address;
import androidx.annotation.UiThread;

/**
 * Created by Hxin on 2020/5/17
 * Function:
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
