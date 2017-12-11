package org.catalyst.courses.gwt;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class AccApp implements EntryPoint {

//    private final CourseServiceAsync courseService;

//    protected AccApp() {
//        courseService = GWT.create(CourseService.class);
//    }

    /**
     * Entry point method
     */
    public void onModuleLoad() {
//        DockLayoutPanel rootPanel = new DockLayoutPanel(Style.Unit.EM);
//        rootPanel.addNorth(new HTML("common assetss header"),15);
//        rootPanel.addSouth(new HTML("common assets footer"), 15);
//        rootPanel.add(new ContentPanel());
//        RootLayoutPanel.get().add(rootPanel);

        RootLayoutPanel.get().add(new ContentPanel());
        RootLayoutPanel.get().forceLayout();

//        Button search = new Button("SEARCH");
//        search.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent clickEvent) {
//                List<Integer> selectedIds = sp.getSelectedCompetencies();
//                String alert = "";
//                for(Integer id : selectedIds) {
//                    alert += id.toString() + " - ";
//                }
//                Window.alert(alert);
//            }
//        });
    }
}
