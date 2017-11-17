package org.catalyst.services.hibernate;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tests the hibernate provider using an in-memory H2 database (no particular reason for using H2 besides that is what
 * the tutorials used)
 */
public class HibernateManagerTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(HibernateManagerTest.class);

    private final TestEvent eventA = new TestEvent("Good-bye crewl werld", new Date());
    private final TestEvent eventB = new TestEvent("Some more test event", new Date());
    private final TestEvent eventC = new TestEvent("Here's a test event", new Date());

    private final List<TestEvent> events = Arrays.asList(eventA, eventB);

    public void testSaveAndGet() {
        HibernateManager.getInstance().saveOrUpdate(events);
        List<TestEvent> retrievedEvents = HibernateManager.getInstance().getAllEntities(TestEvent.class);
        List<String> retrievedEventTitles = retrievedEvents.stream().map(TestEvent::getTitle).collect(Collectors.toList());
        assertEquals(events.size(), retrievedEvents.size());
        assertTrue(retrievedEventTitles.contains(eventA.getTitle()));
        assertTrue(retrievedEventTitles.contains(eventB.getTitle()));
        assertFalse(retrievedEventTitles.contains(eventC.getTitle()));
    }

    public void testQuery() {
        HibernateManager.getInstance().saveOrUpdate(events);
        Session session = HibernateManager.getInstance().startTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<TestEvent> criteria = builder.createQuery(TestEvent.class);
        Root<TestEvent> root = criteria.from(TestEvent.class);

        criteria.select( root );
        criteria.where( builder.like( root.get( TestEvent_.title ), "%crewl%" ) );
        List<TestEvent> results = session.createQuery( criteria ).getResultList();
        assertEquals(1, results.size());
        assertEquals(eventA, results.get(0));

        HibernateManager.getInstance().endTransaction(session);
    }
}
