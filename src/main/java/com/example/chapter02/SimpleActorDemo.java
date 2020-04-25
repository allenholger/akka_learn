package com.example.chapter02;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;
import akka.japi.pf.ReceiveBuilder;

import scala.annotation.meta.param;

public class SimpleActorDemo extends UntypedAbstractActor {

    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);
    //级别1的奖金计算
    private Procedure<Object> levelOne = new Procedure<Object>() {
        @Override
        public void apply(Object param) throws Exception, Exception {
            if(param instanceof String){
                if(param.equals("end")){
                    getContext().unbecome();
                }
            }else{
                Emp emp = (Emp)param;
                double result = emp.getSalary() * 1.8;
                log.info("员工 " + emp.getName() + " 的奖金为：" + result);
            }
        }
    };
    //级别2的奖金计算
    private Procedure<Object> levelTwo = new Procedure<Object>() {
        @Override
        public void apply (Object param) throws Exception, Exception {
            if(param instanceof String){
                if(param.equals("end")){
                    getContext().unbecome();
                }
            }else{
                Emp emp = (Emp)param;
                double result = emp.getSalary() * 1.5;
                log.info("员工 " + emp.getName() + " 的奖金为：" + result);
            }
        }
    };


    @Override
    public void onReceive(Object message) throws Throwable {
        if(message.equals("1")){
            //getContext().become(levelOne);
        }else if (message.equals("2")){
            //getContext().become(levelTwo);
        }
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("sys");
        ActorRef actorRef = actorSystem.actorOf(Props.create(SimpleActorDemo.class), "simpleActorDemo");
        actorRef.tell("1", ActorRef.noSender());
        actorRef.tell(new Emp("张三", 10000.00), ActorRef.noSender());
        actorRef.tell(new Emp("李四", 20000.00), ActorRef.noSender());
        actorRef.tell("end", ActorRef.noSender());
        actorRef.tell("2", ActorRef.noSender());
        actorRef.tell(new Emp("王五", 10000.00), ActorRef.noSender());
        actorRef.tell(new Emp("赵六", 20000.00), ActorRef.noSender());
    }
}

class Emp{
    private String name;
    private Double salary;

    public Emp(String name, Double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
