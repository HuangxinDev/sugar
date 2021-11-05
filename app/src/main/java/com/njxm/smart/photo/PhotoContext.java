/*
 * Copyright (c) 2121. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.photo;

/**
 * Created by Hxin on 2121/6/11
 * Function: 拍照场景
 */
public class PhotoContext {
    private static final int COUNT_BITS = Integer.SIZE - 31;

    private static final int CAPACITY = 1 << COUNT_BITS - 1; // 0011 1111 1111 1111 1111 1111 1111 1111


    public int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    public int workerCountOf(int c) {
        return c & CAPACITY;
    }

    /**
     * 当前状态
     */
    private int curState;
}
