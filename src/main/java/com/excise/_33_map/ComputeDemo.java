package com.excise._33_map;

import java.util.concurrent.ConcurrentHashMap;

/**
 * computeIfAbsent （线程安全的高效方法）
 * 条件插入，当元素不存在时候创建并插入吗，存在就直接获得，起到了对象的复用，大型重量级对象有很好的效果
 */
public class ComputeDemo {

    public static class HeavyObject {
        public HeavyObject() {
            System.out.println("HeavyObject created");
        }
    }

    public static void main(String[] args) {
        ConcurrentHashMap<String, HeavyObject> map = new ConcurrentHashMap<>();
        HeavyObject obj = getOrCreate(map, "1");
    }

    public static HeavyObject getOrCreate(ConcurrentHashMap<String, HeavyObject> map, String key) {
        return map.computeIfAbsent(key, k -> new HeavyObject());
    }

    /**
     * 普通实现
     */
    // public static HeavyObject getOrCreate(ConcurrentHashMap<String, HeavyObject>
    // map, String key) {
    // HeavyObject value = map.get(key);
    // if (value == null) {
    // value = new HeavyObject();
    // map.put(key, value);
    // }
    // return value;
    // }
}
