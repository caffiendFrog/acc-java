package org.catalyst.courses.gwt.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Map;

/**
 * The async counterpart of <code>CourseService</code>
 */
public interface CourseServiceAsync {
    void getCompetencies(AsyncCallback<Map<Integer, String>> callback);
    void getSponsors(AsyncCallback<Map<Integer, String>> callback);
    void getTranslations(AsyncCallback<Map<Integer, String>> callback);
    void getLastUpdated(AsyncCallback<String> callback);
}
