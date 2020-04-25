package com.example.chapter02;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.Scheduler;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedAbstractActor;
import akka.japi.Function;
import akka.pattern.CircuitBreaker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import scala.Function0;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

public class CircuitBreakerActor extends UntypedAbstractActor {

    private ActorRef workerActor02 = null;

    private SupervisorStrategy strategy = new OneForOneStrategy(20, Duration.create(1, TimeUnit.MINUTES), new Function<Throwable, SupervisorStrategy.Directive>() {
        @Override
        public SupervisorStrategy.Directive apply(Throwable param) throws Exception, Exception {
            return (SupervisorStrategy.Directive) SupervisorStrategy.resume();
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        workerActor02 = getContext().actorOf(Props.create(WorkerActor02.class), "workerActor02");
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        workerActor02.tell(message, getSender());
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(Props.create(CircuitBreakerActor.class), "circuitBrokerActor");
    }
}

class WorkerActor02 extends UntypedAbstractActor{

    private CircuitBreaker circuitBreaker;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        this.circuitBreaker = new CircuitBreaker(getContext().getDispatcher(), getContext().getSystem().scheduler(),
                5, Duration.create(2, TimeUnit.SECONDS), Duration.create(1, TimeUnit.MINUTES))
//                .onOpen(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "----> Actor CircuritBroker 开启");
//                    }
//                })
                .onOpen(new Function0<BoxedUnit>() {
                    @Override
                    public BoxedUnit apply() {
                        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "----> Actor CircuritBroker 开启");
                        return null;
                    }
                })
                .onHalfOpen(new Function0<BoxedUnit>() {
                    @Override
                    public BoxedUnit apply() {
                        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "----> Actor CircuritBroker 半开启");
                        return null;
                    }
                })
                .onClose(new Function0<BoxedUnit>() {
                    @Override
                    public BoxedUnit apply() {
                        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "----> Actor CircuritBroker 关闭");
                        return null;
                    }
                });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            if(((String)message).startsWith("sync")){
                getSender().tell(circuitBreaker.callWithSyncCircuitBreaker(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        System.out.println("message: " + message);
                        Thread.sleep(1000);
                        return message;
                    }
                }), getSelf());
            }
        }else{
            unhandled(message);
        }
    }
}
