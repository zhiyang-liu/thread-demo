package com.excise.completable;

import java.util.concurrent.CompletableFuture;

/**
 * 脱离线程池的使用，仅作为一个契约
 * 开启一个线程，等待主线程传入参数时候，才继续执行线程
 */
public class CFutureMain1 {
    public static class AskThread implements Runnable {
        CompletableFuture<Integer> re = null;

        public AskThread(CompletableFuture<Integer> re) {
            this.re = re;
        }

        @Override
        public void run() {
            int myRe = 0;
            try {
                myRe = re.get() * re.get();//执行到这里将会阻塞，等待CompletableFuture通知才会继续执行
            } catch (Exception e) {
            }
            System.out.println(myRe);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(new AskThread(future)).start();
        // 模拟长时间其他调用
        Thread.sleep(1000);
        // 告知完成结果
        future.complete(60);
    }
}
