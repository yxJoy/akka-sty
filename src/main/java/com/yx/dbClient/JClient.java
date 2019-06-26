package com.yx.dbClient;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import com.typesafe.config.ConfigFactory;
import com.yx.db.AkkaDb;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * client
 */
public class JClient {

    private final ActorSystem system = ActorSystem.create("clientSystem", ConfigFactory.load("application_client"));
    private final ActorSelection remoteDb;

    public JClient(String remoteAddress) {
        this.remoteDb = system.actorSelection("akka.tcp://akkaDbMy@" +
            remoteAddress + "/user/akkaDbMy-db");
    }

    public Future set(String key, Object val) {
        AkkaDb.DbSetRequest request = new AkkaDb.DbSetRequest(key, val);
        Future<Object> future = Patterns.ask(remoteDb, request, 30000);
        return future;
    }


    public Object get(String key) {
        Object result = null;
        try {
            Future<Object> future = Patterns.ask(remoteDb, new AkkaDb.DbRequest(key), 30000);
            result = Await.result(future, Duration.create(60, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
