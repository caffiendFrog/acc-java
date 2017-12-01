package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.catalyst.services.HibernateManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Testing all fields to make sure we don't accidentally override methods incorrectly in the future
 */
@SuppressWarnings("Duplicates")
public class TranslationTest {
    private final static Logger logger = LogManager.getLogger(TranslationTest.class);

    protected final static String t1Name = "TranslationDetail to Alpacas";
    protected final static String t1Abbreviation = "T1";
    protected final static String t2Name = "TranslationDetail to Robots";
    protected final static String t2Abbreviation = "T2";
    protected final static String t2Note = "Robots must not be operating with windows. Only doors.";

    private final static Translation t1 = new Translation(t1Name, t1Abbreviation);
    private final static Translation t2 = new Translation(t2Name, t2Abbreviation);

    @BeforeClass
    public static void before() {
//        removeAllTranslations();
        HibernateManager.getInstance().resetSessionFactory();
    }

    @Before
    public void setUp() {
        // reset entities
        t1.setName(t1Name);
        t1.setNote(null);
        t1.setAbbreviation(t1Abbreviation);
        t1.courseDetails.clear();

        t2.setName(t2Name);
        t2.setNote(null);
        t2.setAbbreviation(t2Abbreviation);
        t2.courseDetails.clear();
    }

    @Test
    public void testBasicTranslation() {
        assertEquals(t1Name, t1.getName());
        assertEquals(t2Abbreviation, t2.getAbbreviation());

        t2.setNote(t2Note);
        assertEquals(t2Note, t2.getNote());

        // should default to true
        assertTrue(t1.isActive());

        // deactivate and check
        t1.deactivate();
        assertFalse(t1.isActive());
    }

    /**
     * Should be able to update require fields, but shouldn't be able to set them to null
     */
    @Test
    public void testRequired() {
        t1.setAbbreviation("t1_t1");
        assertEquals("t1_t1", t1.getAbbreviation());

        try {
            t1.setAbbreviation(null);
            fail();
        } catch (NullPointerException npe) {
            assertTrue(true);
        } catch (Throwable throwable) {
            // should onluy be throwing an NPE
            fail();
        }

        t2.setName("qwerty");
        assertEquals("qwerty", t2.getName());

        try {
            t2.setName(null);
            fail();
        } catch (NullPointerException npe) {
            assertTrue(true);
        } catch (Throwable t) {
            //should only be throwing an NPE
            fail();
        }
    }

    @Test
    public void testCRUD() {
        saveTranslations();

        Map<Integer,Translation> idToTranslations = getIdToTranslations();
        assertEquals(2, idToTranslations.size());
        assertEquals(t2Abbreviation, idToTranslations.get(t2.getId()).getAbbreviation());

        // Change abbreviation & update
        t1.setAbbreviation("qwerty");
        t2.deactivate();
        saveTranslations();

        idToTranslations = getIdToTranslations();
        assertEquals(2, idToTranslations.size());
        assertEquals("qwerty", idToTranslations.get(t1.getId()).getAbbreviation());
        assertFalse(idToTranslations.get(t2.getId()).isActive());
    }

    @Test
    public void testEquality() {
        saveTranslations();
        Translation result = HibernateManager.getInstance().getEntity(Translation.class, t2.getId());

        // should be 'logically' equal
        assertEquals(t2, result);

        // should not be 'physically' equal
        assertFalse(t2 == result);

        // should only be 2 rows
        Map<Integer,Translation> idToTranslations = getIdToTranslations();
        assertEquals(2, idToTranslations.size());
    }

    private void saveTranslations() {
        HibernateManager.getInstance().saveOrUpdate(Arrays.asList(t1, t2));
    }

    protected static Map<Integer, Translation> getIdToTranslations() {
        List<Translation> translations = HibernateManager.getInstance().getAllEntities(Translation.class);
        return translations.stream().collect(Collectors.toMap(Translation::getId, Function.identity()));
    }

//    protected static void removeAllTranslations() {
//        final Session session =HibernateManager.getInstance().startTransaction();
//        List<Translation> results = HibernateManager.getInstance().getAllEntities(Translation.class);
//        for(Translation result : results) {
//            session.delete(result);
//        }
//        HibernateManager.getInstance().endTransaction(session);
//    }
}
