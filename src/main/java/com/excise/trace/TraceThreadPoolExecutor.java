package com.excise.trace;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 扩展线程池
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
	public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	public void execute(Runnable task) {
		super.execute(wrap(task, clientTrace(), Thread.currentThread()
				.getName()));
	}

	@Override
	public Future<?> submit(Runnable task) {
		return super.submit(wrap(task, clientTrace(), Thread.currentThread()
				.getName()));
	}
	
	private Exception clientTrace() {
		return new Exception("Client stack trace");
	}

	private Runnable wrap(final Runnable task, final Exception clientStack,
			String clientThreadName) {
		return new Runnable() {
			@Override
			public void run() {
				/**
				 * 对我们传入的任务进行一次包装，使之能处理异常
				 */
				try {
					task.run();
				} catch (Exception e) {
					clientStack.printStackTrace();
					throw e;
				}
			}
		};
	}
}
