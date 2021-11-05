package com.njxm.smart.base

/**
 *
 * @author huangxin
 * @date 2021/10/26
 */
class TestIntercept {
}

fun main() {
    val handler = InterceptChainHandler<String>()
    handler.add(OneIntercept())
    handler.add(TwoIntercept())
    handler.intercept("one")
}