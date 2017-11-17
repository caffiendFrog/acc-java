package org.catalyst.courses.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class CourseTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(CourseTest.class);

    private final Institution hku = new Institution(InstitutionTest.hkuName, InstitutionTest.hkuAbbreviation,
                                                    InstitutionTest.hkuIsSponsor);
    private final Institution bmc = new Institution(InstitutionTest.bmcName, InstitutionTest.bmcAbbreviation);
    private final Competency bioCompetency = new Competency(CompetencyTest.bioCompetencyName);
    private final Competency chemCompetency = new Competency(CompetencyTest.chemCompetencyNote);
    private final Audience faculty = new Audience(AudienceTest.facultyName);
    private final Audience postDoc = new Audience(AudienceTest.postDocName);
    private final Translation t1 = new Translation(TranslationTest.t1Name, TranslationTest.t1Abbreviation);
    private final Translation t2 = new Translation(TranslationTest.t2Name, TranslationTest.t2Abbreviation);

    private final String caffeineCourseName = "Complex union of caffeine and felines.";
    private final String rohtoCourseName = "Partiality confounded Rohto-effect";
    private final String caffeineEmail = "caffeine@coff.ee";
    private final String caffieneUrl = "www.coff.ee";
    private final Date caffeineDate = new Date();
    private final String caffeineDescription = "Happy Monday morning wake up juice. How does it help the furballs?";
    private final String caffeineDateInfo = "Every other Fritzday";
    private final String caffeineHours = "2 hours";
    private final String caffeineMaxEnroll = "10 hoomans";
    private final String caffeineSearchBlob = "blobby blob blob boo";
    private final Date caffeineSortDate = new Date();


    private Course caffeineCourse;
    private Course rohtoCourse;

    public void setUp() {
        caffeineCourse = new Course(caffeineCourseName);
        rohtoCourse = new Course(rohtoCourseName);
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
    }
}
