package com.example.chapter04;

import akka.actor.ActorRef;
import akka.dispatch.Envelope;
import akka.dispatch.MessageQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class BusinessMsgQueue implements MessageQueue {
    private Queue<Envelope> queue = new ConcurrentLinkedDeque<>();
    @Override
    public void enqueue(ActorRef receiver, Envelope handle) {
        queue.offer(handle);
    }

    @Override
    public Envelope dequeue() {
        return queue.poll();
    }

    @Override
    public int numberOfMessages() {
        return queue.size();
    }

    @Override
    public boolean hasMessages() {
        return !queue.isEmpty();
    }

    @Override
    public void cleanUp(ActorRef owner, MessageQueue deadLetters) {
        for(Envelope envelope :queue){
            deadLetters.enqueue(owner, envelope);
        }
    }
}
