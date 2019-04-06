package com.excise.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 没有获得就放弃执行
 * 设置为2，即每秒产生两个令牌，每500毫秒产生一个，for循环的效率非常高，500毫秒已经执行结束了，所以结果只有一条输出
 */
public class RateLimiterDemo2 {
    static RateLimiter limiter = RateLimiter.create(2);

    public static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis());
        }
    }

    public static void main(String args[]) throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            if(!limiter.tryAcquire()) {
                continue;
            }
            new Thread(new Task()).start();
        }
    }
}
