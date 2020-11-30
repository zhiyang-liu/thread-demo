package com.excise._2_waitnotify;

/**
 * 简单的wait和notify方法，t1在接到notify通知后，会尝试获得object的对象锁（并不能真正执行），直到t2执行结束才能真正获得
 * notify只能随机从等待队列唤醒一个线程，全部唤醒需要notifyAll
 *
 * 等待池：
 *  线程A调用某个对象的wait方法，线程A就会释放该对象锁，同时进入该锁对象的等待池中，进入等待池中的线程不会去竞争该对象的锁
 *
 * 锁池：
 *  假设线程A已经拥有对象锁，线程B、C想要获取锁就会被阻塞，进入一个地方去等待锁的等待，这个地方就是该对象的锁池
 */
public class SimpleWN {

    // wait和notify方法为Object类提供
    final static Object object = new Object();

    public static class T1 extends Thread {

        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T1 start! ");
                try {
                    System.out.println(System.currentTimeMillis() + ":T1 wait for object ");
                    // 释放对象锁。wait可以设置等待时间那么久不用notify唤醒了
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":T1 end!");
            }
        }

    }

    public static class T2 extends Thread {

        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T2 start! notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis() + ":T2 end!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new T1();
        t1.start();
        Thread.sleep(2000);

        // 再开启一个t1将导致有一个t1，永远无法唤醒,除非将notify改成notifyAll
        Thread t12 = new T1();
        t12.start();
        Thread.sleep(2000);

        Thread t2 = new T2();
        t2.start();
    }

}
