package com.excise.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 完成普通future的工作
 *
 * 使用supplyAsync开启一个异步任务，接着连续使用流式调用对任务的处理结果进行加工
 * 以下几个函数可以执行（创建）一个CompletableFuture任务
 * thenApply 转换
 * thenAccept 最后处理
 */
public class CFutureMain3 {
    public static Integer calc(Integer para) {
    	try {
    		// 模拟一个长时间的执行
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
        return para*para;
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
    	CompletableFuture<Void> fu=CompletableFuture.supplyAsync(() -> calc(50))
          .thenApply((i)->Integer.toString(i))
          .thenApply((str)->"\""+str+"\"")
          .thenAccept(System.out::println);
        fu.get();//这里的get方法会等待calc函数执行完成
    }
}
