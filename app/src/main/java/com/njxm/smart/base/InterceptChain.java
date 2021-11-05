package com.njxm.smart.base;

/**
 * @author huangxin
 * @date 2021/10/26
 */
public abstract class InterceptChain<T> {
    InterceptChain<T> next;

    void intercept(T data) {
        if (next != null) {
            next.intercept(data);
        }
    }
}
