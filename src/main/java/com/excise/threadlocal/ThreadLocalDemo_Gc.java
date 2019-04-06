
package com.excise.threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个本地的变量在线程退出后会进行清理，
 * 如果使用线程池意味着这个线程不一定会退出，最好在不用时ThreadLocal.remove
 *
 * 本例子跟踪ThreadLocal对象，以及内部的SimpleDateFormat对象的垃圾回收，通过重写 finalize方法
 * SimpleDateFormat只会新建十次，因为线程池是固定数量10，代表着同时有10个任务在执行，无论怎么竞争，他们都是使用自己的本地变量SimpleDateFormat
 * 因此不会有安全问题（使用线程池实际上并没有创建1000个SimpleDateFormat，但是依然能够保证安全）
 */
public class ThreadLocalDemo_Gc {
    static volatile ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>() {
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + " is gc");
        }
    };
    static volatile CountDownLatch cd = new CountDownLatch(10000);
    public static class ParseDate implements Runnable {
        int i = 0;
        public ParseDate(int i) {
            this.i = i;
        }
        public void run() {
            try {
                if (tl.get() == null) {
                    tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString() + " is gc");
                        }
                    });
                    System.out.println(Thread.currentThread().getId() + ":create SimpleDateFormat");
                }
                Date t = tl.get().parse("2015-03-29 19:29:" + i % 60);
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                cd.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        cd.await();
        System.out.println("mission complete!!");
        tl = null; //将触发一次回收tl
        System.gc();//)用于调用垃圾收集器，在调用时，垃圾收集器将运行以回收未使用的内存空间。它将尝试释放被丢弃对象占用的内存。
        System.out.println("first GC complete!!");
        //在设置ThreadLocal的时候，会清除ThreadLocalMap中的无效对象（JDK7是这样的，JDK8并没有）
        tl = new ThreadLocal<SimpleDateFormat>();
        cd = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        cd.await();
        
        System.gc();
        System.out.println("second GC complete!!");

    }
}
