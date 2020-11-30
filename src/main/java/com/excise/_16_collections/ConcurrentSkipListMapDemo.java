
package com.excise._16_collections;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 基于跳表实现的Map：用来快速查找的数据结构，与hash不同的是元素是有序的，是线程安全的
 */
public class ConcurrentSkipListMapDemo {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new ConcurrentSkipListMap<>();
        for (int i = 0; i < 30; i++) {
            map.put(i,i);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

}
