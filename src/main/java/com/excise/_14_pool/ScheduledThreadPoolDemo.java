package com.excise._14_pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * FixedDelay周期性调度，在上一个任务结束之后，再经过delay时间进行任务调度
 * FixedRate同样是周期性调度，是以一个任务的开始执行时间作为起点，再经过period时间再调度
 * 本例子会每隔3秒输出一次
 * schedule会在给定时间对任务进行一次调度
 * newScheduledThreadPool与newSingleThreadScheduledExecutor的区别只是前者可以指定线程数量
 */
public class ScheduledThreadPoolDemo {

    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);

        //如果前面的任务没有完成，则调度也不会启动
        ses.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(System.currentTimeMillis() / 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, 0, 2, TimeUnit.SECONDS);

        ses.schedule(new Runnable() {

            @Override
            public void run() {
                System.out.println("定时任务开始执行！！！");
            }

        }, 5, TimeUnit.SECONDS);
    }

}
