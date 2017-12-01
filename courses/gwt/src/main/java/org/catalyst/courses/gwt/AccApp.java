package org.catalyst.courses.gwt;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import org.catalyst.courses.gwt.components.SearchPanel;

public class AccApp implements EntryPoint {

    /**
     * Entry point method
     */
    public void onModuleLoad() {
        Panel mainContainer = RootPanel.get("main_container");
        mainContainer.add(new SearchPanel());
    }
}
