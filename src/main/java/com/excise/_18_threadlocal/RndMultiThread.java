
package com.excise._18_threadlocal;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 为每一个线程分配一个独立的对象也许对性能是有帮助的，这也不一定
 * 如果共享对象对于竞争的处理容易引起性能损失，就应该考虑使用ThreadLocal
 * Random共享实例虽然是线程安全的，但会因为竞争同一seed导致性能下降，所以不如使用ThreadLocal
 * 本例子是产生随机数，一种是使用ThreadLocal为每一个线程分配独立的对象，一种是共用一个对象
 *
 * 另一种替换方案（）JDK7后引入的ThreadLocalRandom  使用ThreadLocal的原理，即不用我们自己实现了
 * 多个线程同时计算随机数计算新的种子时候多个线程会竞争同一个原子变量的更新操作，
 * 由于原子变量的更新是CAS操作，同时只有一个线程会成功，所以会造成大量线程进行自旋重试，
 * 这是会降低并发性能的，所以ThreadLocalRandom应运而生。
 */
public class RndMultiThread {

    public static final int GEN_COUNT = 10000000;

    public static final int THREAD_COUNT = 4;

    static ExecutorService exe = Executors.newFixedThreadPool(THREAD_COUNT);

    public static Random rnd = new Random(123);

    public static ThreadLocal<Random> tRnd = new ThreadLocal<Random>() {

        @Override
        protected Random initialValue() {
            return new Random(123);
        }

    };

    public static class RndTask implements Callable<Long> {

        private int mode = 0;

        public RndTask(int mode) {
            this.mode = mode;
        }

        public Random getRandom() {
            if (mode == 0) {
                return rnd;
            } else if (mode == 1) {
                return tRnd.get();
            } else {
                return null;
            }
        }

        @Override
        public Long call() {
            long b = System.currentTimeMillis();
            for (long i = 0; i < GEN_COUNT; i++) {
                getRandom().nextInt();
            }
            long e = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " spend " + (e - b) + "ms");
            return e - b;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Future<Long>[] futs = new Future[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            futs[i] = exe.submit(new RndTask(0));
        }
        long totaltime = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            totaltime += futs[i].get();
        }
        System.out.println("多线程访问同一个Random实例:" + totaltime + "ms");

        //ThreadLocal的情况
        for (int i = 0; i < THREAD_COUNT; i++) {
            futs[i] = exe.submit(new RndTask(1));
        }
        totaltime = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            totaltime += futs[i].get();
        }
        System.out.println("使用ThreadLocal包装Random实例:" + totaltime + "ms");
        exe.shutdown();
    }

}
