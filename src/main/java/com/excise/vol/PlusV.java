package com.excise.vol;

/**
 * volatile无法代替锁，它无法保证复合操作的原子性  (结果将小于1000000)
 */
public class PlusV {
    static volatile int i=0;
    public static class PlusTask implements Runnable{
        @Override
        public void run() {
            for(int k=0;k<10000;k++)
                i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads=new Thread[10];
        for(int i=0;i<10;i++){
            threads[i]=new Thread(new PlusTask());
            threads[i].start();
        }
        for(int i=0;i<10;i++){
            threads[i].join();
        }

        System.out.println(i);
    }
}
