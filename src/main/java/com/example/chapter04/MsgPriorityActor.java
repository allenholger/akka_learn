package com.example.chapter04;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class MsgPriorityActor extends UntypedAbstractActor {

    @Override
    public void onReceive(Object message) throws Throwable {
        System.out.println(message);
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(Props.create(MsgPriorityActor.class).withMailbox("msgprio-mailbox"), "msgPriorityActor");
        String[] messages = {"王五", "赵六", "张三", "李四"};
        for(int i = 0; i < messages.length; i++){
            actorRef.tell(messages[i], ActorRef.noSender());
        }
    }
}
