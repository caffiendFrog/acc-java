package org.catalyst.courses.entities;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TranslationTest extends TestCase {
    private final static Logger logger = LogManager.getLogger(TranslationTest.class);

    // protected static so we can reference these values in CourseTest
    protected final static String t1Name = "Translation to Alpacas";
    protected final static String t1Abbreviation = "T1";
    protected final static String t2Name = "Translation to Robots";
    protected final static String t2Abbreviation = "T2";
    protected final static String t2Note = "Robots must not be operating with windows. Only doors.";

    private Translation t1;
    private Translation t2;

    public void setUp() {
        t1 = new Translation(t1Name, t1Abbreviation);
        t2 = new Translation(t2Name, t2Abbreviation);
    }

    /**
     * Quick sanity check we're still setting the basics correctly and check the Translation specific fields and defaults
     */
    public void testBasicTranslation() {
        assertEquals(t1Name, t1.getName());
        assertEquals(t1Abbreviation, t1.getAbbreviation());

        t2.setNote(t2Note);
        assertEquals(t2Note, t2.getNote());

        // should not be able to set to null
        try {
            t2.setAbbreviation(null);
            fail();
        } catch (NullPointerException npe) {
            assertTrue(true);
        } catch (Throwable t) {
            fail();
        }
    }
}
