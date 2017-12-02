package org.catalyst.courses.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface AccResources extends ClientBundle {
    AccResources INSTANCE = GWT.create(AccResources.class);

    @Source("acc.css")
    AccStyles accCss();

}
