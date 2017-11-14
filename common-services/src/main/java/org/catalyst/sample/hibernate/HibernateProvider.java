package org.catalyst.sample.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateProvider {
    private final static Logger logger = LogManager.getLogger(HibernateProvider.class);
    private final static HibernateProvider INSTANCE = new HibernateProvider();

    private SessionFactory sessionFactory;

    public static HibernateProvider getInstance() { return INSTANCE; }

    public HibernateProvider() {
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

}
