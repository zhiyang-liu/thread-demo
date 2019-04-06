package com.excise.future;

import java.util.concurrent.*;

/**
 * 设置线程超时的方法，thread.join 和 future.get  都是支持设置超时时间的，但是他们都是阻塞的，如果线程超时报异常
 * 将会阻止后面的线程返回（必须捕获超时异常处理才不影响后续）
 *
 * pool.awaitTermination
 * 当前线程阻塞，直到
 * 等所有已提交的任务（包括正在跑的和队列中等待的）执行完
 * 或者等超时时间到
 * 或者线程被中断，抛出InterruptedException
 * 然后返回true（shutdown请求后所有任务执行完毕）或false（已超时）
 *
 * 以上方法均会抛出超时异常，可以捕获异常，然后处理成默认值返回即可
 */
public class FutureMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //构造FutureTask
        FutureTask<String> future = new FutureTask<String>(new RealData("a"));
        ExecutorService executor = Executors.newFixedThreadPool(1);
        //执行FutureTask，相当于上例中的 client.request("a") 发送请求
        //在这里开启线程进行RealData的call()执行
        executor.submit(future);

       /* System.out.println("请求完毕");
        try {
        //这里依然可以做额外的数据操作，这里使用sleep代替其他业务逻辑的处理
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }*/
        //相当于上例中得data.getContent()，取得call()方法的返回值
        //如果此时call()方法没有执行完成，则依然会等待
        try {
            System.out.println("数据 = " + future.get(1,TimeUnit.MILLISECONDS));//get方法可以指定超时
        } catch (TimeoutException e) {
            System.out.println("超时异常");
        }
        System.out.println("dfasdsf");
    }
}
