package com.yx.db;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class InternalDb {

    private static Map<String, Object> dbMap;

    private InternalDb() {
        if (null == dbMap) {
            dbMap = new HashMap<>();
        }
    }

    static class InternalDbSHolder {
        private static final InternalDb INSTANCE = new InternalDb();
    }

    public static final InternalDb getInstance() {
        return InternalDbSHolder.INSTANCE;
    }

    public static Map<String, Object> getDbMap() {
        return dbMap;
    }
}
