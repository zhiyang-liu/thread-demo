package com.excise._11_reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个线程可以连续几次获得同一把锁，就是可重入锁，synchronized也是可重入锁
 * 重入锁实现重入性：每个锁关联一个线程持有者和计数器
 */
public class ReenterLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();

    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            lock.lock();
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock tl = new ReenterLock();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }

}
