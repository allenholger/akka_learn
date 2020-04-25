package com.example.chapter02;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;

public class PropsActorDemo extends UntypedAbstractActor {
    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            log.info("接受到消息为：" + message.toString());
        }else{
            unhandled(message);
        }
    }

    public static Props createProps(){
        return Props.create(new Creator<PropsActorDemo>() {
            public PropsActorDemo create() throws Exception, Exception {
                return new PropsActorDemo();
            }
        });
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(PropsActorDemo.createProps(), "propsActorDemo");
        actorRef.tell("测试用例", ActorRef.noSender());
    }
}
