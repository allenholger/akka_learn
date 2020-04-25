package com.example.chapter02;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ActorDemo extends UntypedAbstractActor {

    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    @Override
    public void onReceive(Object message) throws Throwable, Throwable {
        if(message instanceof String){
            log.info("消息是字符串：" + message);
            log.info(String.valueOf(self().path().uid()));
        }else{
            unhandled(message);
        }
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(Props.create(ActorDemo.class), "actorDemo");
        actorRef.tell("测试消息！", ActorRef.noSender());
    }
}
