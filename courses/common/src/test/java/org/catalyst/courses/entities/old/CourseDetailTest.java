package org.catalyst.courses.entities.old;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.courses.test.InstitutionDetail_new;
import org.catalyst.services.HibernateManager;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class CourseDetailTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(CourseDetailTest.class);

    protected static final String caffeineCourseName = "Complex union of caffeine and felines.";
    protected static final String rohtoCourseName = "Partiality confounded Rohto-effect";
    protected static final String caffeineEmail = "caffeine@coff.ee";
    protected static final String caffeineUrl = "www.coff.ee";
    protected static final Date caffeineDate = new Date();
    protected static final String caffeineDescription = "Happy Monday morning wake up juice. How does it help the furballs?";
    protected static final String caffeineDateInfo = "Every other Fritzday";
    protected static final String caffeineHours = "2 hours";
    protected static final String caffeineMaxEnroll = "10 hoomans";
    protected static final String caffeineSearchBlob = "blobby blob blob boo";
    protected static final Date caffeineSortDate = new Date();
    protected static final String rohtoDescription = "Cure for red eyes and staring at monitors until the eyeballs bleed dry.";
    protected static final String rohtoHours = "14.5 total";
    protected static final String rohtoDateInfo = "Occurs annually (Fall)";
    protected static final String rohtoEmail = "horton.hears@who.oo";


    private CourseDetail caffeineCourse;
    private CourseDetail rohtoCourse;

    public void setUp() {
        caffeineCourse = new CourseDetail(caffeineCourseName);
        rohtoCourse = new CourseDetail(rohtoCourseName);
    }

    /**
     * Quick sanity check we're still setting the basics correctly and check the course specific fields and defaults
     */
    public void testBasicCourse() {
//        assertEquals(caffeineCourseName, caffeineCourse.getName());

        // courses should be defaulted to un-archived
        assertFalse(rohtoCourse.isArchived());
        caffeineCourse.archive();
        assertTrue(caffeineCourse.isArchived());
        caffeineCourse.unArchive();
        assertFalse(caffeineCourse.isArchived());

        // courses should default to not having a webcast
        assertFalse(rohtoCourse.hasWebcast());
        rohtoCourse.setWebcast(true);
        assertTrue(rohtoCourse.hasWebcast());

        // set and check optional fields
        setOptionalFields(caffeineCourse, rohtoCourse);

        assertEquals(caffeineUrl, caffeineCourse.getContactUrl());
        assertEquals(caffeineDate, caffeineCourse.getDate());
        assertEquals(caffeineMaxEnroll, caffeineCourse.getMaxEnroll());
        assertEquals(caffeineSearchBlob, caffeineCourse.getSearchBlob());
        assertEquals(caffeineSortDate, caffeineCourse.getSortDate());

        assertEquals(rohtoEmail, rohtoCourse.getContactEmail());
        assertEquals(rohtoDateInfo, rohtoCourse.getDateInfo());
        assertEquals(rohtoDescription, rohtoCourse.getDescription());
        assertEquals(rohtoHours, rohtoCourse.getHours());
    }

    /**
     * Check that course specific fields are being saved and retrieved correctly
     */
    public void testCRUD() {
        setOptionalFields(caffeineCourse, rohtoCourse);
//        InstitutionDetail institutionDetail = new InstitutionDetail(hkuName, hkuAbbreviation);
//        caffeineCourse.setInstitutionDetail( institutionDetail );
        InstitutionDetail_new institutionDetail = new InstitutionDetail_new();

        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(institutionDetail, caffeineCourse, rohtoCourse));
        CourseDetail result = HibernateManager.getInstance().getEntity(CourseDetail.class, rohtoCourse.getId());
        // check the defaults
        assertFalse(result.isArchived());
        assertFalse(result.isArchived());
        assertNull(result.getContactUrl());
        assertNull(result.getDate());
        assertNull(result.getMaxEnroll());
        assertNull(result.getSearchBlob());
        assertNull(result.getSortDate());

        // check the fields we did set
        assertEquals(rohtoEmail, result.getContactEmail());
        assertEquals(rohtoDateInfo, result.getDateInfo());
        assertEquals(rohtoDescription, result.getDescription());
        assertEquals(rohtoHours, result.getHours());

        result = HibernateManager.getInstance().getEntity(CourseDetail.class, caffeineCourse.getId());
        assertEquals(caffeineUrl, result.getContactUrl());
        assertEquals(caffeineMaxEnroll, result.getMaxEnroll());

        // Need to format the java date to what is stored in the db
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(sdf.format(caffeineDate), result.getDate().toString());
        assertEquals(sdf.format(caffeineSortDate), result.getSortDate().toString());
    }

    public void testEquality() {
        setOptionalFields(caffeineCourse, rohtoCourse);
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(caffeineCourse, rohtoCourse));
        CourseDetail result = HibernateManager.getInstance().getEntity(CourseDetail.class, caffeineCourse.getId());

        // should be 'logically' equal
        assertEquals(caffeineCourse, result);

        // should not be 'physically' equal
        assertFalse(caffeineCourse == result);
    }

    protected static void setOptionalFields(final CourseDetail caffeineCourse, final CourseDetail rohtoCourse) {
        caffeineCourse.setContactEmail(caffeineEmail);
        caffeineCourse.setContactUrl(caffeineUrl);
        caffeineCourse.setDate(caffeineDate);
        caffeineCourse.setDateInfo(caffeineDateInfo);
        caffeineCourse.setDescription(caffeineDescription);
        caffeineCourse.setHours(caffeineHours);
        caffeineCourse.setMaxEnroll(caffeineMaxEnroll);
        caffeineCourse.setSearchBlob(caffeineSearchBlob);
        caffeineCourse.setSortDate(caffeineSortDate);

        rohtoCourse.setContactEmail(rohtoEmail);
        rohtoCourse.setDateInfo(rohtoDateInfo);
        rohtoCourse.setDescription(rohtoDescription);
        rohtoCourse.setHours(rohtoHours);
    }
}
