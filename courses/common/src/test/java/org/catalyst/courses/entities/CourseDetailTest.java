package org.catalyst.courses.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.hibernate.HibernateManager;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class CourseDetailTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(CourseDetailTest.class);

    private final InstitutionDetail hku = new InstitutionDetail(InstitutionDetailTest.hkuName, InstitutionDetailTest.hkuAbbreviation,
                                                    InstitutionDetailTest.hkuIsSponsor);
    private final InstitutionDetail bmc = new InstitutionDetail(InstitutionDetailTest.bmcName, InstitutionDetailTest.bmcAbbreviation);
    private final CompetencyDetail bioCompetency = new CompetencyDetail(CompetencyDetailTest.bioName);
    private final CompetencyDetail chemCompetency = new CompetencyDetail(CompetencyDetailTest.chemNote);
    private final AudienceDetail faculty = new AudienceDetail(AudienceDetailTest.facultyName);
    private final AudienceDetail postDoc = new AudienceDetail(AudienceDetailTest.postDocName);
    private final TranslationDetail t1 = new TranslationDetail(TranslationDetailTest.t1Name, TranslationDetailTest.t1Abbreviation);
    private final TranslationDetail t2 = new TranslationDetail(TranslationDetailTest.t2Name, TranslationDetailTest.t2Abbreviation);

    private final String caffeineCourseName = "Complex union of caffeine and felines.";
    private final String rohtoCourseName = "Partiality confounded Rohto-effect";
    private final String caffeineEmail = "caffeine@coff.ee";
    private final String caffeineUrl = "www.coff.ee";
    private final Date caffeineDate = new Date();
    private final String caffeineDescription = "Happy Monday morning wake up juice. How does it help the furballs?";
    private final String caffeineDateInfo = "Every other Fritzday";
    private final String caffeineHours = "2 hours";
    private final String caffeineMaxEnroll = "10 hoomans";
    private final String caffeineSearchBlob = "blobby blob blob boo";
    private final Date caffeineSortDate = new Date();
    private final String rohtoDescription = "Cure for red eyes and staring at monitors until the eyeballs bleed dry.";
    private final String rohtoHours = "14.5 total";
    private final String rohtoDateInfo = "Occurs annually (Fall)";
    private final String rohtoEmail = "horton.hears@who.oo";


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
        assertEquals(caffeineCourseName, caffeineCourse.getName());

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
        setOptionalFields();

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
        setOptionalFields();

        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(caffeineCourse, rohtoCourse));
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

        // Intentionally not checking the seconds, occasionally it is off by a second, likely because we are using
        // new Date() for the date.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        assertTrue(sdf.format(caffeineDate).equals(sdf.format(result.getDate())));
        assertTrue(sdf.format(caffeineSortDate).equals(sdf.format(result.getSortDate())));
    }

//    public void testRelationships() {
//        // create & save course to get course id
//        setOptionalFields();
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(caffeineCourse, rohtoCourse));
//
//        // create relationships
//        int courseId = caffeineCourse.getId();
//        hku.setCourseId(courseId);
//        bioCompetency.setCourseId(courseId);
//        chemCompetency.setCourseId(courseId);
//        t2.setCourseId(courseId);
//        postDoc.setCourseId(courseId);
//
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(hku, bioCompetency, chemCompetency, t2, postDoc));
//    }

    private void setOptionalFields() {
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
