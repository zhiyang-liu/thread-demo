package com.excise.singleton;

/**
 * 懒汉式单例，可以保证在第一次调用getInstance才创建对象
 * 但是为了保证安全性，需要在方法加synchronized
 */
public class LazySingleton {
	private LazySingleton() {
		System.out.println("LazySingleton is create"); 
	}
	private static LazySingleton instance = null;
	public static synchronized LazySingleton getInstance() {
		if (instance == null)
			instance = new LazySingleton();
		return instance;
	}
}
