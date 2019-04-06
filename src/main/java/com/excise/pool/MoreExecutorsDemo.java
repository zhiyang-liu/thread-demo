package com.excise.pool;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executor;

/**
 * MoreExecutors是Guava提供的扩展线程池工具类
 * directExecutor()的作用可以让Runnable的run方法可以顺序执行，因为有时候run内方法需要异步与同步同时存在
 * 本示例将永远先输出"I am running in main
 */
public class MoreExecutorsDemo {
    public static void main(String[] args) {
        Executor exceutor = MoreExecutors.directExecutor();
        exceutor.execute(() ->
        {
            System.out.println("I am running in " + Thread.currentThread().getName());
        });
        System.out.println("this is a main thread");
    }
}
