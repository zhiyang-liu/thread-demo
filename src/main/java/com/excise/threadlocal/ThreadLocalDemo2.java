package com.excise.threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 另一种可行的方案就是使用ThreadLocal
 * 为每一个线程分配不同的对象，需要在应用层面保证ThreadLocal只起到了简单的容器作用
 */
public class ThreadLocalDemo2 {
	static ThreadLocal<SimpleDateFormat> tl=new ThreadLocal<SimpleDateFormat>();
    public static class ParseDate implements Runnable{
    	int i=0;
    	public ParseDate(int i){this.i=i;}
		public void run() {
			try {
				if(tl.get()==null){
					tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
				}
				Date t=tl.get().parse("2015-03-29 19:29:"+i%60);
				System.out.println(i+":"+t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
    }
	public static void main(String[] args) {
		ExecutorService es=Executors.newFixedThreadPool(10);
		for(int i=0;i<1000;i++){
			es.execute(new ParseDate(i));
		}
		
	}

}
