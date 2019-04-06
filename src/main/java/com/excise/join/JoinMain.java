package com.excise.join;

    /**
    * Java线程中的Thread.yield( )方法，译为（谦让）线程让步。顾名思义，就是说当一个线程使用了这个方法之后，它就会把自己CPU执行的时间让掉，
    * 让自己或者其它的线程运行，注意是让自己或者其他线程运行，并不是单纯的让给其他线程。
    *
    * join方法（线程等待）的作用是阻塞当前线程，直到目标线程执行完毕，
    */
public class JoinMain {
    public volatile static int i=0;
    public static class AddThread extends Thread{
        @Override
        public void run() {
            for(i=0;i<10000000;i++);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        AddThread at=new AddThread();
        at.start();
        at.join();//main线程会获得线程对象threadA的锁（wait 意味着拿到该对象的锁),
        // 调用该对象的wait(等待时间)，直到该对象唤醒main线程 （也就是子线程threadA执行完毕退出的时候）
        System.out.println(i);
    }
}
