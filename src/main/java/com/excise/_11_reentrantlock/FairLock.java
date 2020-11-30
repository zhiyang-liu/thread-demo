package com.excise._11_reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 当采用公平锁后，t1与t2将交替运行，如果非公平将杂乱无序（默认非公平锁）
 * synchronized是非公平锁
 */
public class FairLock implements Runnable {

    public static ReentrantLock fairLock = new ReentrantLock(true); // 设置公平锁

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+" 获得锁");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FairLock r1 = new FairLock();
        Thread t1 = new Thread(r1,"Thread_t1");
        Thread t2 = new Thread(r1,"Thread_t2");
        t1.start();
        t2.start();
    }

}
