/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.model;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.location.Address;

import com.njxm.smart.contract.AreaContract;

/**
 * Created by Hxin on 2020/5/17 Function: abc
 */
public class AreaModel implements AreaContract.Model {

    private static final ThreadPoolExecutor sTHREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            1, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    TimeUnit unit;
    private BlockingQueue workQueue;

    /**
     * Return {@code true} if
     *
     * @return
     */
    @Override
    public List<Address> requestAddress() {

        AreaModel.sTHREAD_POOL_EXECUTOR.submit(new Callable<AreaModel>() {

            @Override
            public AreaModel call() throws Exception {
                return null;
            }
        });

        return null;
    }
}
