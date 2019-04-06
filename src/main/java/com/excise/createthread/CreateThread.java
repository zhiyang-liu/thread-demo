package com.excise.createthread;

/**
 * 创建多线程，建议用实现接口，因为继承是稀缺资源
 */
public class CreateThread implements Runnable{
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new CreateThread());
        t.start();
        t.interrupt();//中断线程，便会触发Thread.currentThread().isInterrupted()，然后退出就可以了，可以避免stop方法导致的安全问题
        while (true) {
            System.out.println("this is a main thread");
            Thread.sleep(100);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("线程被中断了！");
                    break;
                }
                System.out.println("this is a test thread");
                Thread.sleep(100);
            }
        } catch (Exception e) {

        }
        Thread.yield();//重新竞争
    }
}
