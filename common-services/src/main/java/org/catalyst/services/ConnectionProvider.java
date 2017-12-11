package org.catalyst.services;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ConnectionProvider {
    private final static Logger logger = LogManager.getLogger(ConnectionProvider.class);
    private final static boolean DEBUG = logger.isDebugEnabled();

    private CloseableHttpClient client;

    private long lastAccess;

    public ConnectionProvider() {
        client = HttpClients.createDefault();
    }

    /**
     * Returns null if there is a problem with the request
     * @param url
     * @return
     */
    public String getResponse(final String url) {
        HttpGet httpGet = new HttpGet(url);
        String responseBody = null;
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                responseBody = EntityUtils.toString(response.getEntity());
            } else {
                logger.warn("The response for this request was not 200, not reading the response: "
                        + statusCode);
            }
        } catch (IOException io) {
            logger.warn("There was a problem trying to do a get, this may cause additional problems: "+
                io.getMessage());
            if (DEBUG) logger.debug(io);
        }
        return  responseBody;
    }

    public void shutdown() {
        try {
            client.close();
        } catch (IOException io) {
            logger.warn("There was a problem trying to close the http client, this may cause additional problems: ["
                    + io.getMessage() +"]");
            if (DEBUG) logger.debug(io);
        }
    }
}
