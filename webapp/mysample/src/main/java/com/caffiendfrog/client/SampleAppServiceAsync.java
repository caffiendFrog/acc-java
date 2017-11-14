package com.caffiendfrog.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SampleAppServiceAsync {
    void getWhat(String msg, AsyncCallback<String> callback);
}
