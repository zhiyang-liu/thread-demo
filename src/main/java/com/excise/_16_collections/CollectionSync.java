package com.excise._16_collections;

import java.util.*;

public class CollectionSync implements Runnable {

	static List<Integer> list = null;

	public void run() {
		for (int i = 0; i < 1000; i++) {
			list.add(i);
		}
	}

	public static void main(String[] args) {
		list = Collections.synchronizedList(new ArrayList<>());
//		list = new Vector<Integer>();
		CollectionSync cs = new CollectionSync();
		Thread t1 = new Thread(cs);
		Thread t2 = new Thread(cs);
		t1.start();
		t2.start();
    }

}
