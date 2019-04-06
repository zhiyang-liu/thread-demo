package com.excise.stamped;

import java.util.concurrent.locks.StampedLock;

/**
 * 以前的读写锁是悲观的，读操作与写操作依然是冲突的
 * StampedLock是java8引入的，是一种乐观的读策略，这种乐观策略类似一种无锁操作，使得乐观锁完全不会阻塞写线程
 */
public class Point {
	private double x, y;
	private final StampedLock sl = new StampedLock();

	/**
	 * 写方法，用写锁
	 */
	void move(double deltaX, double deltaY) {
		long stamp = sl.writeLock();
		try {
			x += deltaX;
			y += deltaY;
		} finally {
			sl.unlockWrite(stamp);
		}
	}

	/**
	 * 只读方法
	 */
	double distanceFromOrigin() {
		long stamp = sl.tryOptimisticRead();//这个方法尝试获得一次乐观锁，会获得一个类似于时间戳的整数
		double currentX = x, currentY = y;
		if (!sl.validate(stamp)) {//判断这个时间戳是否在读的期间被修改过，如果没有修改过则跳到return。否则继续加锁读
			stamp = sl.readLock();//如果发生了篡改，则获得悲观锁重新读取
			try {
				currentX = x;
				currentY = y;
			} finally {
				sl.unlockRead(stamp);
			}
		}
		return Math.sqrt(currentX * currentX + currentY * currentY);
	}
}
