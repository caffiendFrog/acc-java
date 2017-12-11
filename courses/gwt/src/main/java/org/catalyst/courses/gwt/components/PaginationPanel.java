package org.catalyst.courses.gwt.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PaginationPanel extends Composite {
    interface Binder extends UiBinder<Widget, PaginationPanel> {}
    private static final Binder uiBinder = GWT.create(Binder.class);

    public PaginationPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
