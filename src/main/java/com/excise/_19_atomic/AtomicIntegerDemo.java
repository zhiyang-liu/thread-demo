package com.excise._19_atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 无锁的线程安全整数AtomicInteger
 * 如果使用i++，将会导致安全问题，可以加锁，但是AtomicInteger拥有比锁更好的性能
 * 可用的还有AtomicLong AtomicBoolean
 */
public class AtomicIntegerDemo {

    static AtomicInteger i = new AtomicInteger();

    public static class AddThread implements Runnable {

        public void run() {
           for (int k = 0; k < 10000; k++)
               i.incrementAndGet(); // CAS操作当前值加1，返回新值
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        for (int k = 0; k < 10; k++) {
            ts[k] = new Thread(new AddThread());
        }
        for (int k = 0; k < 10; k++) {
            ts[k].start();
        }
        for (int k = 0; k < 10; k++) {
            ts[k].join();
        }
        System.out.println(i);
    }

}
