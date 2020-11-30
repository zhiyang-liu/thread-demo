package com.excise._33_map;

import java.util.concurrent.ConcurrentHashMap;

/**
 * java8对ConcurrentHashMap的增强，引入reduce函数，支持lambda表达式
 * reduce操作对数据处理的同时，会将其转换成另外一种形式
 */
public class ReduceDemo {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= 100; i++) {
            map.put(Integer.toString(i), i);
        }
        // 第一个参数为并行度，一个并行任务可以处理的元素个数，设置LongMax将禁止并行
        int count = map.reduceValues(2, (i, j) -> i + j);
        System.out.println(count);
    }

}
