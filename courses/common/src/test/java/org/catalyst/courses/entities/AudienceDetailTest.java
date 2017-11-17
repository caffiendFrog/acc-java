package org.catalyst.courses.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.HibernateManager;

import java.util.Arrays;

public class AudienceDetailTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(AudienceDetailTest.class);

    // protected static so we can reference these values in CourseDetailTest
    protected final static String facultyName = "Faculty";
    protected final static String postDocName = "Post Doc";
    protected final static String facultyNote = "New faculty in the past 5 years only.";

    private AudienceDetail faculty;
    private AudienceDetail postDoc;

    public void setUp() {
        faculty = new AudienceDetail(facultyName);
        postDoc = new AudienceDetail(postDocName);
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

        AudienceDetail result = HibernateManager.getInstance().getEntity(AudienceDetail.class, faculty.getId());
        assertNull(result.getAbbreviation());
    }

    public void testEquality() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(faculty, postDoc));
        AudienceDetail result = HibernateManager.getInstance().getEntity(AudienceDetail.class, faculty.getId());
        // should be 'logically' equal
        assertEquals(faculty, result);
        // should not be 'physically' equal
        assertFalse( faculty == result );
    }
}
