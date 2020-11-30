package com.excise._15_fork;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join框架，分而治之思想,类似于mapreduce程序思想，依赖于ForkJoin线程池便能解决多个线程不同时执行耗尽单机资源
 * fork方法开启线程进行处理
 *
 * RecursiveTask：有返回值的ForkJoinTask子类
 * RecursiveAction：无返回值的ForkJoinTask子类
 */

/**
 * java.lang.NoClassDefFoundError: Could not initialize class java.util.concurrent.locks.AbstractQueuedSynchronizer$Node
 * 解决上述错误的方法是修改子任务递归数量，超出JVM内存
 */
public class CountTask extends RecursiveTask<Long> {

    private static final int THRESHOLD = 10000;

    private long start;

    private long end;
    
    public CountTask(long start,long end) {
        this.start = start;
        this.end = end;
    }
    
    public Long compute() {
        long sum = 0;
        boolean canCompute = (end - start) < THRESHOLD;
        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 分成100个小任务
            long step = (start + end) / 100;
            ArrayList<CountTask> subTasks = new ArrayList<>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = pos + step;
                if (lastOne > end)
                    lastOne = end;
                CountTask subTask = new CountTask(pos, lastOne);
                pos += step + 1;
                subTasks.add(subTask);
                subTask.fork();
            }
            for (CountTask  t : subTasks) {
                sum += t.join();
            }
        }
        return sum;
    }
    
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(0, 200000L);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        try {
            long sum = result.get();
            System.out.println("sum = " + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}