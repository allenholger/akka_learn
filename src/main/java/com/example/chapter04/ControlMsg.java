package com.example.chapter04;

import akka.dispatch.ControlMessage;

public class ControlMsg implements ControlMessage {
    private final String status;

    public ControlMsg(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ControlMsg{" +
                "status='" + status + '\'' +
                '}';
    }
}
