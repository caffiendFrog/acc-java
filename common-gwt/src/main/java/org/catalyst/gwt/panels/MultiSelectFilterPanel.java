package org.catalyst.gwt.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import org.catalyst.gwt.widgets.MultiSelectListBox;

import java.util.List;
import java.util.Map;

public class MultiSelectFilterPanel extends Composite {
    interface Binder extends UiBinder<Widget, MultiSelectFilterPanel> {}
    private static final Binder uiBinder = GWT.create(Binder.class);

    @UiField
    protected Label filterLabel;

    @UiField
    protected MultiSelectListBox filterListBox;

    @UiField
    protected FlowPanel footnotePanel;

    public MultiSelectFilterPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setFilterLabel(final String label) {
        filterLabel.setText(label);
    }

    public void addFootnoteWidget(final Widget child) {
        footnotePanel.add(child);
    }

    public void setFilterValues(final Map<Integer, String> filterValues) {
        for(Map.Entry<Integer, String> entry : filterValues.entrySet()) {
            filterListBox.addItem(entry.getValue(), entry.getKey().toString());
        }
    }

    public List<Integer> getSelectedIds() {
        return filterListBox.getSelectedIds();
    }

    public void setVisibleItemCount(final int numVisibleItems) {
        filterListBox.setVisibleItemCount(numVisibleItems);
    }
}
