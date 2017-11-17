package org.catalyst.courses.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.HibernateManager;

import java.util.Arrays;

public class CourseTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(CourseTest.class);

    private final InstitutionDetail hku = new InstitutionDetail(InstitutionDetailTest.hkuName, InstitutionDetailTest.hkuAbbreviation,
            InstitutionDetailTest.hkuIsSponsor);
    private final InstitutionDetail bmc = new InstitutionDetail(InstitutionDetailTest.bmcName, InstitutionDetailTest.bmcAbbreviation);
    private final CompetencyDetail bio = new CompetencyDetail(CompetencyDetailTest.bioName);
    private final CompetencyDetail chem = new CompetencyDetail(CompetencyDetailTest.chemNote);
    private final AudienceDetail faculty = new AudienceDetail(AudienceDetailTest.facultyName);
    private final AudienceDetail postDoc = new AudienceDetail(AudienceDetailTest.postDocName);
    private final TranslationDetail t1 = new TranslationDetail(TranslationDetailTest.t1Name, TranslationDetailTest.t1Abbreviation);
    private final TranslationDetail t2 = new TranslationDetail(TranslationDetailTest.t2Name, TranslationDetailTest.t2Abbreviation);
    private final CourseDetail caffeineDetail = new CourseDetail(CourseDetailTest.caffeineCourseName);
    private final CourseDetail rohtoDetail = new CourseDetail(CourseDetailTest.rohtoCourseName);

    private Course caffeine;
    private Course rohto;

    public void setUp() {
        // Need to save data to database to get ids
        CourseDetailTest.setOptionalFields(caffeineDetail, rohtoDetail);
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(hku, bmc, bio, chem, faculty, postDoc, t1, t2, caffeineDetail, rohtoDetail));

        caffeine = new Course(caffeineDetail.getId(), t1.getId(), faculty.getId(), bmc.getId(), chem.getId());
        rohto = new Course(rohtoDetail.getId(), t2.getId(), faculty.getId(), hku.getId(), bio.getId());
    }
    /**
     * Do a quick check that we're setting & getting the right things
     */
    public void testBasicCourse() {
        assertEquals(t1.getId(), caffeine.getTranslationDetailId());
        assertEquals(rohtoDetail.getId(), rohto.getCourseDetailId());
        assertEquals(faculty.getId(), caffeine.getAudienceDetailId());
        assertEquals(hku.getId(), rohto.getInstitutionDetailId());
        assertEquals(bio.getId(), rohto.getCompetencyDetailId());
    }

    /**
     * Check that fields are all getting saved & retrieved correctly
     */
    public void testCRUD() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(caffeine, rohto));
        Course result = HibernateManager.getInstance().getEntity(Course.class, caffeine.getId());

        assertEquals(caffeineDetail.getId(), result.getCourseDetailId());
        assertEquals(t1.getId(), result.getTranslationDetailId());
        assertEquals(faculty.getId(), result.getAudienceDetailId());
        assertEquals(bmc.getId(), result.getInstitutionDetailId());
        assertEquals(chem.getId(), result.getCompetencyDetailId());
    }

    public void testEquality() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(caffeine, rohto));
        Course result = HibernateManager.getInstance().getEntity(Course.class, rohto.getId());

        // should be 'logically' equal
        assertEquals(rohto, result);

        // should not be 'physically' equal
        assertFalse(rohto == result);
    }
}
