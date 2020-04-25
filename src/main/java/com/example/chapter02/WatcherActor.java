package com.example.chapter02;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Kill;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WatcherActor extends UntypedAbstractActor {

    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    private ActorRef child = null;
    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            if(message.equals("stopChild")){
                getContext().stop(child);
            }
        }else if(message instanceof Terminated){
            Terminated t = (Terminated)message;
            log.info("监控到 " + t.getActor() + "停止了");
        } else{
            unhandled(message);
        }
    }

    @Override
    public void preStart() throws Exception {
        log.info("watcherActor start...");
        child = this.getContext().actorOf(Props.create(WorkerActor.class), "workerActor");
        getContext().watch(child);
    }

    @Override
    public void postStop() throws Exception {
        log.info("watcherActor stop");
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(Props.create(WatcherActor.class), "watcherActor");
        //actorRef.tell(Kill.getInstance(), ActorRef.noSender());
        actorRef.tell("stopChild", ActorRef.noSender());
    }
}
