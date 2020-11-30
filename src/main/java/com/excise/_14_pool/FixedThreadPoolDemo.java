package com.excise._14_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newFixedThreadPool(int)返回一个固定数量的线程池。当有新任务提交时,若线程池有空闲线程则分配，若没有任务会暂存在一个队列中，等待出现空闲线程
 * newSingleThreadExecutor()返回只有一个线程的线程池。当有多余任务提交时候，会保存在队列按照先入先出等待执行
 * newCachedThreadPool()返回可根据实际情况调整线程数量的线程池。有复用优先使用可复用的，没有新建，所有线程处理完任务后会返回线程池进行复用
 * newSingleThreadScheduledExecutor()线程池大小为1。没有空闲加入队列，扩展了在给定时间执行某任务，或者周期性执行
 * newScheduledThreadPool(int)同上，但可以指定线程数量
 */
public class FixedThreadPoolDemo {

    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        MyTask task = new MyTask();
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            es.submit(task);
        }
    }

}
