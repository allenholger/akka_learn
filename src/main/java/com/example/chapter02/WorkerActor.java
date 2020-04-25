package com.example.chapter02;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Kill;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WorkerActor extends UntypedAbstractActor {
    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    @Override
    public void postStop() throws Exception {
        log.info(this.getSelf().path().name() + "被停止了");
    }

    @Override
    public void preStart() throws Exception {
        log.info("workerActor start...");
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info(message.toString());
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(Props.create(WorkerActor.class), "workerActor");
        actorRef.tell("停止该actor", ActorRef.noSender());
        //actorSystem.stop(actorRef);
        //actorRef.tell(PoisonPill.getInstance(), ActorRef.noSender());
        actorRef.tell(Kill.getInstance(), ActorRef.noSender());
    }
}
