package com.njxm.smart.base;

/**
 * @author huangxin
 * @date 2021/10/26
 */
public class OneIntercept extends InterceptChain<String> {
    @Override
    void intercept(String data) {
        if (data.equals("one")) {
            System.out.println("my data");
        } else {
            super.intercept(data);
        }
    }
}
