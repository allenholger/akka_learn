package com.example.chapter05;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.tools.nsc.doc.html.HtmlTags;

public class ScatterGatherFirstCompletedWorker02 extends UntypedAbstractActor {
    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);
    @Override
    public void onReceive(Object message) throws Throwable {
        log.info(getSelf() + "收到的消息：" + message);
        Thread.sleep(2000);
        getSender().tell("worker02", getSelf());
    }
}
