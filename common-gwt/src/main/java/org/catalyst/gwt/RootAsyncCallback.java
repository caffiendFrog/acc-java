package org.catalyst.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RootAsyncCallback<T> implements AsyncCallback<T> {
    private final static Logger logger = LogManager.getLogger(RootAsyncCallback.class);
    private final static boolean DEBUG = logger.isDebugEnabled();

    @Override
    public void onFailure(final Throwable caught) {
        //TODO make this smarter once application is more fleshed out
        logger.error(caught.getMessage());
    }

    @Override
    public abstract void onSuccess(final T result);
}
