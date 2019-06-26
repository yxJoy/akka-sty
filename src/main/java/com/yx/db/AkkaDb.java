package com.yx.db;

import akka.actor.AbstractActor;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * db server
 */
public class AkkaDb extends AbstractActor {

    private static final Logger log = LoggerFactory.getLogger(AkkaDb.class);

    static public Props props() {
        return Props.create(AkkaDb.class, () -> new AkkaDb());
    }

    static public class DbRequest implements Serializable {
        public final String key;

        public DbRequest(String key) {
            this.key = key;
        }
    }

    static public class DbSetRequest implements Serializable {
        public final String key;
        public final Object val;

        public DbSetRequest(String key, Object val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            return "DbSetRequest{" +
                    "key='" + key + '\'' +
                    ", val=" + val +
                    '}';
        }
    }

    @Override
    public Receive createReceive() {

        Map<String, Object> internalDb = InternalDb.getInstance().getDbMap();

        return receiveBuilder()
                .match(DbSetRequest.class, msg -> {
                    log.info("set request : {}", msg);
                    log.info("before set internalDb={}", internalDb);
                    internalDb.put(msg.key, msg.val);
                    System.out.println(internalDb);
                    log.info("after set internalDb={}", internalDb);
                    sender().tell("success,key=" + msg.key, self());
                })
                .match(DbRequest.class, msg -> {
                    log.info("dbRequest,key={}", msg.key);
                    log.info("before get internalDb={}", internalDb);
                    Object o = internalDb.get(msg.key);
                    sender().tell(o, self());
                })
                .matchAny(o->
                    sender().tell("bad request,request=" + o, self())
                )
                .build();
    }
}
