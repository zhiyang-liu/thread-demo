package com.excise._12_tool;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏的两个异常：
 * 一个就是常规的在等待过程中的线程被中断会抛出InterruptedException
 * 一个是循环栅栏可能没有办法等到所有线程到齐了，而抛出BrokenBarrierException
 * 本例子，线程5会抛出InterruptedException，其他9个线程会抛出BrokenBarrierException，表示无法满足条件了
 */
public class CyclicBarrierDemo2 {

    public static class Soldier implements Runnable {

        private String soldierName;

        private final CyclicBarrier cyclicBarrier;

        Soldier(CyclicBarrier cyclicBarrier, String soldierName) {
            this.cyclicBarrier = cyclicBarrier;
            this.soldierName = soldierName;
        }

        public void run() {
            try {
                // 等待所有士兵到齐
                cyclicBarrier.await();
                doWork();
                // 等待所有士兵完成工作
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldierName + ":劳动完成");
        }

    }

    public static class BarrierRun implements Runnable {

        boolean flag;

        int N;

        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        public void run() {
            if (flag) {
                System.out.println("司令:[士兵" + N + "个，任务完成！]");
            } else {
                System.out.println("司令:[士兵" + N + "个，集合完毕！]");
                flag = true;
            }
        }
    }

    public static void main(String args[]) {
        final int N = 10;
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));

        Thread[] allSoldier = new Thread[N];

        // 设置屏障点，主要是为了执行这个方法
        System.out.println("集合队伍！");
        for (int i = 0; i < N; ++i) {
            System.out.println("士兵 " + i + " 报道！");
            allSoldier[i] = new Thread(new Soldier(cyclic, "士兵 " + i));
            allSoldier[i].start();
            if (i==5) {
                // 终止等待中的线程会抛InterruptedException
                allSoldier[0].interrupt();
            }
        }
    }

}
