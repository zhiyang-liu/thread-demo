
package com.excise.atomic;

import java.util.Random;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * LongAccumulator是LongAdder的扩展（内部实现是一样的，都是将long型整数分割，并存储在不同的变量中，以防止多线程竞争）
 * 对于LongAdder来说只是每次对给定的整数进行一次加法，而LongAccumulator可以实现任意函数操作
 *
 * 本示例为通过多线程访问若干个整数，返回遇到最大的整数
 */
public class LongAccumulatorDemo {
    public static void main(String[] args) throws Exception {
        //第一个参数就是需要执行的二元函数，这里传入的是是一个比较函数，两者相比留下较大的
        //第二个参数是初始值
        LongAccumulator accumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);
        Thread[] ts = new Thread[1000];

        for (int i = 0; i < 1000; i++) {
            ts[i] = new Thread(() -> {
                Random random = new Random();
                long value = random.nextLong();
                accumulator.accumulate(value);
            });
            ts[i].start();
        }
        for (int i = 0; i < 1000; i++) {
            ts[i].join();
        }
        System.out.println(accumulator.longValue());
    }
}
