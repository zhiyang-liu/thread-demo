package com.excise._24_future;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

/**
 * guava对Future模式的支持
 * JDK自带的会阻塞主线程，这里不影响主线程的执行，而是当执行结束后再通知主线程
 */
public class FutrueDemo {

    public static void main(String args[]) throws InterruptedException {

        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        ListenableFuture<String> task = service.submit(new RealData("x"));

        task.addListener(() -> {
            System.out.print("异步处理成功:");
            try {
                System.out.println(task.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, MoreExecutors.directExecutor());

        System.out.println("main task done.....");
        Thread.sleep(3000);
    }

}
