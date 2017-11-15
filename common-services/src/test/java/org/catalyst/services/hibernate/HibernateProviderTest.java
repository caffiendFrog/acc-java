package org.catalyst.services.hibernate;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tests the hibernate provider using an in-memory H2 database (no particular reason for using H2 besides that is what
 * the tutorials used)
 */
public class HibernateProviderTest extends TestCase {

    private final TestEvent eventA = new TestEvent("Here's a test event", new Date());
    private final TestEvent eventB = new TestEvent("Some more test event", new Date());
    private final TestEvent eventC = new TestEvent("Good-bye crewl werld", new Date());
    private final List<TestEvent> events = Arrays.asList(eventA, eventB);

    public void testSaveAndGet() {
        HibernateProvider.getInstance().save(events);
        List<TestEvent> retrievedEvents = HibernateProvider.getInstance().getEntries(TestEvent.class);
        List<String> retrievedEventTitles = retrievedEvents.stream().map(TestEvent::getTitle).collect(Collectors.toList());
        assertEquals(events.size(), retrievedEvents.size());
        assertTrue(retrievedEventTitles.contains(eventA.getTitle()));
        assertTrue(retrievedEventTitles.contains(eventB.getTitle()));
        assertFalse(retrievedEventTitles.contains(eventC.getTitle()));
    }
}
