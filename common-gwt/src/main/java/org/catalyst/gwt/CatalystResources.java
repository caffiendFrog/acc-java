package org.catalyst.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface CatalystResources extends ClientBundle {
    CatalystResources INSTANCE = GWT.create(CatalystResources.class);

    @Source("catalyst.css")
    CssResource catalystCss();
}
