package com.excise.vol;

/**
 * volatile对于保证操作的原子性是非常有帮助的，但是不能代替锁，它无法保证复合操作的原子性（本例子只有一个复制操作）
 * 。见类PlusV
 */
public class MultiThreadVolatileLong {
    public static volatile long t=0;
    public static class ChangeT implements Runnable{
        private long to;
        public ChangeT(long to){
            this.to=to;
        }
        @Override
        public void run() {
            while(true){
                MultiThreadVolatileLong.t=to;
                Thread.yield();
            }
        }
    }
    public static class ReadT implements Runnable{
        @Override
        public void run() {
            while(true){
                long tmp=MultiThreadVolatileLong.t;
                if(tmp!=111L && tmp!=-999L && tmp!=333L && tmp!=-444L)
                    System.out.println(tmp);
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ChangeT(111L)).start();
        new Thread(new ChangeT(-999L)).start();
        new Thread(new ChangeT(333L)).start();
        new Thread(new ChangeT(-444L)).start();
        new Thread(new ReadT()).start();
    }
}
