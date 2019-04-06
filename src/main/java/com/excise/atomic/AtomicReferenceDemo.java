package com.excise.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * money.compareAndSet(m, m+20)就是CAS操作
 * 存在的问题，如果我们想只进行一次当客户金额小于20元给免费充值一次20元（即现在小于20的，以后消费再小于20便不再赠送），现在是只要账户小于20就会自动充值
 * 可以使用AtomicStampedReference来解决
 * 产生的原因，在每次赠送20元时候，只会检查账户金额是否更新了，没有更新并且小于20就送，实际山我们只需要送一次，再小于20时候将不再赠送
 */

/**
 * CAS操作相比于锁机制有性能方面的好处，但是有时候会更新失败，即存在了安全问题（值与期望的不同）
 * 因此如果需要必须执行成功，则需要循环尝试
 */
public class AtomicReferenceDemo {
    static AtomicReference<Integer> money=new AtomicReference<Integer>();
    public static void main(String[] args) {
        money.set(19);
        //模拟多个线程同时更新后台数据库，为用户充值
        for(int i = 0 ; i < 3 ; i++) {              
            new Thread() {  
                public void run() {  
                    while(true){
                        while(true){
                            Integer m=money.get();
                            if(m<20){
                                if(money.compareAndSet(m, m+20)){
                                    System.out.println("余额小于20元，充值成功，余额:"+money.get()+"元");
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
        
        //用户消费线程，模拟消费行为，每次消费10元
        new Thread() {  
            public void run() {  
                for(int i=0;i<100;i++){
                    while(true){
                        Integer m=money.get();
                        if(m>10){
                            System.out.println("大于10元");
                            if(money.compareAndSet(m, m-10)){
                                System.out.println("成功消费10元，余额:"+money.get());
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
