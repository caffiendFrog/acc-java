package org.catalyst.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private final static Logger logger = LogManager.getLogger(ConnectionManager.class);
    private final static ConcurrentHashMap<String, ConnectionProvider> sessionToConnection = new ConcurrentHashMap();

    private static ConnectionManager INSTANCE = null;
    private static boolean FORCE_SSL_PROTOCOL = true;

    public static ConnectionManager getINSTANCE() {
        if ( INSTANCE == null ) {
            INSTANCE = new ConnectionManager();
        }
        return INSTANCE;
    }

    public ConnectionManager() {

    }

    public String get(final String sessionId, final String url) {
//        URL url = new URL(url)
        String results;
        if (!sessionToConnection.containsKey(sessionId)) {
            sessionToConnection.put(sessionId, new ConnectionProvider());
        }
        ConnectionProvider connection = sessionToConnection.get(sessionId);
        results = connection.getResponse(url);
        return results;
    }

    public void shutdown() {
        synchronized (sessionToConnection) {
            for(ConnectionProvider connection : sessionToConnection.values()) {
                connection.shutdown();
            }
        }
    }
}
