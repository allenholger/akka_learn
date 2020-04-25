package com.example.chapter05;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TaskRouteeActor extends UntypedAbstractActor {

    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("self: " + getSelf() + "-----> message: " + message + "------> parent: " + getContext().getParent());
    }
}
