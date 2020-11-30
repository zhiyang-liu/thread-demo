package com.excise._32_stamped;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock的小陷阱
 * StampedLock内部挂起线程时候（写锁被占用导致读挂起），使用的是Unsafe.park，它在遇到线程中断那时候，会直接返回，不会报异常
 * 而StampedLock的死循环逻辑中没有处理中断的逻辑，因此中断后会再次执行循环
 * 如果在使用过程中，你执行了中断，而且退出条件又得不到满足，便会疯狂占用CPU
 */
public class StampedLockCPUDemo {

    static Thread[] holdCpuThreads = new Thread[3];

    static final StampedLock lock = new StampedLock();

    private static class HoldCPUReadThread implements Runnable {

        public void run() {
            long readLock = lock.readLock();
            System.out.println(Thread.currentThread().getName() + " 获得读锁");
            lock.unlockRead(readLock);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new Thread() {

            public void run() {
                long readLong = lock.writeLock();
                // LockSupport.parkNanos(600000000000L); // 600s
                LockSupport.parkNanos(15L * 1000 * 1000 * 1000); //15s   单位是微秒
                lock.unlockWrite(readLong);
            }

        }.start();

        Thread.sleep(100);

        for (int i = 0; i < 3; ++i) {
            holdCpuThreads[i] = new Thread(new HoldCPUReadThread());
            holdCpuThreads[i].start();
        }

        Thread.sleep(10000);

        // 线程中断后，会占用CPU
        for (int i = 0; i < 3; ++i) {
            holdCpuThreads[i].interrupt(); // 在10s的时候进行了三个读线程的中断，写线程是占用15s，中断后读线程依然可以执行
        }
    }

}
