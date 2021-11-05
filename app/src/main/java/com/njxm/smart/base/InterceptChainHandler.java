package com.njxm.smart.base;

/**
 * @author huangxin
 * @date 2021/10/26
 */
public class InterceptChainHandler<T> {
    InterceptChain<T> head = new InterceptChain<T>() {
        @Override
        void intercept(T data) {
            super.intercept(data);
        }
    };

    private InterceptChain<T> tail = null;

    void add(InterceptChain<T> interceptChain) {
        if (tail == null) {
            tail = head;
        }
        tail.next = interceptChain;
        tail = tail.next;
    }

    void intercept(T data) {
        head.next.intercept(data);
    }
}
