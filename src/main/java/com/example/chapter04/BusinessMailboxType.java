package com.example.chapter04;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageQueue;
import com.typesafe.config.Config;

import scala.Option;

public class BusinessMailboxType implements MailboxType {

    public BusinessMailboxType(ActorSystem.Settings setting, Config config){

    }

    @Override
    public MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new BusinessMsgQueue();
    }
}
