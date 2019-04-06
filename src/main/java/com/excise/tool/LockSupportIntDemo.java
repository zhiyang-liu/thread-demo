package com.excise.tool;

import java.util.concurrent.locks.LockSupport;

/**
 * 中断处于park状态的线程，线程马上就会响应，t1执行结束才能继续执行t2
 *实际上中断后，将使得线程继续向下执行，需要Thread.interrupted()来捕获中断
 */
public class LockSupportIntDemo {
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
                LockSupport.park();
                if(Thread.interrupted()){
                    System.out.println(getName()+" 被中断了");
                }
            }
            System.out.println(getName()+"执行结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.interrupt();
        LockSupport.unpark(t2);
    }
}
