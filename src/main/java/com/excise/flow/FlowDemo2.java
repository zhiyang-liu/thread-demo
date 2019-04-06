package com.excise.flow;
/*import java.util.Arrays;
import java.util.concurrent.SubmissionPublisher;*/

/**
 * 发布订阅模式还可以通过数据处理链对数据进行流式处理，这里是一个泛化的数据转换模块
 * 对数据流中的数据进行两种不同的处理，一种转为大写，一种转为小写
 */
/*public class FlowDemo2
{
   public static void main(String[] args)
   {
      // Create a publisher.

      SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
  
      // Create a subscriber and register it with the publisher.

      MySubscriber<String> subscriber = new MySubscriber<>();
      MySubscriber<String> subscriber2 = new MySubscriber<>();
      
      TransformProcessor<String,String> toUpperCase = new TransformProcessor<>(String::toUpperCase);
      TransformProcessor<String,String> toLowverCase = new TransformProcessor<>(String::toLowerCase);
      
      publisher.subscribe(toUpperCase);
      publisher.subscribe(toLowverCase);
      
      toUpperCase.subscribe(subscriber);
      toLowverCase.subscribe(subscriber2);
      


      // Publish several data items and then close the publisher.

      System.out.println("Publishing data items...");
      String[] items = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                         "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
      Arrays.asList(items).stream().forEach(i ->{
    	  publisher.submit(i);
    	  System.out.println(Thread.currentThread().getName()+" publish "+i);
      });
      publisher.close();

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
}*/

