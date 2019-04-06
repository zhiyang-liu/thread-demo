package com.excise.pool;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * MoreExecutors提供了将普通线程池转化为守护线程池的方法
 * 本示例执行结束将推出，有与守护线程的关系
 */
public class MoreExecutorsDemo2 {
    public static void main(String[] args) {
        ThreadPoolExecutor exceutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
        MoreExecutors.getExitingExecutorService(exceutor);
        exceutor.execute(() -> System.out.println("I am running in " + Thread.currentThread().getName()));
    }
}
