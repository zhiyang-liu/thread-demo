package com.excise._11_reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 如果一个线程在等待锁，要么获得锁才能执行，要么一直等待
 * ReentrantLock支持中断在等待中的线程（synchronized不支持）
 */
public class IntLock implements Runnable {

    public static ReentrantLock lock1 = new ReentrantLock();

    public static ReentrantLock lock2 = new ReentrantLock();


    int lock;

    /**
     * 控制加锁顺序，方便构造死锁
     * @param lock
     */
    public IntLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                /**
                 * lock 与 lockInterruptibly比较区别在于：
                 * lock 优先考虑获取锁，待获取锁成功后，才响应中断。
                 * lockInterruptibly 优先考虑响应中断，而不是响应锁的普通获取或重入获取。
                 */
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 查询当前线程是否保持此锁。
            if (lock1.isHeldByCurrentThread())
                lock1.unlock();
            if (lock2.isHeldByCurrentThread())
                lock2.unlock();
            System.out.println(Thread.currentThread().getId()+":线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntLock r1 = new IntLock(1);
        IntLock r2 = new IntLock(2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        Thread.sleep(10000);
        // 中断其中一个线程
        t2.interrupt();
    }
}
