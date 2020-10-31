package com.excise._4_join;

    /**
    * Java线程中的Thread.yield( )方法，译为（谦让）线程让步。顾名思义，就是说当一个线程使用了这个方法之后，它就会把自己CPU执行的时间让掉，
    * 让自己或者其它的线程运行，注意是让自己或者其他线程运行，并不是单纯的让给其他线程。
    *
    * join方法（线程等待）的作用是阻塞当前线程，直到目标线程执行完毕，
    */
public class JoinMain {

    public volatile static int i = 0;

    public static class AddThread extends Thread {

        @Override
        public void run() {
            for (i = 0; i < 10000000; i++);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        AddThread at = new AddThread();
        at.start();
        // main线程会获得线程对象at,并调用at的wait方法，进入对象at的的等待池
        // at执行完后会调用notifyAll唤醒对象at的所有等待池中的队列
        at.join();
        System.out.println(i);
    }

}
