package org.catalyst.gwt.rpc;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>CommonService</code>
 */
public interface CommonServiceAsync {
    void getHeader(int assetId, AsyncCallback<SafeHtml> callback);
    void getFooter(int assetId, AsyncCallback<SafeHtml> callback);
}
