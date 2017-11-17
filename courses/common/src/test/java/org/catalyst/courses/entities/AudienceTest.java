package org.catalyst.courses.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.hibernate.HibernateManager;

import java.util.Arrays;

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

    /**
     * Basic unit test to check the behavior of the required name field and also to check that non-applicable fields
     * (abbreviation) is always null and can't be set accidentally.
     */
    public void testBasicAudience() {
        assertEquals(facultyName, faculty.getName());

        faculty.setNote(facultyNote);
        assertEquals(facultyNote, faculty.getNote());

        // abbreviation should always be null
        faculty.setAbbreviation("FCY");
        assertNull(faculty.getAbbreviation());
    }

    /**
     * Most of the fields are already covered in the <code>DetailTest</code>. Check that we don't accidentally save
     * an abbreviation
     */
    public void testCRUD() {
        // try to set the abbreviation
        postDoc.setAbbreviation("pd");

        // Create/Save audience
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(faculty, postDoc));

        Audience result = HibernateManager.getInstance().getEntity(Audience.class, faculty.getId());
        assertNull(result.getAbbreviation());

        // check the equals override works
        assertEquals(faculty, result);
    }
}
