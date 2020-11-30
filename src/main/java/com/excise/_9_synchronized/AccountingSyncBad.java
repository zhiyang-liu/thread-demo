package com.excise._9_synchronized;

/**
 * 两个线程指向了不同实例，使用的是两把不同的锁，非安全
 * 解决方法可以将increase改为static的，请求的是当前类，而非当前实例，安全
 */
public class AccountingSyncBad implements Runnable {

    static int i = 0;

    public synchronized void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            increase();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AccountingSyncBad());
        Thread t2 = new Thread(new AccountingSyncBad());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }

}
