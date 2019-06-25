package com.yx.example;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 */
public class IotSupervisor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public static Props props() {
        return Props.create(IotSupervisor.class, IotSupervisor::new);
    }


    public void preStart() {
        log.info("IoT Application started");
    }

    public void postStop() {
        log.info("IoT Application stopped");
    }


    public Receive createReceive() {
        return receiveBuilder().build();
    }


}
