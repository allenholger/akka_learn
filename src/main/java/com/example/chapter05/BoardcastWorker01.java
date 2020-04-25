package com.example.chapter05;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class BoardcastWorker01 extends UntypedAbstractActor {

    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info(getSelf() + "------------>" + message);
    }
}
