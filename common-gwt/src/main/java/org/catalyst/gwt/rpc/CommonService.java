package org.catalyst.gwt.rpc;


import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Client side stub for RPC services
 */
@RemoteServiceRelativePath("common")
public interface CommonService extends RemoteService {
    SafeHtml getHeader(final int assetId);
    SafeHtml getFooter(final int assetId);
}
