package org.catalyst.courses.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.catalyst.courses.gwt.components.ResultsPanel;
import org.catalyst.courses.gwt.components.SearchPanel;
import org.catalyst.courses.gwt.rpc.CourseService;
import org.catalyst.courses.gwt.rpc.CourseServiceAsync;
import org.catalyst.gwt.panels.CommonAssetsPanel;

import java.util.Map;

public class ContentPanel extends Composite {
    interface Binder extends UiBinder<Widget, ContentPanel> {}
    private static final Binder uiBinder = GWT.create(Binder.class);
    
    private static final CourseServiceAsync courseService = GWT.create(CourseService.class);

    @UiField
    CommonAssetsPanel headerPanel;

    @UiField
    CommonAssetsPanel footerPanel;

    @UiField
    SearchPanel searchPanel;
    
    @UiField
    ResultsPanel resultsPanel;

    public ContentPanel() {
        AccResources.INSTANCE.accCss().ensureInjected();
        initWidget(uiBinder.createAndBindUi(this));
        initSearchPanel();
        headerPanel.setHeaderPanel();
        footerPanel.setFooterPanel();
    }
    
    private void initSearchPanel() {
        courseService.getCompetencies(new AsyncCallback<Map<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(Map<Integer, String> integerStringMap) {
                searchPanel.setCompetencyFilter(integerStringMap);
            }
        });

        courseService.getSponsors(new AsyncCallback<Map<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(Map<Integer, String> integerStringMap) {
                searchPanel.setSponsorFilter(integerStringMap);
            }
        });

        courseService.getTranslations(new AsyncCallback<Map<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(Map<Integer, String> integerStringMap) {
                searchPanel.setTranslationalFilter(integerStringMap);
            }
        });

        courseService.getLastUpdated(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(String s) {
                searchPanel.setLastUpdated(s);
//                Window.alert(s);
            }
        });

    }
}
