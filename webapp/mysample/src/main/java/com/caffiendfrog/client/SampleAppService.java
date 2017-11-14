package com.caffiendfrog.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SampleAppService")
public interface SampleAppService extends RemoteService {
    String getWhat(String msg);

    class MyApp {
        private static SampleAppServiceAsync INSTANCE = GWT.create(SampleAppService.class);

        public static synchronized SampleAppServiceAsync getInstance() {
            return INSTANCE;
        }
    }
}
