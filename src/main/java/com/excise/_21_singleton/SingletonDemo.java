package com.excise._21_singleton;

public class SingletonDemo {

	public static void main(String[] args) {
		// 调用Singleton的其他静态成员时候，会创建对象
		System.out.println(Singleton.STATUS);

		// 静态内部类和非静态内部类一样，都是在被调用时才会被加载
		System.out.println(StaticSingleton.STATUS);
	}

}
 