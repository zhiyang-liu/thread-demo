package com.excise._31_completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 完成普通future的工作
 * 
 * thenCompose组合多个CompletableFuture
 * 一个CompletableFuture执行结束后，将执行结果通过Function接口传递给下一个CompletionStage实例进行处理
 */
public class CFutureMain5 {
    
    public static Integer calc(Integer para) {
        return para/2;
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<Void> fu =
                CompletableFuture.supplyAsync(() -> calc(50))
                .thenCompose((i) -> CompletableFuture.supplyAsync(() -> calc(i)))
                .thenApply((str)->"\"" + str + "\"").thenAccept(System.out::println);
        fu.get();
    }

}
