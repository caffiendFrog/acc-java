package org.catalyst.courses.gwt.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
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
    private static final String FILTER_BY = "Filter by:";
    private static final String MULTI_SELECT = "(multi-select)";

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

    @UiField
    HTML filterByLabel;

//    @UiField
//    MultiSelectListBox comp2;


    public SearchPanel() {
        initWidget(uiBinder.createAndBindUi(this));

        initFilterByLabel();
        initCompetencyFilter();
        initSponsorFilter();
        initTranslationalFilter();
        initInstrutions();
    }

    private void initFilterByLabel() {
        final SafeHtmlBuilder htmlBuilder = new SafeHtmlBuilder();
        htmlBuilder.appendHtmlConstant("<b>");
        htmlBuilder.appendEscaped(FILTER_BY);
        htmlBuilder.appendHtmlConstant("</b>");
        htmlBuilder.appendHtmlConstant("<p/>");
        htmlBuilder.appendEscaped(MULTI_SELECT);
        filterByLabel.setHTML(htmlBuilder.toSafeHtml());
        filterByLabel.setStyleName(AccResources.INSTANCE.accCss().instructions());
        filterByLabel.getElement().getStyle().setDisplay(Style.Display.INLINE_BLOCK);
        filterByLabel.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);
        filterByLabel.setWidth("10%");
    }

    private void initInstrutions() {
        final SafeHtmlBuilder htmlBuilder = new SafeHtmlBuilder().appendEscapedLines(INSTRUCTIONS);
        instructionLabel.setHTML(htmlBuilder.toSafeHtml());
        instructionLabel.setStyleName(AccResources.INSTANCE.accCss().instructions());
    }

    private void initCompetencyFilter() {
        competencyPanel.setFilterLabel("Core Competency Area");
        competencyPanel.setVisibleItemCount(VISIBLE_ITEM_COUNT);
        competencyPanel.setListBoxStyle(AccResources.INSTANCE.accCss().translational());
        competencyPanel.getElement().getStyle().setDisplay(Style.Display.INLINE_BLOCK);
        competencyPanel.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);
        competencyPanel.setWidth("34%");
    }

    private void initSponsorFilter() {
        sponsorPanel.setFilterLabel("Sponsor");
        sponsorPanel.setVisibleItemCount(VISIBLE_ITEM_COUNT);
        sponsorPanel.setListBoxStyle(AccResources.INSTANCE.accCss().sponsors());
        sponsorPanel.getElement().getStyle().setDisplay(Style.Display.INLINE_BLOCK);
        sponsorPanel.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);
        sponsorPanel.setWidth("15%");
    }

    private void initTranslationalFilter() {
        translationalPanel.setFilterLabel("Translational Activity");
        translationalPanel.setVisibleItemCount(VISIBLE_ITEM_COUNT);

        //TODO Externalize this link
        final Anchor footnoteUrl = new Anchor("Translational Activities", "https://catalyst.harvard.edu/pathfinder/");
        final Label footnoteText = new Label("Learn more about ");

        translationalPanel.addFootnoteWidget(footnoteText);
        translationalPanel.addFootnoteWidget(footnoteUrl);
        translationalPanel.setListBoxStyle(AccResources.INSTANCE.accCss().translational());
        translationalPanel.getElement().getStyle().setDisplay(Style.Display.INLINE_BLOCK);
        translationalPanel.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);
        translationalPanel.setWidth("26%");
    }

    public void setLastUpdated(final String lastUpdated) {
        String updatedText = updatedLabel.getText();
        updatedText = updatedText.concat(lastUpdated);
        updatedLabel.setText(updatedText);
        updatedLabel.getElement().getStyle().setDisplay(Style.Display.INLINE_BLOCK);
        updatedLabel.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);
        updatedLabel.setWidth("15%");
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
