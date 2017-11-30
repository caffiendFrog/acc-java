package org.catalyst.courses.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.HibernateManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Testing all fields to make sure we don't accidentally override methods incorrectly in the future
 */
@SuppressWarnings("Duplicates")
public class AudienceTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(AudienceTest.class);

    // protected static so we can reference these values in CourseTest
    protected final static String facultyName = "Faculty";
    protected final static String postDocName = "Post Doc";
    protected final static String facultyNote = "New faculty in the past 5 years only.";

    private final static Audience faculty = new Audience(facultyName);
    private final static Audience postDoc = new Audience(postDocName);

    public void setUp() {
        // reset entities
        faculty.setName(facultyName);
        faculty.setNote(null);
        faculty.activate();
        faculty.courseDetails.clear();

        postDoc.setName(postDocName);
        postDoc.setNote(null);
        postDoc.activate();
        postDoc.courseDetails.clear();

    }

    public void testBasicAudience() {
        assertEquals(facultyName, faculty.getName());

        faculty.setNote(facultyNote);
        assertEquals(facultyNote, faculty.getNote());

        // should default to active
        assertTrue(postDoc.isActive());

        // deactivate and check
        postDoc.deactivate();
        assertFalse(postDoc.isActive());
    }

    /**
     * Should be able to update require fields, but shouldn't be able to set them to null
     */
    public void testRequired() {
        postDoc.setName("qwerty");
        assertEquals("qwerty", postDoc.getName());

        try {
            postDoc.setName(null);
            fail();
        } catch (NullPointerException npe) {
            assertTrue(true);
        } catch (Throwable t) {
            //should only be throwing an NPE
            fail();
        }
    }

    public void testCRUD() {
        // Create/Save audience
        saveAudiences();

        // Read it back and check
        Map<Integer, Audience> idToAudiences = getIdToAudiences();
        assertEquals(2, idToAudiences.size());
        assertEquals(faculty.getName(), idToAudiences.get(faculty.getId()).getName());

        // update one of the audiences
        faculty.setNote(facultyNote);
        faculty.deactivate();
        saveAudiences();

        // Read them back
        idToAudiences = getIdToAudiences();
        assertEquals(2, idToAudiences.size());
        assertEquals(faculty.getNote(), idToAudiences.get(faculty.getId()).getNote());
        assertFalse(idToAudiences.get(faculty.getId()).isActive());
    }

    public void testEquality() {
        HibernateManager.getInstance().saveOrUpdate(faculty);
        Audience result = HibernateManager.getInstance().getEntity(Audience.class, faculty.getId());
        // should be 'logically' equal
        assertEquals(faculty, result);
        // should not be 'physically' equal
        assertFalse( faculty == result );

        // Make sure we still only have 2 rows
        Map<Integer, Audience> idToAudiences = getIdToAudiences();
        assertEquals(2, idToAudiences.size());
    }

    private void saveAudiences() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(faculty, postDoc));
    }

    protected static Map<Integer, Audience> getIdToAudiences() {
        List<Audience> audiences = HibernateManager.getInstance().getAllEntities(Audience.class);
        return audiences.stream().collect(Collectors.toMap(Audience::getId, Function.identity()));
    }
}