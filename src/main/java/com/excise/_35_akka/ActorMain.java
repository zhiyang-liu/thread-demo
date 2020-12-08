package com.excise._35_akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.BalancingPool;

/**
 * Actors模型对并发模型进行了更高的抽象，它是一种异步、非阻塞、高性能的轻量级事件驱动编程模型。
 * 所谓轻量级事件指的是这些acotr内存消耗极小，1GB内存可容纳百万级别个Actor。Actors模型适用于可以用于高并发、分布式场景。
 *
 * Created by liuzhiyang on 2020/12/7 下午8:34
 */
public class ActorMain {

    public static void main(String[] args) {
        // 生成角色系统
        ActorSystem system = ActorSystem.create("msgSystem");

        // 生成角色 ProduceMsgActor
        ActorRef produceMsgActor = system.actorOf(new BalancingPool(3).props(Props.create(ProduceMsgActor.class)),"ProduceMsgActor");
        // 生成角色 DisposeMsgActor
        ActorRef disposeMsgActor = system.actorOf(new BalancingPool(2).props(Props.create(DisposeMsgActor.class)),"DisposeMsgActor");

        // 给produceMsgActor发消息请求
        produceMsgActor.tell("please produce msg1", ActorRef.noSender());

    }

    //定义角色 ProduceMsgActor  产生消息
    static class ProduceMsgActor extends AbstractActor {

        @Override
        public Receive createReceive() {
            return receiveBuilder().match(String.class, t -> {
                // 收到消息
                System.out.println(self() + "  receive msg  from " + sender() + ": " + t);

                // 响应消息请求
                Msg msg = new Msg("haha");
                System.out.println(self() + "  produce msg: " + msg.getContent());

                // 根据路径查找下一个处理者
                ActorSelection nextDisposeRefs = getContext().actorSelection("/user/DisposeMsgActor");

                // 将消息发给下一个处理者DisposeMsgActor
                nextDisposeRefs.tell(msg, self());
            }).matchAny(t -> {
                System.out.println("no disposer");
            }).build();
        }

    }

    // 定义角色 DisposeMsgActor 消费消息
    static class DisposeMsgActor extends AbstractActor {

        @Override
        public Receive createReceive() {
            return receiveBuilder().match(Msg.class, t -> {
                // 收到消息
                System.out.println(self() + "  receive msg  from " + sender() + ": " + t.getContent());
                System.out.println(self() + " dispose msg : " + t.getContent());
            }).matchAny(t -> {
                System.out.println("no disposer");
            }).build();
        }

    }

    // 定义消息
    static class Msg {

        private String content = "apple";

        public Msg(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

}
