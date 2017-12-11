package org.catalyst.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.UUID;

public class ConnectionManagerTest {
    private final static Logger logger = LogManager.getLogger(ConnectionManagerTest.class);
    @Test
    public void sanityTest() {
        String sessionID = UUID.randomUUID().toString();
        String response = ConnectionManager.getINSTANCE().get(sessionID, "www.google.com");
        logger.debug(response);
    }
}
