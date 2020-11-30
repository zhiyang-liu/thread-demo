package com.excise._12_tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 三种多线程并发控制工具（信号量）
 * 信号量可以指定多个线程，同时访问某一资源
 * 程序将以5个线程为一组进行输出
 * 调用一次信号量加一个。信号量用完以后，后续使用acquire()方法请求信号的线程便会加入阻塞队列挂起。
 */
public class SemapDemo implements Runnable {

    final Semaphore semp = new Semaphore(5);

    @Override
    public void run() {
        try {
            semp.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + ":done!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semp.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        final SemapDemo demo = new SemapDemo();
        for (int i = 0; i < 20; i++) {
            exec.submit(demo);
        }
        /**
         * 1、停止接收新的submit的任务；
         * 2、已经提交的任务（包括正在跑的和队列中等待的）,会继续执行完成；
         * 3、等到第2步完成后，才真正停止；
         */
        exec.shutdown();
    }

}
