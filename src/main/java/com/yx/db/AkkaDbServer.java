package com.yx.db;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 */
public class AkkaDbServer {

    private static final Logger log = LoggerFactory.getLogger(AkkaDbServer.class);

    public static void main(String[] args) throws Exception {
        try {
            ActorSystem system = ActorSystem.create("akkaDbMy", ConfigFactory.load("application"));
            ActorRef actorRef = system.actorOf(Props.create(AkkaDb.class), "akkaDbMy-db");

            log.info(actorRef.path().toString());

            CountDownLatch latch = new CountDownLatch(1);

            latch.await();
        } catch (Exception e) {
            log.error("server stop !", e);
        }
    }
}
