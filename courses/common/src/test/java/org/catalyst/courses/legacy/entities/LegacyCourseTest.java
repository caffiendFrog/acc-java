package org.catalyst.courses.legacy.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class LegacyCourseTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(LegacyCourseTest.class);

    private final static String configFilename = "legacy_hibernate.cfg.xml";
    private SessionFactory sessionFactory;

    public void setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(configFilename)
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            logger.debug((e.getMessage()));
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void testLoad() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<LegacyCourse> results = session.createQuery("from "+LegacyCourse.class.getSimpleName()).list();
        for (LegacyCourse lc : results) {
            System.out.println(lc);
        }
    }
}
