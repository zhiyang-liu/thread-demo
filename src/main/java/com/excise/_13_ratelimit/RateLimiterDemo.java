package com.excise._13_ratelimit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * guava是谷歌下的一个核心库，提供了一大批设计精良、使用方便的工具类（可以认为是JDK标准库的重要补充）
 * RateLimiter是guava下的一款限流工具（使用的令牌桶算法）
 */
public class RateLimiterDemo {

    static RateLimiter limiter = RateLimiter.create(2);

    public static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis());
        }

    }

    public static void main(String args[]) {
        for (int i = 0; i < 50; i++) {
            limiter.acquire();
            new Thread(new Task()).start();
        }
    }

}
