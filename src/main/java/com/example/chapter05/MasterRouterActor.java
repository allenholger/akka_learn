package com.example.chapter05;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinPool;

import scala.sys.Prop;

public class MasterRouterActor extends UntypedAbstractActor {

    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    private ActorRef router;
    @Override
    public void preStart() throws Exception {
        super.preStart();
        router = getContext().actorOf(new RoundRobinPool(3).props(Props.create(TaskRouteeActor.class)), "taskRouteeActor");
        log.info("router: " + router);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        router.tell(message, getSender());
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(Props.create(MasterRouterActor.class), "masterRouterActor");
        for(int i = 0; i < 9; i++){
            actorRef.tell(i, ActorRef.noSender());
        }
    }
}
