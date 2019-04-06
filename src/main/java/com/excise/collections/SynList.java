package com.excise.collections;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SynList {
	public static List<String> l=Collections.synchronizedList(new LinkedList<String>());
//    public static List<String> l=Collections.synchronizedList(new ArrayList<String>());
//    public static List<String> l=new Vector();
    //适合读多写少的场景，读读完全没有加锁，写入也不会阻塞读操作，只有写入和写入之间需要等待。
    //没有使用读写锁，而是只是在写操作加锁，写的过程中复制原数据到新的副本写入后在更新即可，这样便不影响读
//    public static List<String> l=new CopyOnWriteArrayList<String>();
    
    public static void main(String[] args) {
        l.add("hello");
    }

}
