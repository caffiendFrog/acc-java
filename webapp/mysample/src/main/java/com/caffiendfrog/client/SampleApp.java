package com.caffiendfrog.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class SampleApp implements EntryPoint {
    public void onModuleLoad() {
        final Button button = new Button("Good grief click me");
        final Label label = new Label();

        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (label.getText().equals("")) {
                    SampleAppService.MyApp.getInstance().getWhat("Good bye crewl werld", new MyCallback(label));
                } else {
                    label.setText("get out of here");
                }
            }
        });

        RootPanel.get("slot1").add(button);
        RootPanel.get("slot2").add(label);
    }

    private static class MyCallback implements AsyncCallback<String> {
        private Label label;

        public MyCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
