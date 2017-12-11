package org.catalyst.gwt.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import org.catalyst.gwt.rpc.CommonService;
import org.catalyst.gwt.rpc.CommonServiceAsync;

public class CommonAssetsPanel extends Composite {
    interface Binder extends UiBinder<Widget, CommonAssetsPanel> {}
    private static final Binder uiBinder = GWT.create(Binder.class);

    private static final CommonServiceAsync commonService = GWT.create(CommonService.class);

    @UiField
    HTML assetsPanel;

    int commonAssetsID = 21;

    // TODO externalize ID number for common assets
    public CommonAssetsPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setHeaderPanel() {
        commonService.getHeader(commonAssetsID, new AsyncCallback<SafeHtml>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(SafeHtml safeHtml) {
                assetsPanel.setHTML(safeHtml);
            }
        });
    }

    public void setFooterPanel() {
        commonService.getFooter(commonAssetsID, new AsyncCallback<SafeHtml>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(SafeHtml safeHtml) {
                assetsPanel.setHTML(safeHtml);
            }
        });
    }
}
