package org.catalyst.courses.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.HibernateManager;

import java.util.List;

public class InstitutionDetailTest extends TestCase {
    private final static Logger logger  = LogManager.getLogger(InstitutionDetailTest.class);

    // protected static so we can reference these values in CourseDetailTest
    protected final static String hkuName = "Hello Kitty University";
    protected final static String bmcName = "Badtz Maru College";
    protected final static String hkuAbbreviation = "HKU";
    protected final static String bmcAbbreviation = "BMC";
    protected final static String bmcNote = "Cartoon penguins allowed only";
    protected final static boolean hkuIsSponsor = false;
    protected final static String t1Name = "Translation to Alpacas";
    protected final static String t1Abbreviation = "T1";
    protected static final String rohtoCourseName = "Partiality confounded Rohto-effect";
    protected static final String rohtoDescription = "Cure for red eyes and staring at monitors until the eyeballs bleed dry.";

    private Institution hku;
    private Institution bmc;

    public void testSanity() {
        hku = new Institution(hkuName, hkuAbbreviation);
//        HibernateManager.getInstance().saveOrUpdate(hku);

        bmc = new Institution(bmcName, bmcAbbreviation);

        Course course = new Course(rohtoCourseName);
        course.setDescription(rohtoDescription);
        course.addSponsor(hku);
        course.addSponsor(bmc);
        HibernateManager.getInstance().saveOrUpdate(course);

//        CourseDetail result = HibernateManager.getInstance().getEntity(CourseDetail.class, 1);
//        logger.debug(result.toString());
//        Session session = HibernateManager.getInstance().startTransaction();
        List<Course> result = HibernateManager.getInstance().getAllEntities(Course.class);
        for(Course c : result) {
            logger.debug(c.toString());
            assertEquals(rohtoCourseName, c.getName());
            assertFalse(c.getId() == 0);
            logger.debug(c.getName());
            logger.debug(c.getId());
            for(Institution s : c.getSponsors()) {
                String abbr = s.getAbbreviation();
                assertTrue(abbr.equals(bmcAbbreviation) || abbr.equals(hkuAbbreviation));
                assertFalse(s.getId() == 0);
                logger.debug(abbr);
                logger.debug(s.getId());
            }
        }

    }
}
