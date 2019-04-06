package com.excise.singleton;

/**
 * 常规单例，效率非常高，但是不能保证第一次调用getInstance才创建对象，因为可能在调用静态成员STATUS时候就创建了
 * 优点是不用加锁，多线程条件下效率较高
 */
public class Singleton {
	public static int STATUS=1;
	private Singleton(){
		System.out.println("Singleton is create");
	}
	private static Singleton instance = new Singleton();
	public static Singleton getInstance() {
		return instance;
	} 
}
