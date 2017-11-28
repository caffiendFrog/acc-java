package org.catalyst.courses.entities.old;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.HibernateManager;

import java.util.Arrays;
import java.util.Date;

public class CourseTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(CourseTest.class);

    protected final static String hkuName = "Hello Kitty University";
    protected final static String bmcName = "Badtz Maru College";
    protected final static String hkuAbbreviation = "HKU";
    protected final static String bmcAbbreviation = "BMC";
    protected final static String bmcNote = "Cartoon penguins allowed only";
    protected final static boolean hkuIsSponsor = false;
    protected final static String t1Name = "TranslationDetail to Alpacas";
    protected final static String t1Abbreviation = "T1";
    protected final static String t2Name = "TranslationDetail to Robots";
    protected final static String t2Abbreviation = "T2";
    protected final static String t2Note = "Robots must not be operating with windows. Only doors.";

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

    public void testSanity() {
        TranslationDetail t1 = new TranslationDetail(t1Name, t1Abbreviation);
        TranslationDetail t2 = new TranslationDetail(t2Name, t2Abbreviation);
        CourseDetail course =  new CourseDetail(caffeineCourseName);
//        Course course = new Course();
        course.setContactEmail(caffeineEmail);
        course.setContactUrl(caffeineUrl);
        course.setDate(caffeineDate);
        course.setDateInfo(caffeineDateInfo);
        course.setDescription(caffeineDescription);
        course.setHours(caffeineHours);
        course.setMaxEnroll(caffeineMaxEnroll);
        course.setSearchBlob(caffeineSearchBlob);
        course.setSortDate(caffeineSortDate);

        course.addTranslation(t1);
        course.addTranslation(t2);

        Detail hkuDetail = new Detail(hkuName, hkuAbbreviation);
        Detail bmcDetail = new Detail(bmcName, bmcAbbreviation);
        InstitutionDetail hku = new InstitutionDetail(hkuDetail);
        InstitutionDetail bmc = new InstitutionDetail(bmcDetail);
        bmc.setSponsor(true);

        course.sponsors.add(hku);
        course.sponsors.add(bmc);
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(t1, t2, hkuDetail, bmcDetail));
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(hku, bmc));
        HibernateManager.getInstance().saveOrUpdate(course);

    }
//    private final InstitutionDetail hku = new InstitutionDetail(InstitutionDetailTest.hkuName, InstitutionDetailTest.hkuAbbreviation,
//            InstitutionDetailTest.hkuIsSponsor);
//    private final InstitutionDetail bmc = new InstitutionDetail(InstitutionDetailTest.bmcName, InstitutionDetailTest.bmcAbbreviation);
//    private final CompetencyDetail bio = new CompetencyDetail(CompetencyDetailTest.bioName);
//    private final CompetencyDetail chem = new CompetencyDetail(CompetencyDetailTest.chemNote);
//    private final AudienceDetail faculty = new AudienceDetail(AudienceDetailTest.facultyName);
//    private final AudienceDetail postDoc = new AudienceDetail(AudienceDetailTest.postDocName);
//    private final TranslationDetail t1 = new TranslationDetail(TranslationDetailTest.t1Name, TranslationDetailTest.t1Abbreviation);
//    private final TranslationDetail t2 = new TranslationDetail(TranslationDetailTest.t2Name, TranslationDetailTest.t2Abbreviation);
//    private final CourseDetail caffeineDetail = new CourseDetail(CourseDetailTest.caffeineCourseName);
//    private final CourseDetail rohtoDetail = new CourseDetail(CourseDetailTest.rohtoCourseName);
//
//    private Course caffeine;
//    private Course rohto;
//
//    public void setUp() {
//        // Need to save data to database to get ids
//        CourseDetailTest.setOptionalFields(caffeineDetail, rohtoDetail);
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(hku, bmc, bio, chem, faculty, postDoc, t1, t2, caffeineDetail, rohtoDetail));
//
//        caffeine = new Course(caffeineDetail.getId(), t1.getId(), faculty.getId(), bmc.getId(), chem.getId());
//        rohto = new Course(rohtoDetail.getId(), t2.getId(), faculty.getId(), hku.getId(), bio.getId());
//    }
//    /**
//     * Do a quick check that we're setting & getting the right things
//     */
//    public void testBasicCourse() {
//        assertEquals(t1.getId(), caffeine.getTranslationDetailId());
//        assertEquals(rohtoDetail.getId(), rohto.getCourseDetailId());
//        assertEquals(faculty.getId(), caffeine.getAudienceDetailId());
//        assertEquals(hku.getId(), rohto.getInstitutionDetailId());
//        assertEquals(bio.getId(), rohto.getCompetencyDetailId());
//    }
//
//    /**
//     * Check that fields are all getting saved & retrieved correctly
//     */
//    public void testCRUD() {
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(caffeine, rohto));
//        Course result = HibernateManager.getInstance().getEntity(Course.class, caffeine.getId());
//
//        assertEquals(caffeineDetail.getId(), result.getCourseDetailId());
//        assertEquals(t1.getId(), result.getTranslationDetailId());
//        assertEquals(faculty.getId(), result.getAudienceDetailId());
//        assertEquals(bmc.getId(), result.getInstitutionDetailId());
//        assertEquals(chem.getId(), result.getCompetencyDetailId());
//    }
//
//    public void testEquality() {
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(caffeine, rohto));
//        Course result = HibernateManager.getInstance().getEntity(Course.class, rohto.getId());
//
//        // should be 'logically' equal
//        assertEquals(rohto, result);
//
//        // should not be 'physically' equal
//        assertFalse(rohto == result);
//    }
}
