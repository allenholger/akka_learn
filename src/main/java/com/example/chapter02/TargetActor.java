package com.example.chapter02;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TargetActor extends UntypedAbstractActor {
    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("消息的发送者：" + getSender());
        getSender().tell("接收到来自" + getSelf() + "的消息:" + message.toString(), getSelf());
    }
}
