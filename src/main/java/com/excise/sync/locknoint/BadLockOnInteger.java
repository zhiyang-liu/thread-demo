package com.excise.sync.locknoint;

/**
 * 以i作为锁对象，可能会引发问题，i++本质上是创建一个Integer对象，当i是位于-128到127时返回的同一个对象，
 * 当超出范围便会新建对象，所以锁对象不能保证唯一
 * 解决办法：不要使用i作为锁对象，新添加一个锁对象即可
 */
public class BadLockOnInteger implements Runnable{
    public static Integer i=0;
    static BadLockOnInteger instance=new BadLockOnInteger();
    @Override
    public void run() {
        for(int j=0;j<10000000;j++){
            synchronized(i){
//            synchronized(instance){
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(instance);
        Thread t2=new Thread(instance);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
