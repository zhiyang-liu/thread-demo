package com.excise._3_suspend;

/**
 * 挂起（suspend）和继续执行（resume）线程
 * suspend方法线程挂起后，并不会释放任何锁资源，其他任何线程想要访问被它占用的锁时都得等待
 */
public class BadSuspend {

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
                Thread.currentThread().suspend();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(2000);
        t2.start();
        t1.resume();
        // 唤醒t2的时候，t2可能还没有执行到挂起操作，所以t2可能永远挂起,程序就不会终止
        t2.resume();
        // 线程没有执行完之前，会一直阻塞在join方法处
        t1.join();
        t2.join();
    }

}
