package com.excise._18_threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  ThreadLocal理解的例子：锁是控制资源的方法，就好像让100个人填写一张表，只有一支笔，大家得挨个填写
 *  从另一个角度，可以每个人发一支笔，这样就不会发生争抢
 *
 *  本例子用SimpleDateFormat来解析日期，由于它不是线程安全的，很可能有异常
 *  一种可行的方案是在sdf.parse前后加锁
 */
public class ThreadLocalDemo1 {

	private static final  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class ParseDate implements Runnable {

    	int i = 0;

    	public ParseDate(int i) {this.i = i;}

		public void run() {
			try {
				Date t = sdf.parse("2015-03-29 19:29:" +  i % 60);
				System.out.println(i + ":" + t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
    }

	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 1000; i++) {
			es.execute(new ParseDate(i));
		}
	}

}
