package com.excise._3_suspend;

public class GoodSuspend {

    public static Object u = new Object();

    public static class ChangeObjectThread extends Thread {

        volatile boolean suspendme = false;

        public void suspendMe() {
            suspendme = true;
        }

        public void resumeMe() {
            suspendme = false;
            synchronized (this) {
                notify();
            }
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this) { // 这里锁对象使用u也可以
                    while (suspendme) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                synchronized (u) {
                    System.out.println("in ChangeObjectThread");
                }

                // 线程让步，把自己CPU执行的时间让掉，让自己或者其它的线程运行
                Thread.yield();
            }
        }

    }

    public static class ReadObjectThread extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    System.out.println("in ReadObjectThread");
                }

                Thread.yield();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread();
        ReadObjectThread t2 = new ReadObjectThread();

        t1.start();
        t2.start(); // t1和t2同时执行

        Thread.sleep(1000);

        t1.suspendMe(); // 1s之后让t1等待，利用wait方法，不影响其他线程执行，实现挂起

        System.out.println("suspend t1 2 sec");

        Thread.sleep(2000);

        System.out.println("resume t1");

        t1.resumeMe(); // 2s之后再让t1执行，与suspend与resume方法功能类似，但不会
    }

}
