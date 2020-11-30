package com.excise._34_trace;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 前面的解决办法只能定位在哪里发生了错误，但是无法知道这个任务在哪提交的
 * 通过自定义扩展线程池来处理异常
 */
public class TraceMain {

	public static void main(String[] args) {
		// 使用扩展的线程池
		ThreadPoolExecutor pools = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS, new SynchronousQueue<>());
		for (int i = 0; i < 5; i++) {
			pools.submit(new DivTask(100, i));
		}
	}

}
