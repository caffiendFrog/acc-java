package org.catalyst.courses.gwt.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ResultsPanel extends Composite {
    interface Binder extends UiBinder<Widget, ResultsPanel>{}
    private static final Binder uiBinder = GWT.create(Binder.class);

    public ResultsPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
