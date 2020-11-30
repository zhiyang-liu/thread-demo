package com.excise._16_collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 多个线程写hashmap，可能的结果：
 * 1：正常结束，结果符合预期
 * 2：正常结束，不符合预期，小于预期容量
 * 3：程序永远无法结束，成环，1.7这样，1.8已解决
 */
public class SynHashMap {

//    public static Map m = Collections.synchronizedMap(new HashMap());

    public static Map m = new ConcurrentHashMap();
    
    public static void main(String[] args) {
        m.put("hello", "world");
    }

}
