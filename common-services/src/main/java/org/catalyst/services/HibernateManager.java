package org.catalyst.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HibernateManager {
    private final static Logger logger = LogManager.getLogger(HibernateManager.class);
    private final static boolean DEBUG = logger.isDebugEnabled();
    private final static HibernateManager INSTANCE = new HibernateManager();

    private SessionFactory sessionFactory;
    private List<Session> activeSessions = new ArrayList<>();

    public static HibernateManager getInstance() { return INSTANCE; }

    public HibernateManager() {
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

    /**
     * Saves & commits the list of entities
     * @param entities
     */
    public <T> void saveOrUpdate(List<T> entities) {
        final Session session = startTransaction();
        for ( T entity : entities ) {
            session.saveOrUpdate(entity);
        }
        endTransaction(session);
    }

    /**
     * Convenience method for when there is only a single entity
     * @param entity
     * @param <T>
     */
    public <T> void saveOrUpdate(T entity) {
        saveOrUpdate(Arrays.asList(entity));
    }

    public <T> T getEntity(final Class<T> clazz, final int primaryKey) {
        final Session session = startTransaction();
        T result = session.get(clazz, primaryKey);
        endTransaction(session);
        return result;
    }

    public <T> List<T> getAllEntities(final Class<T> clazz) {
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<T>  criteria = builder.createQuery(clazz);
        Root<T> root = criteria.from(clazz);
        criteria.select(root);
        final Session session = startTransaction();
        if (DEBUG) logger.debug(session.createQuery(criteria).getQueryString());
        List<T> results = session.createQuery(criteria).getResultList();
        endTransaction(session);
        return results;
    }

    public List query(final Class entityClazz, final String clause) {
        final String hql = "SELECT * FROM " + entityClazz.getSimpleName() + " WHERE " + clause;
        final Session session = startTransaction();
        if (DEBUG) logger.debug("QUERY: ["+hql+"]");
        final List result = session.createQuery(hql).list();
        endTransaction(session);
        return result;
    }

    public Session startTransaction() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        activeSessions.add(session);
        return session;
    }

    public void endTransaction(final Session session) {
        session.getTransaction().commit();
        session.close();
        activeSessions.remove(session);
    }


    public void shutdown() {
        // close out any active sessions
        for (Session session : activeSessions) {
            session.close();
        }
        sessionFactory.close();
    }
}
