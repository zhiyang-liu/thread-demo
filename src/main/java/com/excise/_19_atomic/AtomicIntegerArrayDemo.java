package com.excise._19_atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 数组也能无锁：使用Unsafe类的CAS操作控制int[]在多线程下的安全性
 * 当前可用的原子数组有AtomicIntegerArray，AtomicLongArray，AtomicReferenceArray（普通对象数组）
 * 本示例不会有安全问题
 */
public class AtomicIntegerArrayDemo {

	static AtomicIntegerArray arr = new AtomicIntegerArray(10);

    public static class AddThread implements Runnable {

        public void run() {
           for (int k = 0; k < 10000; k++)
        	   arr.getAndIncrement(k % arr.length()); // 依次对每个值递增1
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
        System.out.println(arr);
	}

}
