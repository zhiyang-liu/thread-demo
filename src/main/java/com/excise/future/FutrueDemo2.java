package com.excise.future;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Executors;

/**
 * guava还支持对异常的处理，当触发异常时候如何处理可以定义，这里也是不影响主线程执行，是线程执行结束再通知main
 */
public class FutrueDemo2 {
	public static void main(String args[]) throws InterruptedException {
		ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
		ListenableFuture<String> task = service.submit(new RealData("x"));

		Futures.addCallback(task, new FutureCallback<String>() {
			public void onSuccess(String o) {
				System.out.println("异步处理成功,result=" + o);
			}

			public void onFailure(Throwable throwable) {
				System.out.println("异步处理失败,e=" + throwable);
			}
		}, MoreExecutors.newDirectExecutorService());

		System.out.println("main task done.....");
		Thread.sleep(3000);
	}
}
