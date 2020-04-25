package com.example.chapter04;

import akka.actor.ActorSystem;
import akka.dispatch.PriorityGenerator;
import akka.dispatch.UnboundedStablePriorityMailbox;
import com.typesafe.config.Config;

public class MsgUnboundPriorityMailbox extends UnboundedStablePriorityMailbox {

    public MsgUnboundPriorityMailbox(ActorSystem.Settings settings, Config config){
        super(new PriorityGenerator() {
            @Override
            public int gen(Object message) {
                if (message.equals("张三")) {
                    return 0;
                } else if (message.equals("李四")) {
                    return 1;
                } else if (message.equals("王五")) {
                    return 2;
                }else{
                    return 5;
                }
            }
        });
    }

}
