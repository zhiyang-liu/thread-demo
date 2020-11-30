package com.excise._7_daemon;

/**
 * 设置守护线程必须在start之前设置
 * 系统只有main线程一个用户线程，当主线程结束后，守护线程自然退出
 * 如果不设置为守护线程，那么主线程结束后，自定义线程仍继续执行
 */
public class DaemonDemo {

    public static class DaemonT extends Thread {

        public void run() {
            while(true){
                System.out.println("I am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static void main(String[] args) throws InterruptedException {
        Thread t = new DaemonT();
        t.setDaemon(true);
        t.start();

        Thread.sleep(2000);
    }
}
