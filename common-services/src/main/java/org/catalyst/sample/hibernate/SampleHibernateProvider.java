package org.catalyst.sample.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class SampleHibernateProvider {
    private final static Logger logger = LogManager.getLogger(SampleHibernateProvider.class);
    private final static SampleHibernateProvider INSTANCE = new SampleHibernateProvider();

    private SessionFactory sessionFactory;

    public static SampleHibernateProvider getInstance() { return INSTANCE; }

    public SampleHibernateProvider() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            logger.debug(e.getMessage());
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    /**
     * Saves & commits the list of entries
     * @param entries
     */
    public <T> void save(List<T> entries) {
        final Session session = sessionFactory.openSession();
        session.beginTransaction();
        for ( T entry : entries ) {
            session.save(entry);
        }
        session.getTransaction().commit();
        session.close();
    }

    public <T> List<T> getEntries() {
        final Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from SanityEvent").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

}
