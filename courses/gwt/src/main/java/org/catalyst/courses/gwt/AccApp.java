package org.catalyst.courses.gwt;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import org.catalyst.courses.gwt.components.SearchPanel;
import org.catalyst.courses.gwt.rpc.CourseService;
import org.catalyst.courses.gwt.rpc.CourseServiceAsync;

import java.util.List;
import java.util.Map;

public class AccApp implements EntryPoint {

    private final CourseServiceAsync courseService;

    protected AccApp() {
        courseService = GWT.create(CourseService.class);
    }

    /**
     * Entry point method
     */
    public void onModuleLoad() {
        Panel mainContainer = RootPanel.get("main_container");
        SearchPanel sp = new SearchPanel();
        mainContainer.add(sp);
        courseService.getMockCompetencies(new AsyncCallback<Map<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(Map<Integer, String> integerStringMap) {
                sp.setCompetencies(integerStringMap);
            }
        });

        Button search = new Button("SEARCH");
        search.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                List<Integer> selectedIds = sp.getSelectedCompetencies();
                String alert = "";
                for(Integer id : selectedIds) {
                    alert += id.toString() + " - ";
                }
                Window.alert(alert);
            }
        });
        mainContainer.add(search);
//        SearchPanel sp = new SearchPanel()
//                new SearchPanel(courseService.getMockCompetencies(new AsyncCallback<Map<Integer, String>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(Map<Integer, String> integerStringMap) {
//
//            }
//        }
////        mainContainer.add(new SearchPanel(sp));
//        mainContainer.add(sp);
    }
}
