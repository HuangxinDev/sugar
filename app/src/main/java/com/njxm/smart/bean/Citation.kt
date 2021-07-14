package com.njxm.smart.bean

class Citation constructor(var name: String, var info: String, var collage: String) : Cloneable {
    override public fun clone(): Any {
        return super.clone()
    }
}

fun main() {
    val citation: Citation = Citation("huangxin", "三号学生", "98")
    val citation1 = citation.clone()
}