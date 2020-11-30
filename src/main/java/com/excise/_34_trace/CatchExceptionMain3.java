package com.excise._34_trace;

import java.util.concurrent.*;

/**
 * submit吃掉异常
 * 1. 用Future.get()获得异常
 * 2. try-catch
 * get会线程等待，因此当get 到错误后，后面的线程将无法执行，第一个就发生错误，因此后面无法输出结果
 */
public class CatchExceptionMain3 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThreadPoolExecutor pools = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
				0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		for (int i = 0; i < 5; i++) {
			Future re = pools.submit(new DivTask(100, i));
			re.get();
		}
	}

}
