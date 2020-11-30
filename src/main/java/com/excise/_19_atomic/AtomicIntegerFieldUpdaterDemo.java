package com.excise._19_atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 1. AtomicIntegerFieldUpdater有访问权限（score不能使用private）
 * 2. 必须是非静态字段
 * 3. 必须有volatile
 * 支持三种让普通变量也享受原子操作，分别为int，long和对象
 * 本示例是模拟一个投票场景，让score自增，如果非原子操作将会有安全问题，让自定义对象中的score字段享受原子操作
 * 结果与使用AtomicInteger一样所以是正确的
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static class Candidate {
        int id;
        volatile int score;
    }

    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    // 检查Updater是否工作正确
    public static AtomicInteger allScore = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final Candidate stu = new Candidate();
        Thread[] t = new Thread[10000];
        for (int i = 0 ; i < 10000 ; i++) {
            t[i] = new Thread() {
                public void run() {  
                    if (Math.random() > 0.4) {
                        scoreUpdater.incrementAndGet(stu);
                        allScore.incrementAndGet();
                    }
                }  
            };
            t[i].start();
        }  
        for (int i = 0 ; i < 10000 ; i++) {
            t[i].join();
        }
        System.out.println("score=" + stu.score);
        System.out.println("allScore=" + allScore);
    }

}
