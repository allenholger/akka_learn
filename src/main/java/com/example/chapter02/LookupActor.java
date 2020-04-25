package com.example.chapter02;

import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class LookupActor extends UntypedAbstractActor {
    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    private ActorRef targetActor = null;
    {
        targetActor = getContext().actorOf(Props.create(TargetActor.class, "targetActor"));
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            if("find".equalsIgnoreCase(message.toString())){
                ActorSelection actorSelection = getContext().actorSelection("targetActor");
                actorSelection.tell(new Identify("A001"), self());

            }else if(message instanceof ActorIdentity){
                ActorIdentity actorIdentity = (ActorIdentity) message;
                if(actorIdentity.correlationId().equals("A001")){
                    ActorRef actorRef = actorIdentity.getActorRef().get();
                    if(actorRef != null){
                        log.info("actorIdentify：" + actorIdentity.correlationId() + "   " + actorRef);
                        actorRef.tell("hello target!", self());
                    }

                }
            }
        }else{
            unhandled(message);
        }
    }
}
