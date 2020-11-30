package com.excise._31_completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * orTimeout是JDK9使得CompletableFuture支持timeout功能
 * 本例子指定线程执行时间不能超过1s，否则将报TimeOutException异常
 */
public class CFutureMain7 {

    public static Integer calc(Integer para) {
        return para / 2;
    }

    public static void main(String[] args) {
//        CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {}
//            return calc(50);
//        }).orTimeout(1, TimeUnit.SECONDS).exceptionally(e -> {
//            System.err.println(e);
//            return 0;
//        }).thenAccept(System.out::println);
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {}
    }

}
