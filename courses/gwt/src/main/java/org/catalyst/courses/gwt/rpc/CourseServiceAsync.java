package org.catalyst.courses.gwt.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Map;

/**
 * The async counterpart of <code>CourseService</code>
 */
public interface CourseServiceAsync {
    void getMockCompetencies(AsyncCallback<Map<Integer, String>> callback);
    void getMockSponsors(AsyncCallback<Map<Integer, String>> callback);
    void getMockTranslations(AsyncCallback<Map<Integer, String>> callback);
    void getLastUpdated(AsyncCallback<String> callback);
}
