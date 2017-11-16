package org.catalyst.services.hibernate;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tests the hibernate provider using an in-memory H2 database (no particular reason for using H2 besides that is what
 * the tutorials used)
 */
public class HibernateProviderTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(HibernateProviderTest.class);

    private final TestEvent eventA = new TestEvent("Here's a test event", new Date());
    private final TestEvent eventB = new TestEvent("Some more test event", new Date());
    private final TestEvent eventC = new TestEvent("Good-bye crewl werld", new Date());
    private final List<TestEvent> events = Arrays.asList(eventA, eventB);

    public void testSaveAndGet() {
        HibernateProvider.getInstance().saveOrUpdate(events);
        List<TestEvent> retrievedEvents = HibernateProvider.getInstance().getAll(TestEvent.class);
        List<String> retrievedEventTitles = retrievedEvents.stream().map(TestEvent::getTitle).collect(Collectors.toList());
        assertEquals(events.size(), retrievedEvents.size());
        assertTrue(retrievedEventTitles.contains(eventA.getTitle()));
        assertTrue(retrievedEventTitles.contains(eventB.getTitle()));
        assertFalse(retrievedEventTitles.contains(eventC.getTitle()));
    }

    public void testQuery() {
        HibernateProvider.getInstance().saveOrUpdate(events);
        final String queryString = "TestEvent.title like '%more%'";
        List<TestEvent> results = HibernateProvider.getInstance().query(TestEvent.class, queryString);
        assertEquals(1, results.size());
    }
}
