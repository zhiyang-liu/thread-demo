package com.excise.synctrl.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition对象与wait和notify方法的作用基本相同
 * condition.signal()要求线程持有相关的重入锁才能起作用
 * 一般情况下，需要释放线程持有相关的重入锁，然后被唤醒的线程才能获得锁继续执行
 */
public class ReenterLockCondition implements Runnable{
    public static ReentrantLock lock=new ReentrantLock();
    public static Condition condition = lock.newCondition();
    @Override
    public void run() {
        try {
            lock.lock();
            condition.await();
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ReenterLockCondition tl=new ReenterLockCondition();
        Thread t1=new Thread(tl);
        t1.start();
        Thread.sleep(2000);
        //通知线程t1继续执行
        lock.lock();
        condition.signal();
		lock.unlock();
    }
}