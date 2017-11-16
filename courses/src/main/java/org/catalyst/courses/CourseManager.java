package org.catalyst.courses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.hibernate.HibernateManager;

public class CourseManager {
    private final static Logger logger = LogManager.getLogger(CourseManager.class);

    private final HibernateManager hibernateProvider = HibernateManager.getInstance();
}
