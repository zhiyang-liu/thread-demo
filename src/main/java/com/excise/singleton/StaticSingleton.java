package com.excise.singleton;

/**
 * 将需要单例的类StaticSingleton，在静态内部类中创建，这样既不用加锁，也能保证在第一次调用 getInstance才创建对象
 * 静态内部类和非静态内部类一样，都是在被调用时才会被加载
 */
public class StaticSingleton {
	public static int STATUS = 1;
	private StaticSingleton(){
		System.out.println("StaticSingleton is create");
	}
	private static class SingletonHolder {
		private static StaticSingleton instance = new StaticSingleton();
	}
	public static StaticSingleton getInstance() {
		return SingletonHolder.instance;
	}
}

