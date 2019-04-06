package com.excise.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 完成普通future的工作
 * 
 * 以下几个函数可以执行（创建）一个CompletableFuture任务
 * static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier);
 * static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor);
 * static CompletableFuture<Void> runAsync(Runnable runnable);
 * static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor);
 *
 * supplyAsync用于有返回值的场景，runAsync用于没有返回值的场景
 * 两种方法都支持传入一个线程池的方法，如果不传入，则在系统默认的ForkJoinPool.common中执行
 * 这个默认线程池会将所有线程都设置为Daemon线程，意味着如果主线程退出，无论这些线程是否执行完毕都将会退出系统
 */
public class CFutureMain2 {
    public static Integer calc(Integer para) {
    	try {
    		// 模拟一个长时间的执行
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		System.out.println("执行中。。。");
        return para*para;
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        final CompletableFuture<Integer> future =
                CompletableFuture.supplyAsync(() -> calc(50));//开启一个新的线程执行
        System.out.println(future.get());//依然是阻塞执行
    }
}
