package com.excise.deadlock;


/**
 * 模拟一个死锁现象
 * 解决死锁问题：通过重入锁的中断或者限时等待
 * 两种避免死锁的方法：
 * ReentrantLock支持中断在等待中的线程（synchronized不支持）见IntLock示例
 * trylock方法可以设置超时时间，当等待时间超过限制便会中断 见TimeLock示例
 */
public class DeadLock extends Thread {
  protected Object tool;
  static Object fork1 = new Object();
  static Object fork2 = new Object();

  public DeadLock(Object obj) {
    this.tool = obj;
    if (tool == fork1) {
      this.setName("哲学家A");
    }
    if (tool == fork2) {
      this.setName("哲学家B");
    }
  }

  @Override
  public void run() {
    if (tool == fork1) {
      synchronized (fork1) {
        try {
          Thread.sleep(500);
        } catch (Exception e) {
          e.printStackTrace();
        }
        synchronized (fork2) {
          System.out.println("哲学家A开始吃饭了");
        }
      }

    }
    if (tool == fork2) {
      synchronized (fork2) {
        try {
          Thread.sleep(500);
        } catch (Exception e) {
          e.printStackTrace();
        }
        synchronized (fork1) {
          System.out.println("哲学家B开始吃饭了");
        }
      }

    }
  }

  public static void main(String[] args) throws InterruptedException {
    DeadLock 哲学家A = new DeadLock(fork1);
    DeadLock 哲学家B = new DeadLock(fork2);
    哲学家A.start();
    哲学家B.start();
    Thread.sleep(1000);
  }
}
