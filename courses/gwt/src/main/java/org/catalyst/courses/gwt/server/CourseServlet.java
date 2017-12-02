package org.catalyst.courses.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.catalyst.courses.gwt.rpc.CourseService;

import java.util.HashMap;
import java.util.Map;

public class CourseServlet extends RemoteServiceServlet implements CourseService {
    
    public Map<Integer,String> getMockCompetencies() {  
        Map<Integer,String> results = new HashMap<>();
        results.put(0,"all");
        results.put(2,"monkees");
        results.put(3,"dunkin");
        results.put(5,"flowers");
        results.put(6,"frogs");
        results.put(23,"cats");
        results.put(21,"turkies");
        results.put(11,"peeps");
        return results;
    }

    public Map<Integer, String> getMockTranslations() {
        Map<Integer, String> results = new HashMap<>();
        results.put(0,"all");
        results.put(1,"T1");
        results.put(2,"T2");
        results.put(3,"T3");
        results.put(4,"T4");
        return results;
    }
}
