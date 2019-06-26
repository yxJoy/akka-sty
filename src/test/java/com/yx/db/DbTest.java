package com.yx.db;

import com.yx.dbClient.JClient;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Future;

/**
 * unit test
 */
public class DbTest {


    private static final Logger log = LoggerFactory.getLogger(DbTest.class);

    public static final String REMOTE_ADDRESS = "127.0.0.1:2552";

    public static final String TEST_KEY = "LOVE";

    static JClient J_CLIENT = new JClient(REMOTE_ADDRESS);

    @Test
    public void setTest() {
        Future future = J_CLIENT.set(TEST_KEY, "JOY");
        log.info("future={}", future);

        Object o = J_CLIENT.get(TEST_KEY);
        Assert.assertEquals("JOY", o);
    }


    @Test
    public void getTest() {
        Object o = J_CLIENT.get(TEST_KEY);
        Assert.assertEquals("JOY", o);
    }

}
