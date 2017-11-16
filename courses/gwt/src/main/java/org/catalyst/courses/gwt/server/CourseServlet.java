package org.catalyst.courses.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.courses.gwt.rpc.CourseService;
import org.catalyst.services.hibernate.HibernateManager;

public class CourseServlet extends RemoteServiceServlet implements CourseService {
    private final static Logger logger = LogManager.getLogger(CourseService.class);

    private final HibernateManager hibernateProvider = HibernateManager.getInstance();
}
