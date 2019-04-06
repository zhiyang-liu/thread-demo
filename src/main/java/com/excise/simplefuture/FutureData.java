package com.excise.simplefuture;

/**
 * FutureData，它是用来提取RealData的
 * 当主线程调用getResult()时候，如果没有准备好，则主线程会等待
 * 当开启的线程执行结束后会修改状态，然后再唤醒主线程
 */
public class FutureData implements Data {
    protected RealData realdata = null;
    protected boolean isReady = false;
    //synchronized修饰方法时候，所对象就是调用方法的对象
    public synchronized void setRealData(RealData realdata) {
        if (isReady) {                        
            return;     
        }
        this.realdata = realdata;
        isReady = true;
        notifyAll();
    }
    public synchronized String getResult() {
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        return realdata.result;
    }
}
