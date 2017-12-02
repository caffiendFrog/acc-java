package org.catalyst.courses.gwt.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.catalyst.courses.gwt.AccResources;
import org.catalyst.gwt.panels.MultiSelectFilterPanel;

import java.util.List;
import java.util.Map;

public class SearchPanel extends Composite {
    interface Binder extends UiBinder<Widget, SearchPanel> {}
    private static final Binder uiBinder = GWT.create(Binder.class);

    private static final int VISIBLE_ITEM_COUNT = 5;
    private static final String INSTRUCTIONS = "To browse all classes, click Find without selecting search criteria.\n" +
            "To search by more than one item in the dropdown menu, hold down the Control key (PC) or Command key (Mac) when selecting.";

    @UiField
    Label updatedLabel;

    @UiField
    MultiSelectFilterPanel competencyPanel;

    @UiField
    MultiSelectFilterPanel sponsorPanel;

    @UiField
    MultiSelectFilterPanel translationalPanel;

    @UiField
    HTML instructionLabel;

//    @UiField
//    MultiSelectListBox comp2;


    public SearchPanel() {
        AccResources.INSTANCE.accCss().ensureInjected();
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);

       initCompetencyFilter();
       initSponsorFilter();
       initTranslationalFilter();
       initInstrutions();
    }

    private void initInstrutions() {
        final SafeHtmlBuilder htmlBuilder = new SafeHtmlBuilder().appendEscapedLines(INSTRUCTIONS);
        instructionLabel.setHTML(htmlBuilder.toSafeHtml());
        instructionLabel.setStyleName(AccResources.INSTANCE.accCss().instructions());
    }

    private void initCompetencyFilter() {
        competencyPanel.setFilterLabel("Core Competency Area");
        competencyPanel.setVisibleItemCount(VISIBLE_ITEM_COUNT);
    }

    private void initSponsorFilter() {
        sponsorPanel.setFilterLabel("Sponsor");
        sponsorPanel.setVisibleItemCount(VISIBLE_ITEM_COUNT);
    }

    private void initTranslationalFilter() {
        translationalPanel.setFilterLabel("Translational Activity");
        translationalPanel.setVisibleItemCount(VISIBLE_ITEM_COUNT);

        //TODO Externalize this link
        final Anchor footnoteUrl = new Anchor("Translational Activities", "https://catalyst.harvard.edu/pathfinder/");
        final Label footnoteText = new Label("Learn more about ");

        translationalPanel.addFootnoteWidget(footnoteText);
        translationalPanel.addFootnoteWidget(footnoteUrl);
    }

    public void setLastUpdated(final String lastUpdated) {
        final String updatedText = updatedLabel.getText();
        updatedText.concat(lastUpdated);
    }

    public List<Integer> getSelectedCompetencies() {
        return competencyPanel.getSelectedIds();
    }
    public void setCompetencyFilter(final Map<Integer, String> competencyFilterValues) {
        competencyPanel.setFilterValues(competencyFilterValues);
    }

    public List<Integer> getSelectedSponsors() {
        return sponsorPanel.getSelectedIds();
    }

    public void setSponsorFilter(final Map<Integer, String> sponsorFilterValues) {
        sponsorPanel.setFilterValues(sponsorFilterValues);
    }

    public List<Integer> getSelectedTranslations() {
        return translationalPanel.getSelectedIds();
    }

    public void setTranslationalFilter(final Map<Integer, String> translationalFilterValues) {
        translationalPanel.setFilterValues(translationalFilterValues);
    }


}
