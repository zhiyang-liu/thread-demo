package com.excise.waitnotify;

/**
 * 简单的wait和notify方法，t1在接到notify通知后，会尝试获得object的对象锁（并不能真正执行），直到t2执行结束才能真正获得
 */
public class SimpleWN {
    final static Object object = new Object();
    public static class T1 extends Thread {
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis()+":T1 start! ");
                try {
                    System.out.println(System.currentTimeMillis()+":T1 wait for object ");
                    object.wait();//，释放对象锁。wait可以设置等待时间那么久不用notify唤醒了
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+":T1 end!");
            }
        }
    }
    public static class T2 extends Thread {
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis()+":T2 start! notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis()+":T2 end!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new T1() ;
        Thread t2 = new T2() ;

        //再开启一个t1将导致有一个t1，永远无法唤醒,除非将notify改成notifyAll
        Thread t1_1 = new T1() ;
        t1_1.start();
        Thread.sleep(2000);
        t1.start();
        Thread.sleep(2000);
        t2.start();
    }
}
