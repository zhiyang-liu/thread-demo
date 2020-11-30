package com.excise._6_group;

/**
 * 线程组的stop方法会停止组内的所有线程，它会遇到与Thread.stop同样的问题(未知执行到何处就强行终止，较危险)，
 * 除了安全问题外，如果在sleep阶段stop可能抛出终止异常
 */
public class ThreadGroupStop implements Runnable {

    @Override
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName()
                + "-" + Thread.currentThread().getName();
        while (true) {
            System.out.println("I am " + groupAndName);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup tg = new ThreadGroup("PrintGroup");
        Thread t1 = new Thread(tg, new ThreadGroupStop(),"T1");
        Thread t2 = new Thread(tg, new ThreadGroupStop(),"T2");
        t1.start();
        t2.start();
        Thread.sleep(3000);
        tg.stop();
    }

}
