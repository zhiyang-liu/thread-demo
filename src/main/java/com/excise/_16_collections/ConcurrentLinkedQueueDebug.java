
package com.excise._16_collections;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ConcurrentLinkedQueue可以算是在高并发环境中性能最好的队列了
 * 整个算法没有任何锁
 */
public class ConcurrentLinkedQueueDebug {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("1");
        q.poll();
        q.add("3");
    }

}
