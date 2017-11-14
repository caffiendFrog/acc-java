package org.catalyst.sample.webapp;

import junit.framework.TestCase;
import org.catalyst.sample.hibernate.Event;
import org.catalyst.sample.hibernate.HibernateProvider;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class SanityTest extends TestCase {
    @SuppressWarnings("unchecked")
    public void testSanity() {
        HibernateProvider hibernateProvider = HibernateProvider.getInstance();
        Session session = hibernateProvider.getSession();
        session.beginTransaction();
        session.save( new SanityEvent( "Our very first event!", new Date() ) );
        session.save( new SanityEvent( "A follow up event", new Date() ) );
        session.getTransaction().commit();
        session.close();

        // now lets pull events from the database and list them
        session = hibernateProvider.getSession();
        session.beginTransaction();
        List result = session.createQuery( "from SanityEvent" ).list();
        for ( SanityEvent event : (List<SanityEvent>) result ) {
            System.out.println( "SanityEvent (" + event.getDate() + ") : " + event.getTitle() );
        }
        session.getTransaction().commit();
        session.close();
    }
}
