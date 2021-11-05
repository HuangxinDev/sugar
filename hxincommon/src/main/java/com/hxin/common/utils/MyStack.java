package com.hxin.common.utils;

import java.util.Vector;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class MyStack {

    private final Vector vector = new Vector();

    public void push(Object element) {
        vector.insertElementAt(element, 0);
    }

    public Object pop() {
        Object result = vector.firstElement();
        vector.removeElementAt(0);
        return result;
    }

    public int size() {
        return vector.size();
    }

    public boolean isEmpty() {
        return vector.isEmpty();
    }
}
