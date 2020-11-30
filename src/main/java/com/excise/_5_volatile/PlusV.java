package com.excise._5_volatile;

/**
 * volatile无法代替锁，它无法保证复合操作的原子性  (结果将小于100000)
 * 符合操作，即先获取，然后再修改，在获取和修改之间无法保证原子性
 */
public class PlusV {

    static volatile int i = 0;

    public static class PlusTask implements Runnable {

        @Override
        public void run() {
            for (int k = 0; k < 10000; k++)
                i++;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new PlusTask());
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }

        System.out.println(i);
    }

}
