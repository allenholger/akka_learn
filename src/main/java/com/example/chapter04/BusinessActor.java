package com.example.chapter04;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.dispatch.RequiresMessageQueue;

public class BusinessActor extends UntypedAbstractActor{
    @Override
    public void onReceive(Object message) throws Throwable {
        System.out.println(message);
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(Props.create(BusinessActor.class).withMailbox("business-mailbox"), "businessActor");
        actorRef.tell("hello", ActorRef.noSender());
    }
}
