package com.excise._33_map;

import java.util.concurrent.ConcurrentHashMap;

/**
 * search方法会再Map中找到第一个使得Function返回不为null的值
 * 本示例返回第一个能够被2整除的数，由于hash算法的随机性和并行的随机性，因此得到的结果是随机的
 */
public class SearchDemo {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= 100; i++) {
            map.put(Integer.toString(i), i);
        }
        // 第一个参数，需要此操作是并行执行的元素个数
        int found = map.search(3, (str, i) -> {
            if (i % 2 == 0) {
                return i;
            }
            return null;
        });
        System.out.println(found);
    }

}
