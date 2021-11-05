package com.hxin.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @author huangxin
 * @date 2021/8/14
 */
public class IntervalWindow implements Observer {

    private Interval interval;

    public IntervalWindow() {
        interval = new Interval(10);
        interval.addObserver(this);
        update(interval, null);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private List<String> list = new ArrayList<>();

    public List<String> getData() {
        return Collections.unmodifiableList(list);
    }

    public void test() {

    }
}
