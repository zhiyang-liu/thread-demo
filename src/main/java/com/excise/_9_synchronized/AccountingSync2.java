package com.excise._9_synchronized;

/**
 * 方法2 在方法上synchronized，注意方法是否是静态，锁将不同
 * 加锁之后，将保证结果为20000000，不会出现小于情况
 */
public class AccountingSync2 implements Runnable {

    static AccountingSync2 instance = new AccountingSync2();

    static int i = 0;

    public synchronized void increase() {
        // 当方法是非静态，且传入的是Accounting的不同引用，将无法保证安全，
        // 要么将方法修改为static，要么t1 t2传入同一个引用instance; 见AccountingSyncBad
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }

}

