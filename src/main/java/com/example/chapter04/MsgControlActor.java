package com.example.chapter04;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.stream.ActorMaterializerSettings;

public class MsgControlActor extends UntypedAbstractActor {

    @Override
    public void onReceive(Object message) throws Throwable {
        System.out.println(message);
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(Props.create(MsgControlActor.class).withMailbox("msgctl-mailbox"), "actorRef");
        Object[] messages = {"Java", "C#", new ControlMsg("ServerPage"), "PHP"};
        for (int i = 0; i < messages.length; i++){
            actorRef.tell(messages[i], ActorRef.noSender());
        }
    }
}
