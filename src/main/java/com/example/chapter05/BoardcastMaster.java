package com.example.chapter05;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.BroadcastGroup;

import javax.tools.JavaCompiler;

import java.util.ArrayList;
import java.util.List;

import scala.collection.JavaConverters;

public class BoardcastMaster extends UntypedAbstractActor {
    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    private ActorRef router;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        ActorRef boardcastWorker01 = getContext().actorOf(Props.create(BoardcastWorker01.class), "worker01");
        ActorRef boardcastWorker02 = getContext().actorOf(Props.create(BoardcastWorker02.class), "worker02");
        List<String> routeePaths = new ArrayList<>();
        routeePaths.add(boardcastWorker01.path().toStringWithoutAddress());
        routeePaths.add(boardcastWorker02.path().toStringWithoutAddress());
        router = getContext().actorOf(new BroadcastGroup(routeePaths).props(), "boradcast");
    }

    @Override
    public void onReceive(Object message) throws Throwable, Throwable {
        router.tell(message, getSelf());
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(Props.create(BoardcastMaster.class), "boardcastMaster");
        actorRef.tell("测试", ActorRef.noSender());
    }
}
