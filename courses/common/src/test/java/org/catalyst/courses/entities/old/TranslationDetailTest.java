package org.catalyst.courses.entities.old;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TranslationDetailTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(TranslationDetailTest.class);

    // protected static so we can reference these values in CourseDetailTest
    protected final static String t1Name = "TranslationDetail to Alpacas";
    protected final static String t1Abbreviation = "T1";
    protected final static String t2Name = "TranslationDetail to Robots";
    protected final static String t2Abbreviation = "T2";
    protected final static String t2Note = "Robots must not be operating with windows. Only doors.";

    private TranslationDetail t1;
    private TranslationDetail t2;

//    public void setUp() {
//        t1 = new TranslationDetail(t1Name, t1Abbreviation);
//        t2 = new TranslationDetail(t2Name, t2Abbreviation);
//    }
//
//    /**
//     * Quick sanity check we're still setting the basics correctly and check the TranslationDetail specific fields and defaults
//     */
//    public void testBasicTranslation() {
//        assertEquals(t1Name, t1.getName());
//        assertEquals(t1Abbreviation, t1.getAbbreviation());
//
//        t2.setNote(t2Note);
//        assertEquals(t2Note, t2.getNote());
//
//        // should not be able to set to null
//        try {
//            t2.setAbbreviation(null);
//            fail();
//        } catch (NullPointerException npe) {
//            assertTrue(true);
//        } catch (Throwable t) {
//            fail();
//        }
//    }
//
    public void testSanity() {
        assertTrue(true);
    }
//    public void testCRUD() {
//        // Create/Save translation
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(t1, t2));
//
//        // Read the translation back
//        TranslationDetail result = HibernateManager.getInstance().getEntity(TranslationDetail.class, t2.getId());
//        assertEquals(t2Abbreviation, result.getAbbreviation());
//    }
//
//    public void testEquality() {
//        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(t2, t1));
//
//        TranslationDetail result = HibernateManager.getInstance().getEntity(TranslationDetail.class, t2.getId());
//        // should be 'logically' equal
//        assertEquals(t2, result);
//        assertFalse( t2 == result );
//    }
}
