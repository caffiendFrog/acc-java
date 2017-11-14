package com.caffiendfrog.server.com.caffiendfrog;

import com.caffiendfrog.client.SampleAppService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SampleAppServiceImpl extends RemoteServiceServlet implements SampleAppService {
    public String getWhat(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}
