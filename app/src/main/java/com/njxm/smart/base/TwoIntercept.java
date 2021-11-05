package com.njxm.smart.base;

/**
 * @author huangxin
 * @date 2021/10/26
 */
public class TwoIntercept extends InterceptChain<String> {
    @Override
    void intercept(String data) {
        super.intercept(data);
        System.out.println("two intercept:  " + data);
    }
}
