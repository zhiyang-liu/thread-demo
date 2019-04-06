package com.excise.vol;

/**
 *  volatile也不能完全保证数据的可见性和一致性，
 *  在server模式下。由于系统优化的结果，ReaderThread无法看到主线程的结果,可以使用java虚拟机参数-server来执行
 *  没有经过测试
 */
public class NoVisibility {
    private volatile static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready);
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);
        number = 42;
        ready = true;
        Thread.sleep(10000);
    }
}
