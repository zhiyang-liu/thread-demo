package com.excise.tool;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport:线程阻塞工具（即使unpark方法发生在park方法之前，也能保证唤醒）
 * （同时park方法不会挂起线程还不释放锁，给出Runnable状态，它会给出WAITING状态（只是返回这个状态实际上还是不会释放锁，wait方法会））
 * 与thread.suspend相比，它弥补了调用resume方法时还没有执行到suspend方法导致的线程永远挂起
 * 与object.wait方法相比，它不需要获得某个对象的锁
 * wait方法会释放锁资源，suspend与LockSupport都不会释放锁资源
 */
public class LockSupportDemo {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name){
            super.setName(name);
        }
        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in "+getName());
                LockSupport.park(this);//可以设置一个对象参数，相当于为当前线程设置一个阻塞对象，
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
