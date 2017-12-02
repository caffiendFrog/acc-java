package org.catalyst.courses.gwt.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.Map;

/**
 * Client side stub for RPC service
 */
@RemoteServiceRelativePath("course")
public interface CourseService extends RemoteService {
    Map<Integer,String> getMockCompetencies();

}
