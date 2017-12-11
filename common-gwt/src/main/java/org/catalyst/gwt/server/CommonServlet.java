package org.catalyst.gwt.server;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.gwt.rpc.CommonService;
import org.catalyst.services.ConnectionManager;

import java.util.UUID;

public class CommonServlet extends RemoteServiceServlet implements CommonService {
    private static final Logger logger = LogManager.getLogger(CommonServlet.class);

    private final ConnectionManager connectionMgr = ConnectionManager.getINSTANCE();

    private final String header = "http://services.catalyst.harvard.edu/assets/header/";
    private final String footer = "http://services.catalyst.harvard.edu/assets/footer/";

    // TODO Is it safe to assume the html we get from services is safe?
    public SafeHtml getHeader(int assetId) {
        String response = connectionMgr.get(UUID.randomUUID().toString(), header.concat(Integer.toString(assetId)));
        return SafeHtmlUtils.fromTrustedString(response);
    }

    public SafeHtml getFooter(int assetId) {
        String response = connectionMgr.get(UUID.randomUUID().toString(), footer.concat(Integer.toString(assetId)));
        return SafeHtmlUtils.fromTrustedString(response);
    }
}
