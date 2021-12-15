package com.njxm.smart.activities;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class CountDownTimer {
    private final Timer timer;

    private CountDownLatch countDownLatch = new CountDownLatch(10);

    private int intervalInMills;

    private int initializeInMills;

    private OnTimeChanged timeChanged;


    public CountDownTimer() {
        this(0, 0);
    }

    public CountDownTimer(int intervalInMills, int initializeInMills) {
        this.intervalInMills = intervalInMills;
        this.initializeInMills = initializeInMills;
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeChanged != null) {
                    timeChanged.onTick();
                }
            }
        }, initializeInMills, intervalInMills);
    }

    public void start() {
    }

    public void onCancel() {
        timer.cancel();
    }

    interface OnTimeChanged {
        void onTick();

        void onFinish();
    }


}
