package com.excise._34_trace;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * submit吃掉异常
 * 改用execute，可以捕获异常，其他四种正常的也可以正常输出执行
 */
public class CatchExceptionMain2 {

	public static void main(String[] args) {
		ThreadPoolExecutor pools = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
				0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		for (int i = 0; i < 5; i++) {
			pools.execute(new DivTask(100, i));
		}
	}
}
