package com.example.chapter05;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.dispatch.OnComplete;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.routing.ScatterGatherFirstCompletedGroup;
import akka.util.Timeout;
import scala.Function1;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.util.Try;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScatterGatherFirstCompletedMaster extends UntypedAbstractActor {
    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    private ActorRef router;
    @Override
    public void preStart() throws Exception {
        super.preStart();
        ActorRef worker01 = getContext().actorOf(Props.create(ScatterGatherFirstCompletedWorker01.class), "worker01");
        ActorRef worker02 = getContext().actorOf(Props.create(ScatterGatherFirstCompletedWorker02.class), "worker02");
        List<String> routeePaths = new ArrayList<String>();
        routeePaths.add(worker01.path().toStringWithoutAddress());
        routeePaths.add(worker02.path().toStringWithoutAddress());
        router = getContext().actorOf(new ScatterGatherFirstCompletedGroup(routeePaths, Duration.create(3, TimeUnit.SECONDS)).props(), "router");
    }

    @Override
    public void onReceive(Object message) throws Throwable, Throwable {
        if(message.equals("helloA")){
            router.tell(message, getSelf());
        }else{
            Timeout timeout = new Timeout(Duration.create(10, TimeUnit.SECONDS));
            Future<Object> future = Patterns.ask(getSelf(), "helloA", timeout);
            future.onComplete(new OnComplete<Object>() {
                @Override
                public void onComplete(Throwable failure, Object success) throws Throwable, Throwable {
                    log.info("failure: " + failure);
                    log.info("result: " + success);
                }
            }, this.getContext().getDispatcher());
        }

    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(Props.create(ScatterGatherFirstCompletedMaster.class), "master");
        actorRef.tell("helloA", ActorRef.noSender());
    }
}
