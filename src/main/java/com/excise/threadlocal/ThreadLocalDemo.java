package com.excise.threadlocal;

/**
 * ThreadLocal，很多地方叫做线程本地变量，。
 * 可ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。
 * 本示例永远不会输出，因为每个线程都是访问自己线程中的副本，不可能有安全问题
 * 在一定程度上也是解决了安全问题
 */
public class ThreadLocalDemo implements Runnable {
    public static class User{
        public int id;
        public String strId;
        public User(int id, String strId) {
            super();
            this.id = id;
            this.strId = strId;
        }
    }
    public static final ThreadLocal<User> localvar= new ThreadLocal<User>();
    public static User u=new User(1,"1");
    @Override
    public void run() {
//		localvar.set(u);
        localvar.set(new User(1,"1"));
        for(int i=0;i<50000;i++){
            localvar.get().id=i;
            localvar.get().strId=Integer.toString(i);
            if(localvar.get().id!=Integer.parseInt(localvar.get().strId)){
                System.out.println("ID:"+localvar.get().id+" strId:"+localvar.get().strId);
            }
        }
    }

    public static void main(String[] args) {
        ThreadLocalDemo r=new ThreadLocalDemo();
        Thread t1=new Thread(r);
        Thread t2=new Thread(r);
        t1.start();
        t2.start();
    }
}
