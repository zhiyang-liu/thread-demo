package com.excise.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference在比较是否更新的问题上，除了值没有被更新外，还可以检查时间戳是否是期望的
 * 即在当前时间账户小于20，才进行赠送
 * 本质上就是AtomicReference只检查值是否更新的了，只其中可能该数值经过两次修改后改回了之前的值，那么就无法检测出这种情况
 * 只不过大多数情况这种情况也没有什么问题
 */
public class AtomicStampedReferenceDemo {
    static AtomicStampedReference<Integer> money=new AtomicStampedReference<Integer>(19,0);
    public static void main(String[] args) {
        //模拟多个线程同时更新后台数据库，为用户充值
        for(int i = 0 ; i < 3 ; i++) {
            final int timestamp=money.getStamp();//在循环之外获得
            new Thread() {  
                public void run() {  
                    while(true){
                        while(true){
                            Integer m=money.getReference();
                            if(m<20){
                                if(money.compareAndSet(m, m+20,timestamp,timestamp+1)){
                                    System.out.println("余额小于20元，充值成功，余额:"+money.getReference()+"元");
                                    break;
                                }
                            }else{
                                //System.out.println("余额大于20元，无需充值");
                                break ;
                            }
                        }
                    }
                }  
            }.start();
        }
        
        //用户消费线程，模拟消费行为
        new Thread() {  
            public void run() {  
                for(int i=0;i<100;i++){
                    while(true){
                        int timestamp=money.getStamp();
                        Integer m=money.getReference();
                        if(m>10){
                            System.out.println("大于10元");
                            if(money.compareAndSet(m, m-10,timestamp,timestamp+1)){
                                System.out.println("成功消费10元，余额:"+money.getReference());
                                break;
                            }
                        }else{
                            System.out.println("没有足够的金额");
                            break;
                        }
                    }
                    try {Thread.sleep(100);} catch (InterruptedException e) {}
                }
            }  
        }.start();  
    }
}
