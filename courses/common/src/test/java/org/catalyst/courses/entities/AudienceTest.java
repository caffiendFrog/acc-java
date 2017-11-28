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
public class AudienceTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(AudienceTest.class);

    // protected static so we can reference these values in CourseTest
    protected final static String facultyName = "Faculty";
    protected final static String postDocName = "Post Doc";
    protected final static String facultyNote = "New faculty in the past 5 years only.";

    private Audience faculty;
    private Audience postDoc;

    public void setUp() {
        faculty = new Audience(facultyName);
        postDoc = new Audience(postDocName);
    }

    @SuppressWarnings("Duplicates")
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
    @SuppressWarnings("Duplicates")
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
        assertEquals(faculty.getName(), idToAudiences.get(faculty.getId()).getName());

        // update one of the audiences
        faculty.setNote(facultyNote);
        faculty.deactivate();
        saveAudiences();

        // Read them back
        idToAudiences = getIdToAudiences();
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
    }

    private void saveAudiences() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(faculty, postDoc));
    }

    private Map<Integer, Audience> getIdToAudiences() {
        List<Audience> audiences = HibernateManager.getInstance().getAllEntities(Audience.class);
        return audiences.stream().collect(Collectors.toMap(Audience::getId, Function.identity()));
    }
}