package org.catalyst.courses.gwt.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.catalyst.gwt.widgets.MultiSelectListBox;

import java.util.List;
import java.util.Map;

public class SearchPanel extends Composite {
    interface Binder extends UiBinder<Widget, SearchPanel> {}
    private static final Binder uiBinder = GWT.create(Binder.class);

    @UiField
    MultiSelectListBox competencies;

//    @UiField
//    MultiSelectListBox comp2;


    public SearchPanel() {
        initWidget(uiBinder.createAndBindUi(this));

        competencies.setMultipleSelect(true);
//        competencies.addChangeHandler(new ChangeHandler() {
//            @Override
//            public void onChange(ChangeEvent changeEvent) {
//
//            }
//        })

    }

    public List<Integer> getSelectedCompetencies() {
        return competencies.getSelectedIds();
    }
    public void setCompetencies(final Map<Integer, String> fakeIdToComptencies) {
        for(Map.Entry<Integer, String> entry : fakeIdToComptencies.entrySet()) {
            competencies.addItem(entry.getValue(), entry.getKey().toString());
        }
    }

}
