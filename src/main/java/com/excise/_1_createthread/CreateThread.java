package com.excise._1_createthread;

/**
 * 创建多线程，建议用实现接口，因为继承是稀缺资源
 * 终止线程，建议在线程实现逻辑中判断线程状态进行终止，保证终止线程执行的位置，避免使用暴力stop方法引起安全问题
 */
public class CreateThread implements Runnable{

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
        Thread.yield(); // 重新竞争
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new CreateThread());
        t.start();
        //中断线程，便会触发Thread.currentThread().isInterrupted()，然后退出就可以了，可以避免stop方法导致的安全问题
        t.interrupt();
        while (true) {
            System.out.println("this is a main thread");
            Thread.sleep(100);
        }
    }

}
