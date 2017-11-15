package org.catalyst.sample.webapp;

import junit.framework.TestCase;
import org.catalyst.sample.hibernate.SampleHibernateProvider;
import org.catalyst.services.hibernate.HibernateProvider;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SanityTest extends TestCase {
    @SuppressWarnings("unchecked")
    public void testSanity() {
        SampleHibernateProvider hibernateProvider = SampleHibernateProvider.getInstance();
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

    public void testProvider() {
        List<SanityEvent> events = new ArrayList<>();
        events.add(new SanityEvent( "Our very SECOND event!", new Date() ) );
        events.add(new SanityEvent( "A follow up THIRD event", new Date() ) );
        HibernateProvider.getInstance().save(events);

        List<SanityEvent> results = HibernateProvider.getInstance().getEntries(SanityEvent.class);
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
        for (SanityEvent event : results) {
            System.out.println( "SanityEvent (" + event.getDate() + ") : " + event.getTitle() );
        }
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=");
    }
}
