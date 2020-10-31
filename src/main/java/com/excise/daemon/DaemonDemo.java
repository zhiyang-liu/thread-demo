package com.excise._1_daemon;

/**
 * 设置守护线程必须在start之前设置
 * 系统只有main线程一个用户线程，当主线程结束后，守护线程
 */
public class DaemonDemo {
    public static class DaemonT extends Thread{
        public void run(){
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
        Thread t=new DaemonT();
        t.setDaemon(true);
        t.start();

        Thread.sleep(2000);
    }
}
