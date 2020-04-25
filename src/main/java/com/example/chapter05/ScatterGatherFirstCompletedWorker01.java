package com.example.chapter05;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ScatterGatherFirstCompletedWorker01 extends UntypedAbstractActor {
    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);
    @Override
    public void onReceive(Object message) throws Throwable {
        log.info(getSelf() + "收到的消息：" + message);
        Thread.sleep(10000);
        getSender().tell("worker01", getSelf());
    }
}
