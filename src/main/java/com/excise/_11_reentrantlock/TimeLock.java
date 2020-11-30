package com.excise._11_reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * trylock方法可以设置超时时间，当等待时间超过限制便会中断
 * 如果不设置参数，会尝试获得锁，如果锁没有被占用则获得并返回true，否则将不会进行等待并返回false（详细见类TryLock）
 */
public class TimeLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                Thread.sleep(6000);
            } else {
                System.out.println("get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {lock.unlock();}
    }

    public static void main(String[] args) {
        TimeLock tl = new TimeLock();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);
        t1.start();
        t2.start();
    }
}
