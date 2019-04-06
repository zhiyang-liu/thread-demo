package com.excise.flow;

/*import java.util.Arrays;
import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;*/

/**
 * java9提供的发布订阅模式的api
 * subscription.request(1);请求一个数据，在构造函数和next函数有，注册订阅者就会调用回去一个数据，当有可用数据就会调用onnext
 */
/*
public class FlowDemo
{
   public static void main(String[] args)
   {
      // Create a publisher.

      SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
  
      // Create a subscriber and register it with the publisher.

      MySubscriber<String> subscriber = new MySubscriber<>();
      MySubscriber<String> subscriber2 = new MySubscriber<>();
      publisher.subscribe(subscriber);
      publisher.subscribe(subscriber2);

      // Publish several data items and then close the publisher.

      System.out.println("Publishing data items...");
      String[] items = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
              "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
      Arrays.asList(items).stream().forEach(i ->{
    	  publisher.submit(i);
    	  System.out.println(Thread.currentThread().getName()+" publish "+i);
      });
      publisher.close();

      //当订阅完成以后会唤醒主线程，这里是为了等待订阅处理完成
      try
      {
         synchronized("A")
         {
            "A".wait();
         }
      }
      catch (InterruptedException ie)
      {
      }
   }
}

class MySubscriber<T> implements Subscriber<T>
{
   private Subscription subscription;

   @Override
   public void onSubscribe(Subscription subscription)
   {
      this.subscription = subscription;
      subscription.request(1);
      System.out.println(Thread.currentThread().getName()+" onSubscribe");
   }

   @Override
   public void onNext(T item)
   {
      System.out.println(Thread.currentThread().getName()+" Received: " + item);
      subscription.request(1);
   }

   @Override
   public void onError(Throwable t)
   {
      t.printStackTrace();
      synchronized("A")
      {
         "A".notifyAll();
      }
   }

   @Override
   public void onComplete()
   {
      System.out.println("Done");
      synchronized("A")
      {
         "A".notifyAll();
      }
   }
}*/
