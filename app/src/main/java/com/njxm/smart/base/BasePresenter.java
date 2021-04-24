/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.base;

import java.lang.ref.WeakReference;

/**
 * Created by Hxin on 2021/4/2 Function:
 */
public abstract class BasePresenter<V extends BaseView> {
    private WeakReference<V> viewReference;

    public void attachView(V view) {
        this.viewReference = new WeakReference<>(view);
    }

    public V getView() {
        return this.viewReference != null ? this.viewReference.get() : null;
    }

    public boolean isAttached() {
        return this.viewReference != null && this.viewReference.get() != null;
    }

    public void detachView() {
        if (this.viewReference != null) {
            this.viewReference.clear();
            this.viewReference = null;
        }
    }
}
